package com.l2.ztob;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;

public class NyloSelectionBox extends Overlay
{
	private InfoBoxComponent component;

	@Getter
	@Setter
	private boolean isSelected = false;

	@Getter
	@Setter
	private boolean isHovered = false;

	public NyloSelectionBox(InfoBoxComponent component)
	{
		this.component = component;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (isSelected)
		{
			component.setColor(Color.GREEN);
			component.setText("On");
		}
		else
		{
			component.setColor(Color.RED);
			component.setText("Off");
		}

		Dimension result = component.render(graphics);

		if (isHovered)
		{
			Color color = graphics.getColor();
			graphics.setColor(new Color(200, 200, 200));
			graphics.drawRect(component.getBounds().x, component.getBounds().y, component.getBounds().width, component.getBounds().height);
			graphics.setColor(color);
		}

		return result;
	}
}
