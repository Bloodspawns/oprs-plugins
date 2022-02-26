package com.l2.zcustomswapper;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;
import net.runelite.client.util.WildcardMatcher;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static net.runelite.api.MenuAction.MENU_ACTION_DEPRIORITIZE_OFFSET;
import static net.runelite.client.util.Text.removeTags;
import static net.runelite.client.util.Text.standardize;

@Extension
@PluginDescriptor(
		name = "[z] Custom Swapper",
		description = "Set your own custom swaps and/or remove options.",
		tags = {"mes", "menu", "entry", "swapper", "custom", "swap"},
		enabledByDefault = false
)
@Slf4j
public class zMenuEntryPlugin extends Plugin implements KeyListener
{
	@Data
	@AllArgsConstructor
	static class EntryFromConfig
	{
		private String option;
		private String target;
		private String topOption;
		private String topTarget;

		EntryFromConfig(String option, String target)
		{
			this(option, target, null, null);
		}

		@Override
		public boolean equals(Object other)
		{
			if (!(other instanceof EntryFromConfig))
			{
				return false;
			}
			return option.equals(((EntryFromConfig)other).option)
					&& target.equals(((EntryFromConfig)other).target)
					&& topOption.equals(((EntryFromConfig) other).topOption)
					&& topTarget.equals(((EntryFromConfig) other).topTarget);
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(option, target, topOption, topTarget);
		}
	}

	@Inject
	private Client client;

	@Inject
	private MenuManager menuManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private zMenuEntryConfig config;

	@Provides
	zMenuEntryConfig getConfig(ConfigManager configManager) { return (zMenuEntryConfig) configManager.getConfig(zMenuEntryConfig.class); }

	private static final Set<MenuAction> EXAMINE_TYPES = ImmutableSet.of(
			MenuAction.EXAMINE_ITEM,
			MenuAction.EXAMINE_ITEM_GROUND,
			MenuAction.EXAMINE_NPC,
			MenuAction.EXAMINE_OBJECT);

	private static final Splitter SPLITTER = Splitter.on("\n").omitEmptyStrings().trimResults();
	private final ArrayList<EntryFromConfig> customSwaps = new ArrayList<>();
	private final ArrayList<EntryFromConfig> shiftCustomSwaps = new ArrayList<>();
	private final ArrayList<EntryFromConfig> removeOptions = new ArrayList<>();
	private final ArrayList<EntryFromConfig> bankCustomSwaps = new ArrayList<>();
	private final ArrayList<EntryFromConfig> shiftBankCustomSwaps = new ArrayList<>();
	private boolean holdingShift = false;

	@Override
	protected void startUp()
	{
		holdingShift = false;
		keyManager.registerKeyListener(this);
		customSwaps.clear();
		parseConfigToList(config.customSwapsString(), customSwaps);
		shiftCustomSwaps.clear();
		parseConfigToList(config.shiftCustomSwapsString(), shiftCustomSwaps);
		removeOptions.clear();
		parseConfigToList(config.removeOptionsString(), removeOptions);
		bankCustomSwaps.clear();
		parseConfigToList(config.bankCustomSwapsString(), bankCustomSwaps);
		shiftBankCustomSwaps.clear();
		parseConfigToList(config.bankShiftCustomSwapsString(), shiftBankCustomSwaps);
	}

