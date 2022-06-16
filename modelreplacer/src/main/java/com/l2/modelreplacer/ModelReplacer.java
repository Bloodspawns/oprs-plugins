package com.l2.modelreplacer;

import com.google.common.io.ByteStreams;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.overlay.OverlayIndex;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.util.Text;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Extension
@PluginDescriptor(
		name = "Model Replacer",
		description = "Replace in-game models",
		enabledByDefault = false
)
public class ModelReplacer extends Plugin
{
	private final static int MODEL_INDEX = 7;
	private final static int MODEL_INDEX_SHIFTED = MODEL_INDEX << 16;
	private final static HashMap<Integer, byte[]> models = new HashMap<>();

	@Inject
	private PluginManager pluginManager;

	@Inject
	private ModelReplacerConfig config;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Provides
	ModelReplacerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ModelReplacerConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		Text.fromCSV(config.modelsToReplace()).forEach(id -> add(Integer.parseInt(id)));
	}

	@Override
	protected void shutDown() throws Exception
	{
		Text.fromCSV(config.modelsToReplace()).forEach(id -> remove(Integer.parseInt(id)));

		final ChatMessageBuilder message = new ChatMessageBuilder()
			.append(Color.MAGENTA, "Wear/Remove item for changes to take effect!");

		chatMessageManager.queue(QueuedMessage.builder()
			.type(ChatMessageType.ITEM_EXAMINE)
			.runeLiteFormattedMessage(message.build())
			.build());
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("modelreplacer"))
		{
			if (event.getOldValue() == null || event.getNewValue() == null)
			{
				return;
			}

			if (event.getKey().equals("modelsToReplace"))
			{
				List<String> oldList = Text.fromCSV(event.getOldValue());
				List<String> newList = Text.fromCSV(event.getNewValue());

				ArrayList<String> removed = oldList.stream().filter(s -> !newList.contains(s)).collect(Collectors.toCollection(ArrayList::new));
				ArrayList<String> added = newList.stream().filter(s -> !oldList.contains(s)).collect(Collectors.toCollection(ArrayList::new));

				removed.forEach(id -> remove(Integer.parseInt(id)));
				added.forEach(id -> add(Integer.parseInt(id)));

				final ChatMessageBuilder message = new ChatMessageBuilder()
					.append(Color.MAGENTA, "Wear/Remove item for changes to take effect!");

				chatMessageManager.queue(QueuedMessage.builder()
					.type(ChatMessageType.ITEM_EXAMINE)
					.runeLiteFormattedMessage(message.build())
					.build());
			}
		}
	}

	private static void add(int modelId)
	{
		int model_overlay_index = MODEL_INDEX_SHIFTED | modelId;
		if (!OverlayIndex.getCacheTransformers().containsKey(model_overlay_index))
		{
			if (modelFileExists(modelId))
			{
				if (!models.containsKey(model_overlay_index))
				{
					models.put(model_overlay_index, getModel(modelId));
				}
				if (!OverlayIndex.getCacheTransformers().containsKey(model_overlay_index))
				{
					OverlayIndex.getCacheTransformers().put(model_overlay_index, (rsData) ->
					{
						if (!models.containsKey(model_overlay_index))
						{
							return rsData;
						}
						return models.get(model_overlay_index);
					});
				}
			}
		}
	}

	private static byte[] getModel(int modelId)
	{
		final String path = String.format("/runelite/%s/%s", MODEL_INDEX, modelId);
		try (final InputStream ovlIn = ModelReplacer.class.getResourceAsStream(path))
		{
			return ByteStreams.toByteArray(ovlIn);
		}
		catch (IOException e)
		{
			log.warn("Missing overlay data for {}", modelId);
			return null;
		}
	}

	private static boolean modelFileExists(int modelId)
	{
		final String path = String.format("/runelite/%s/%s", MODEL_INDEX, modelId);

		var resourceHash = ModelReplacer.class.getResource(path + ".hash");
		if (resourceHash == null)
		{
			return false;
		}
		String hash = resourceHash.getFile();

		var resourceModel = ModelReplacer.class.getResource(path);
		if (resourceModel == null)
		{
			return false;
		}
		String model = resourceModel.getFile();

		return hash != null && !hash.equals("") && model != null && !model.equals("");
	}

	private static void remove(int modelId)
	{
		int model_overlay_index = MODEL_INDEX_SHIFTED | modelId;
		OverlayIndex.getCacheTransformers().remove(model_overlay_index);
	}
}
