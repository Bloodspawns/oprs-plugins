package com.l2.shiftwalker;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;

@Extension
@PluginDescriptor(
		name = "Shift Click Walk Under",
		description = "Use Shift to toggle the Walk Here menu option. While pressed you will Walk rather than interact with objects.",
		tags = {"npcs", "items", "objects"},
		enabledByDefault = false
)
@Slf4j
public class ShiftWalkerPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private ShiftWalkerInputListener inputListener;
	@Inject
	private ConfigManager configManager;
	@Inject
	private KeyManager keyManager;
	private boolean hotKeyPressed = false;

	public ShiftWalkerPlugin()
	{
	}

	public void startUp()
	{
		this.keyManager.registerKeyListener(this.inputListener);
	}

	public void shutDown()
	{
		this.keyManager.unregisterKeyListener(this.inputListener);
	}

	@Subscribe
	public void onFocusChanged(FocusChanged event)
	{
		if (!event.isFocused())
		{
			this.hotKeyPressed = false;
		}
	}

	@Subscribe(priority = -1)
	public void onClientTick(ClientTick event)
	{
		if (client.getGameState() == GameState.LOGGED_IN && !client.isMenuOpen() && hotKeyPressed)
		{
			MenuEntry[] entries = client.getMenuEntries();
			int entryIndex = -1;
			for (int i = 0; i < entries.length; i++)
			{
				MenuEntry entry = entries[i];
				int opId = entry.getType().getId();
				if (opId >= 2000)
				{
					opId -= 2000;
				}
				if (opId == MenuAction.WALK.getId())
				{
					entryIndex = i;
				}
			}
			if (entryIndex < 0)
			{
				return;
			}
			for (MenuEntry menuEntry : entries)
			{
				if (menuEntry.getType().getId() < MenuAction.WALK.getId())
				{
					menuEntry.setDeprioritized(true);
				}
			}
			MenuEntry first = entries[entries.length - 1];
			entries[entries.length - 1] = entries[entryIndex];
			entries[entryIndex] = first;
			client.setMenuEntries(entries);
		}
	}

	@Subscribe(priority = -1)
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (client.getGameState() == GameState.LOGGED_IN && hotKeyPressed)
		{
			boolean hasWalkHere = false;
			for (MenuEntry menuEntry : client.getMenuEntries())
			{
				int opId = menuEntry.getType().getId();
				if (opId >= 2000)
				{
					opId -= 2000;
				}
				hasWalkHere |= opId == MenuAction.WALK.getId();
			}
			if (!hasWalkHere)
			{
				return;
			}
			if (event.getType() < MenuAction.WALK.getId())
			{
				deprioritizeEntry(event.getIdentifier(), event.getType());
			}
		}
	}

	private void deprioritizeEntry(int id, int op_id)
	{
		MenuEntry[] menuEntries = client.getMenuEntries();

		for (int i = menuEntries.length - 1; i >= 0; --i)
		{
			MenuEntry entry = menuEntries[i];

			if (entry.getType().getId() == op_id && entry.getIdentifier() == id)
			{
				// Raise the priority of the op so it doesn't get sorted later
				entry.setDeprioritized(true);
				menuEntries[i] = menuEntries[menuEntries.length - 1];
				menuEntries[menuEntries.length - 1] = entry;

				client.setMenuEntries(menuEntries);
				break;
			}
		}
	}

	public void setHotKeyPressed(boolean hotKeyPressed)
	{
		this.hotKeyPressed = hotKeyPressed;
	}
}
