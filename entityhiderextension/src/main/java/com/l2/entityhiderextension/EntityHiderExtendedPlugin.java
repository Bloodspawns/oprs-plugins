package com.l2.entityhiderextension;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.util.Text;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;

@Extension
@PluginDescriptor(
	name = "EntityHiderExtendedPlugin",
	description = "shhh",
	tags = {"dead", "hide", "npc"}
)
@Slf4j
public class EntityHiderExtendedPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private EntityHiderExtendedConfig config;

	@Inject
	private PluginManager pluginManager;

	private HashSet<String> getNpcsToHide = new HashSet<>();
	private HashSet<String> getNpcsToHideOnDeath = new HashSet<>();
	private HashSet<Integer> getNpcsByAnimationToHide = new HashSet<>();
	private HashSet<Integer> getNpcsByIdToHideOnDeath = new HashSet<>();
	private HashSet<Integer> getNpcsByIdToHide = new HashSet<>();
	private HashSet<Integer> getGrahpicsObjectByIdToHide = new HashSet<>();
	private HashSet<String> getNpcsToNeverHideOnDeath = new HashSet<>();
	private HashSet<Integer> getNpcsByIDToNeverHideOnDeath = new HashSet<>();

	@Provides
	EntityHiderExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EntityHiderExtendedConfig.class);
	}

	private void setDeadNPCsHidden(boolean val)
	{
		client.setDeadNPCsHidden(val);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void startUp()
	{
		client.setIsHidingEntities(true);
		updateConfig();

		getNpcsToHide.clear();
		getNpcsToHideOnDeath.clear();
		getNpcsByAnimationToHide.clear();
		getNpcsByIdToHideOnDeath.clear();
		getGrahpicsObjectByIdToHide.clear();
		getNpcsToNeverHideOnDeath.clear();
		getNpcsByIdToHide.clear();
		getNpcsByIDToNeverHideOnDeath.clear();
		getNpcsToNeverHideOnDeath.addAll(Text.fromCSV(config.neverHideNPC().toLowerCase()));
		parseAndAddSave(Text.fromCSV(config.neverHideNPCID()), getNpcsByIDToNeverHideOnDeath);
		getNpcsToHide.addAll(Text.fromCSV(config.hideNPCsNames().toLowerCase()));
		getNpcsToHideOnDeath.addAll(Text.fromCSV(config.hideNPCsOnDeath().toLowerCase()));
		parseAndAddSave(Text.fromCSV(config.hideNPCsByIDOnDeath()), getNpcsByIdToHideOnDeath);
		parseAndAddSave(Text.fromCSV(config.hideNPCsByID()), getNpcsByIdToHide);
		parseAndAddSave(Text.fromCSV(config.hideNPCsByAnimationId()), getNpcsByAnimationToHide);
		parseAndAddSave(Text.fromCSV(config.hideGrahpicsObjectById()), getGrahpicsObjectByIdToHide);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("ehextended"))
		{
			updateConfig();

			if (event.getOldValue() == null || event.getNewValue() == null)
			{
				return;
			}

			if (event.getKey().equals("neverHideNPC"))
			{
				getNpcsToNeverHideOnDeath.clear();
				getNpcsToNeverHideOnDeath.addAll(Text.fromCSV(config.neverHideNPC().toLowerCase()));
			}

			if (event.getKey().equals("neverHideNPCID"))
			{
				getNpcsByIDToNeverHideOnDeath.clear();
				parseAndAddSave(Text.fromCSV(config.neverHideNPCID()), getNpcsByIDToNeverHideOnDeath);
			}

			if (event.getKey().equals("hideNPCsNames"))
			{
				getNpcsToHide.clear();
				getNpcsToHide.addAll(Text.fromCSV(config.hideNPCsNames().toLowerCase()));
			}

			if (event.getKey().equals("hideNPCsOnDeath"))
			{
				getNpcsByIdToHideOnDeath.clear();
				getNpcsToHideOnDeath.addAll(Text.fromCSV(config.hideNPCsOnDeath().toLowerCase()));
			}

			if (event.getKey().equals("hideNPCsByID"))
			{
				getNpcsByIdToHide.clear();
				parseAndAddSave(Text.fromCSV(config.hideNPCsByID()), getNpcsByIdToHide);
			}

			if (event.getKey().equals("hideNPCsByIDOnDeath"))
			{
				getNpcsByIdToHideOnDeath.clear();
				parseAndAddSave(Text.fromCSV(config.hideNPCsByIDOnDeath()), getNpcsByIdToHideOnDeath);
			}

			if (event.getKey().equals("hideNPCsByAnimationId"))
			{
				getNpcsByAnimationToHide.clear();
				parseAndAddSave(Text.fromCSV(config.hideNPCsByAnimationId()), getNpcsByAnimationToHide);
			}

			if (event.getKey().equals("hideGrahpicsObjectById"))
			{
				getGrahpicsObjectByIdToHide.clear();
				parseAndAddSave(Text.fromCSV(config.hideGrahpicsObjectById()), getGrahpicsObjectByIdToHide);
			}
		}
	}

	private static void parseAndAddSave(Collection<String> source, Collection<Integer> collection)
	{
		for (String s : source)
		{
			try
			{
				int val = Integer.parseInt(s);
				collection.add(val);
			}
			catch (NumberFormatException ex)
			{
				log.warn("Config entry could not be parsed, entry: {}", s);
			}
		}
	}

	private void updateConfig()
	{
		setDeadNPCsHidden(config.hideDeadNPCs());
	}

	@Override
	protected void shutDown()
	{
		client.setIsHidingEntities(false);
		setDeadNPCsHidden(false);
	}

	@Subscribe
	protected void onClientTick(ClientTick clientTick)
	{
		for (NPC npc : client.getNpcs())
		{
			String name = Text.standardize(npc.getName());

			boolean shouldNeverHide1 = getNpcsToNeverHideOnDeath.contains(name);
			boolean shouldNeverHide2 = getNpcsByIDToNeverHideOnDeath.contains(npc.getId());
			if (shouldNeverHide1 || shouldNeverHide2)
			{
				npc.setHidden(false);
				continue;
			}

			boolean shouldHide1 = getNpcsToHide.contains(name);
			boolean shouldHide2 = getNpcsToHideOnDeath.contains(name) && npc.getHealthRatio() == 0;
			boolean shouldHide3 = getNpcsByAnimationToHide.contains(npc.getAnimation());
			boolean shouldHide4 = getNpcsByIdToHideOnDeath.contains(npc.getId()) && npc.getHealthRatio() == 0;
			boolean shouldHide5 = getNpcsByIdToHide.contains(npc.getId());

			npc.setHidden(shouldHide1 || shouldHide2 || shouldHide3 || shouldHide4 || shouldHide5);
		}
	}

	@Subscribe
	protected void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObjectCreated)
	{
		if (getGrahpicsObjectByIdToHide.contains(graphicsObjectCreated.getGraphicsObject().getId()))
		{
			graphicsObjectCreated.getGraphicsObject().setHidden(true);
		}
	}
}
