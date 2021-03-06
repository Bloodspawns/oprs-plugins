/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.l2.npchighlightb;

import java.awt.Color;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("npcindicatorsb")
public interface NpcIndicatorsConfigb extends Config
{
	@ConfigSection(
		name = "Render style",
		description = "The render style of NPC highlighting",
		position = 0
	)
	String renderStyleSection = "renderStyleSection";

	@ConfigItem(
		position = 0,
		keyName = "highlightHull",
		name = "Highlight hull",
		description = "Configures whether or not NPC should be highlighted by hull",
		section = renderStyleSection
	)
	default boolean highlightHull()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "highlightTile",
		name = "Highlight local position tile",
		description = "Configures whether or not NPC should be highlighted by local position tile",
		section = renderStyleSection
	)
	default boolean highlightTile()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "highlightSouthWestTile",
		name = "Highlight south west tile",
		description = "Configures whether or not NPC should be highlighted by south western tile",
		section = renderStyleSection
	)
	default boolean highlightSouthWestTile()
	{
		return false;
	}

	@ConfigItem(
		position = 3,
		keyName = "highlightWorldPositionTile",
		name = "Highlight world position tile",
		description = "Configures whether or not NPC should be highlighted by world position tile",
		section = renderStyleSection
	)
	default boolean highlightWorldPositionTile()
	{
		return false;
	}

	@ConfigItem(
		position = 4,
		keyName = "outlineNpc",
		name = "Outline npc",
		description = "Configures whether or not NPC should be outlined",
		section = renderStyleSection
	)
	default boolean outlineNpc()
	{
		return false;
	}

	@ConfigItem(
		position = 5,
		keyName = "outlineWidth3d",
		name = "Outline width",
		description = "Configures the amount of pixels to outline the npc with",
		section = renderStyleSection
	)
	default int outlineWidth3d()
	{
		return 1;
	}

	@ConfigItem(
		position = 5,
		keyName = "outlineFeather3d",
		name = "Outline feather",
		description = "Configures the amount of pixels to glow the outline of the npc with",
		section = renderStyleSection
	)
	default int outlineFeather3d()
	{
		return 0;
	}

	@ConfigItem(
		position = 6,
		keyName = "tileBorderWidth",
		name = "Width of tile border",
		description = "Configures the amount of pixels for tile outlines",
		section = renderStyleSection
	)
	default int tileBorderWidth()
	{
		return 1;
	}

	@ConfigItem(
		position = 3,
		keyName = "npcToHighlight",
		name = "NPCs to Highlight",
		description = "List of NPC names to highlight"
	)
	default String getNpcToHighlight()
	{
		return "";
	}

	@ConfigItem(
		keyName = "npcToHighlight",
		name = "",
		description = ""
	)
	void setNpcToHighlight(String npcsToHighlight);

	@ConfigItem(
		position = 4,
		keyName = "npcColor",
		name = "Highlight Color",
		description = "Color of the NPC highlight",
		section = renderStyleSection
	)
	@Alpha
	default Color getHighlightColor()
	{
		return Color.CYAN;
	}

	@Alpha
	@ConfigItem(
		position = 4,
		keyName = "fillColor",
		name = "Fill Color",
		description = "Color of the NPC highlight fill",
		section = renderStyleSection
	)
	default Color fillColor()
	{
		return new Color(0, 255, 255, 0);
	}

	@ConfigItem(
		position = 5,
		keyName = "drawNames",
		name = "Draw names above NPC",
		description = "Configures whether or not NPC names should be drawn above the NPC"
	)
	default boolean drawNames()
	{
		return false;
	}

	@ConfigItem(
		position = 6,
		keyName = "drawMinimapNames",
		name = "Draw names on minimap",
		description = "Configures whether or not NPC names should be drawn on the minimap"
	)
	default boolean drawMinimapNames()
	{
		return false;
	}

	@ConfigItem(
		position = 7,
		keyName = "highlightMenuNames",
		name = "Highlight menu names",
		description = "Highlight NPC names in right click menu"
	)
	default boolean highlightMenuNames()
	{
		return false;
	}

	@ConfigItem(
		position = 8,
		keyName = "ignoreDeadNpcs",
		name = "Ignore dead NPCs",
		description = "Prevents highlighting NPCs after they are dead"
	)
	default boolean ignoreDeadNpcs()
	{
		return true;
	}

	@ConfigItem(
		position = 9,
		keyName = "deadNpcMenuColor",
		name = "Dead NPC menu color",
		description = "Color of the NPC menus for dead NPCs"
	)
	Color deadNpcMenuColor();

	@ConfigItem(
		position = 10,
		keyName = "showRespawnTimer",
		name = "Show respawn timer",
		description = "Show respawn timer of tagged NPCs")
	default boolean showRespawnTimer()
	{
		return false;
	}
}