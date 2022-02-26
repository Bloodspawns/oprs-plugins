/*
 * THIS SOFTWARE WRITTEN BY A KEYBOARD-WIELDING MONKEY BOI
 * No rights reserved. Use, redistribute, and modify at your own discretion,
 * and in accordance with Yagex and RuneLite guidelines.
 * However, aforementioned monkey would prefer if you don't sell this plugin for profit.
 * Good luck on your raids!
 */

package com.l2.ztob;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;


import static net.runelite.api.Constants.TILE_FLAG_BRIDGE;

public abstract class RoomOverlay extends Overlay
{
    protected final TheatreConfig config;

    @Inject
    protected Client client;

    @Inject
    protected RoomOverlay(TheatreConfig config) {
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    protected void drawTile(Graphics2D graphics, WorldPoint point, Color color, int strokeWidth, int outlineAlpha, int fillAlpha) {
        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();
        if (point.distanceTo(playerLocation) >= 32) {
            return;
        }
        LocalPoint lp = LocalPoint.fromWorld(client, point);
        if (lp == null) {
            return;
        }

        Polygon poly = Perspective.getCanvasTilePoly(client, lp);
        if (poly == null) {
            return;
        }

        graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineAlpha));
        graphics.setStroke(new BasicStroke(strokeWidth));
        graphics.draw(poly);
        graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), fillAlpha));
        graphics.fill(poly);
    }

    protected void renderNpcOverlay(Graphics2D graphics, NPC actor, Color color, int outlineWidth, int outlineAlpha, int fillAlpha)
    {
        int size = 1;
        NPCComposition composition = actor.getTransformedComposition();
        if (composition != null)
        {
            size = composition.getSize();
        }
        LocalPoint lp = actor.getLocalLocation();
        Polygon tilePoly = Perspective.getCanvasTileAreaPoly(client, lp, size);

        if (tilePoly != null)
        {
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineAlpha));
            graphics.setStroke(new BasicStroke(outlineWidth));
            graphics.draw(tilePoly);
            graphics.setColor(new Color(0,0, 0, fillAlpha));
            graphics.fill(tilePoly);
        }
    }

    protected void renderTextLocation(Graphics2D graphics, String txtString, Color fontColor, Point canvasPoint)
    {
        if (canvasPoint != null)
        {
            final Point canvasCenterPoint = new Point(
                    canvasPoint.getX(),
                    canvasPoint.getY());
            final Point canvasCenterPoint_shadow = new Point(
                    canvasPoint.getX() + 1,
                    canvasPoint.getY() + 1) ;
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint_shadow, txtString, Color.BLACK);
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint, txtString, fontColor);
        }
    }

    protected void renderPoly(Graphics2D graphics, Color color, Polygon polygon)
    {
        renderPoly(graphics, color, polygon, 2);
    }

    protected void renderPoly(Graphics2D graphics, Color color, Polygon polygon, int width)
    {
        if (polygon != null)
        {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(width));
            graphics.draw(polygon);
        }
    }

    /**
     * Returns a polygon representing an area.
     *
     * @param client the game client
     * @param localLocation the center location of the AoE
     * @param size the size of the area (ie. 3x3 AoE evaluates to size 3)
     * @return a polygon representing the tiles in the area
     */
    public static Polygon getCanvasTileAreaPoly(@Nonnull Client client, @Nonnull LocalPoint localLocation, int size)
    {
        return getCanvasTileAreaPoly(client, localLocation, size,0,  true);
    }

    public static Polygon getCanvasTileAreaPoly(@Nonnull Client client, @Nonnull LocalPoint localLocation, int size, int borderOffset)
    {
        return getCanvasTileAreaPoly(client, localLocation, size,borderOffset,  true);
    }

    public static Polygon getCanvasTileAreaPoly(@Nonnull Client client, @Nonnull LocalPoint localLocation, int size, boolean centered)
    {
        return getCanvasTileAreaPoly(client, localLocation, size,0,  centered);
    }

    public static Polygon getCanvasTileAreaPoly(@Nonnull Client client, @Nonnull LocalPoint localLocation, int size, int borderOffset, boolean centered)
    {
        final int plane = client.getPlane();

        final int swX;
        final int swY;

        final int neX;
        final int neY;

        if (centered)
        {
            swX = localLocation.getX() - size * (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;
            swY = localLocation.getY() - size * (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;

            neX = localLocation.getX() + size * (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;
            neY = localLocation.getY() + size * (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;
        }
        else
        {
            swX = localLocation.getX() - (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;
            swY = localLocation.getY() - (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2;

            neX = localLocation.getX() - (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2 + size * (Perspective.LOCAL_TILE_SIZE + borderOffset);
            neY = localLocation.getY() - (Perspective.LOCAL_TILE_SIZE + borderOffset) / 2 + size * (Perspective.LOCAL_TILE_SIZE + borderOffset);
        }

        final int seX = swX;
        final int seY = neY;

        final int nwX = neX;
        final int nwY = swY;

        final byte[][][] tileSettings = client.getTileSettings();

        final int sceneX = localLocation.getSceneX();
        final int sceneY = localLocation.getSceneY();

        if (sceneX < 0 || sceneY < 0 || sceneX >= Perspective.SCENE_SIZE || sceneY >= Perspective.SCENE_SIZE)
        {
            return null;
        }

        int tilePlane = plane;
        if (plane < Constants.MAX_Z - 1 && (tileSettings[1][sceneX][sceneY] & TILE_FLAG_BRIDGE) == TILE_FLAG_BRIDGE)
        {
            tilePlane = plane + 1;
        }

        final int swHeight = getHeight(client, swX, swY, tilePlane);
        final int nwHeight = getHeight(client, nwX, nwY, tilePlane);
        final int neHeight = getHeight(client, neX, neY, tilePlane);
        final int seHeight = getHeight(client, seX, seY, tilePlane);

        Point p1 = Perspective.localToCanvas(client, swX, swY, swHeight);
        Point p2 = Perspective.localToCanvas(client, nwX, nwY, nwHeight);
        Point p3 = Perspective.localToCanvas(client, neX, neY, neHeight);
        Point p4 = Perspective.localToCanvas(client, seX, seY, seHeight);

        if (p1 == null || p2 == null || p3 == null || p4 == null)
        {
            return null;
        }

        Polygon poly = new Polygon();
        poly.addPoint(p1.getX(), p1.getY());
        poly.addPoint(p2.getX(), p2.getY());
        poly.addPoint(p3.getX(), p3.getY());
        poly.addPoint(p4.getX(), p4.getY());

        return poly;
    }

    private static int getHeight(@Nonnull Client client, int localX, int localY, int plane)
    {
        int sceneX = localX >> Perspective.LOCAL_COORD_BITS;
        int sceneY = localY >> Perspective.LOCAL_COORD_BITS;
        if (sceneX >= 0 && sceneY >= 0 && sceneX < Perspective.SCENE_SIZE && sceneY < Perspective.SCENE_SIZE)
        {
            int[][][] tileHeights = client.getTileHeights();

            int x = localX & (Perspective.LOCAL_TILE_SIZE - 1);
            int y = localY & (Perspective.LOCAL_TILE_SIZE - 1);
            int var8 = x * tileHeights[plane][sceneX + 1][sceneY] + (Perspective.LOCAL_TILE_SIZE - x) * tileHeights[plane][sceneX][sceneY] >> Perspective.LOCAL_COORD_BITS;
            int var9 = tileHeights[plane][sceneX][sceneY + 1] * (Perspective.LOCAL_TILE_SIZE - x) + x * tileHeights[plane][sceneX + 1][sceneY + 1] >> Perspective.LOCAL_COORD_BITS;
            return (Perspective.LOCAL_TILE_SIZE - y) * var8 + y * var9 >> Perspective.LOCAL_COORD_BITS;
        }

        return 0;
    }
}
