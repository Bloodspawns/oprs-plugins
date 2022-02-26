package com.l2.accountmanager;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import org.pf4j.Extension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

@Extension
@PluginDescriptor(
	name = "Account Manager",
	description = "Easily log into the game",
	tags = {"Login"},
	enabledByDefault = false
)
@Slf4j
@Singleton
public class AccountManagerPlugin extends Plugin
{
	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private KeyManager keyManager;

	@Inject
	private AccountManagerConfig config;

	@Inject
	private Client client;

	private AccountManagerPluginPanel panel;
	private NavigationButton button;
	private Canvas canvas;

	private final KeyListener keyListener = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			_keyPressed(e);
		}
	};

	@Provides
	AccountManagerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AccountManagerConfig.class);
	}

	@Override
	protected void startUp()
	{
		panel = injector.getInstance(AccountManagerPluginPanel.class);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "icon.png");

		button = NavigationButton.builder()
			.tooltip("Account Manager")
			.icon(icon)
			.priority(1)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(button);

		hookListener();
	}

	protected void _keyPressed(KeyEvent e)
	{
		if (config.hotkey().matches(e))
		{
			panel.setLoginDefault();
		}
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(button);
		client.getCanvas().removeKeyListener(keyListener);
	}

	@Subscribe
	protected void onGameStateChanged(GameStateChanged event)
	{
		hookListener();
	}

	@Subscribe
	protected void onBeforeRender(BeforeRender b)
	{
		if (canvas != client.getCanvas())
		{
			canvas = client.getCanvas();
			hookListener();
		}
	}

	protected void hookListener()
	{
		if (client.getGameState() == GameState.LOGIN_SCREEN)
		{
			client.getCanvas().addKeyListener(keyListener);
		}
		else
		{
			client.getCanvas().removeKeyListener(keyListener);
		}
	}
}