	@Override
	protected void shutDown()
	{
		holdingShift = false;
		keyManager.unregisterKeyListener(this);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("zmenuentryswapper"))
		{
			switch (event.getKey())
			{
				case "customSwapsStr":
					customSwaps.clear();
					parseConfigToList(config.customSwapsString(), customSwaps);
					break;
				case "shiftCustomSwapsStr":
					shiftCustomSwaps.clear();
					parseConfigToList(config.shiftCustomSwapsString(), shiftCustomSwaps);
					break;
				case "removeOptionsStr":
					removeOptions.clear();
					parseConfigToList(config.removeOptionsString(), removeOptions);
					break;
				case "bankCustomSwapsStr":
					bankCustomSwaps.clear();
					parseConfigToList(config.bankCustomSwapsString(), bankCustomSwaps);
					break;
				case "bankShiftCustomSwapsStr":
					shiftBankCustomSwaps.clear();
					parseConfigToList(config.bankShiftCustomSwapsString(), shiftBankCustomSwaps);
					break;
			}
		}
	}

	private void parseConfigToList(String value, List<EntryFromConfig> set)
	{
		final List<String> strList = SPLITTER.splitToList(value);

		for (String str : strList)
		{
			String[] stringList = str.split(",");

			if (stringList.length <= 1)
			{
				continue;
			}

			String option = stringList[0].toLowerCase().trim();
			String target = stringList[1].toLowerCase().trim();
			String topOption = null;
			String topTarget = null;
			if (stringList.length == 4)
			{
				topOption = stringList[2].toLowerCase().trim();
				topTarget = stringList[3].toLowerCase().trim();
			}
			var entryFromConfig = new EntryFromConfig(option, target, topOption, topTarget);

			set.add(entryFromConfig);
		}
	}

	private static int topEntryIndex(final MenuEntry[] entries)
	{
		for (int i = entries.length - 1; i >= 0; i--)
		{
			int type = entries[i].getType().getId();
			if (!EXAMINE_TYPES.contains(MenuAction.of(type)))
			{
				return i;
			}
		}

		return entries.length - 1;
	}

	private static int indexOfEntry(final List<EntryFromConfig> configEntries, final EntryFromConfig entryFromConfig, final MenuEntry[] entries)
	{
		final int topEntryIndex = topEntryIndex(entries);
		MenuEntry topEntry = entries[topEntryIndex];

		String target = removeTags(topEntry.getTarget()).toLowerCase();
		String option = removeTags(topEntry.getOption()).toLowerCase();
		for (int i = 0; i < configEntries.size(); i++)
		{
			var _configEntry = configEntries.get(i);
			if ((_configEntry.option.equals(entryFromConfig.option) || WildcardMatcher.matches(_configEntry.option, entryFromConfig.option))
				&& (_configEntry.target.equals(entryFromConfig.target) || WildcardMatcher.matches(_configEntry.target, entryFromConfig.target)))
			{
				var a = _configEntry.topOption == null;
				var b = _configEntry.topTarget == null;
				Supplier<Boolean> c = () -> _configEntry.topOption.equals(option) || WildcardMatcher.matches(_configEntry.topOption, option);
				Supplier<Boolean> d = () -> _configEntry.topTarget.equals(target) || WildcardMatcher.matches(_configEntry.topTarget, target);
				if (a || b || (c.get() && d.get()))
				{
					return i;
				}
			}
		}
		return -1;
	}

	private MenuEntry[] filterEntries(MenuEntry[] menuEntries)
	{
		ArrayList<MenuEntry> filtered = new ArrayList<>();

		for (MenuEntry entry : menuEntries)
		{
			String target = standardize(removeTags(entry.getTarget()));
			String option = standardize(removeTags(entry.getOption()));

			EntryFromConfig entryFromConfig = new EntryFromConfig(option, target);

			if (indexOfEntry(removeOptions, entryFromConfig, menuEntries) == -1)
			{
				filtered.add(entry);
			}
		}

		return filtered.toArray(new MenuEntry[0]);
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN || client.isMenuOpen() || !isBankInterfaceClosed())
		{
			return;
		}

		MenuEntry[] menuEntries = client.getMenuEntries();
		if (config.removeOptionsToggle())
		{
			menuEntries = filterEntries(menuEntries);
			client.setMenuEntries(menuEntries);
		}

		int entryIndex = -1;
		int priority = -1;
		for (int i = 0; i < menuEntries.length; i++)
		{
			var entry = menuEntries[i];
			String target = standardize(removeTags(entry.getTarget()));
			String option = standardize(removeTags(entry.getOption()));

			EntryFromConfig entryFromConfig = new EntryFromConfig(option, target);

			if (holdingShift && config.shiftCustomSwapsToggle())
			{
				int index = indexOfEntry(shiftCustomSwaps, entryFromConfig, menuEntries);
				if (index > priority)
				{
					entryIndex = i;
					priority = index;
				}
			}
			else if (config.customSwapsToggle())
			{
				int index = indexOfEntry(customSwaps, entryFromConfig, menuEntries);
				if (index > priority)
				{
					entryIndex = i;
					priority = index;
				}
			}
		}

		if (entryIndex < 0)
		{
			return;
		}

		MenuEntry target = menuEntries[entryIndex];

		final int targetId = target.getIdentifier();
		final int targetType = target.getType().getId();

		for (MenuEntry menuEntry : menuEntries)
		{
			if (menuEntry.getType().getId() < target.getType().getId())
			{
				menuEntry.setDeprioritized(true);
			}
		}

		if ((targetId >= 6 && targetId <= 9) && targetType == MenuAction.CC_OP_LOW_PRIORITY.getId())
		{
			target.setType(MenuAction.CC_OP);
		}

		MenuEntry first = menuEntries[menuEntries.length - 1];
		menuEntries[menuEntries.length - 1] = menuEntries[entryIndex];
		menuEntries[entryIndex] = first;
		client.setMenuEntries(menuEntries);
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (isBankInterfaceClosed() || event.getIdentifier() > 2)
		{
			return;
		}

		MenuEntry[] menuEntries = client.getMenuEntries();

		int entryIndex = -1;
		int priority = -1;
		for (int i = 0; i < menuEntries.length; i++)
		{
			var entry = menuEntries[i];
			String target = removeTags(entry.getTarget()).toLowerCase();
			String option = removeTags(entry.getOption()).toLowerCase();

			EntryFromConfig entryFromConfig = new EntryFromConfig(option, target);

			if (holdingShift && config.shiftCustomSwapsToggle())
			{
				int index = indexOfEntry(shiftBankCustomSwaps, entryFromConfig, menuEntries);
				if (index > priority)
				{
					entryIndex = i;
					priority = index;
				}
			}
			else if (config.customSwapsToggle())
			{
				int index = indexOfEntry(bankCustomSwaps, entryFromConfig, menuEntries);
				if (index > priority)
				{
					entryIndex = i;
					priority = index;
				}
			}
		}

		if (entryIndex < 0)
		{
			return;
		}

		MenuEntry target = menuEntries[entryIndex];

		final int opId = target.getIdentifier();
		final int actionId = opId >= 6 ? MenuAction.CC_OP_LOW_PRIORITY.getId() : MenuAction.CC_OP.getId();
		if (event.getType() == MenuAction.CC_OP.getId()
				&& (event.getIdentifier() == 1 || event.getIdentifier() == 2))
		{
			specialSwap(actionId, opId);
		}
	}

	/**
	 * @param actionId Menu Action ID
	 * @param opId Targets Identifier
	 */
	private void specialSwap(int actionId, int opId)
	{
		MenuEntry[] menuEntries = client.getMenuEntries();

		for (int i = menuEntries.length - 1; i >= 0; --i)
		{
			MenuEntry entry = menuEntries[i];

			if (entry.getType().getId() == actionId && entry.getIdentifier() == opId)
			{
				entry.setType(MenuAction.CC_OP);

				menuEntries[i] = menuEntries[menuEntries.length - 1];
				menuEntries[menuEntries.length - 1] = entry;

				client.setMenuEntries(menuEntries);
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent event)
	{
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			holdingShift = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			holdingShift = false;
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged event)
	{
		if (!event.isFocused())
		{
			holdingShift = false;
		}
	}

	private boolean isBankInterfaceClosed()
	{
		final Widget widgetBankTitleBar = client.getWidget(WidgetInfo.BANK_TITLE_BAR);
		final Widget widgetDepositBox = client.getWidget(192 << 16);
		final Widget coxPublicChest = client.getWidget(550, 1);
		final Widget coxPrivateChest = client.getWidget(271, 1);

		return (widgetBankTitleBar == null || widgetBankTitleBar.isHidden())
				&& (widgetDepositBox == null || widgetDepositBox.isHidden())
				&& (coxPublicChest == null || coxPublicChest.isHidden())
				&& (coxPrivateChest == null || coxPrivateChest.isHidden());
	}
}
