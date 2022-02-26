/*
 * THIS SOFTWARE WRITTEN BY A KEYBOARD-WIELDING MONKEY BOI
 * No rights reserved. Use, redistribute, and modify at your own discretion,
 * and in accordance with Yagex and RuneLite guidelines.
 * However, aforementioned monkey would prefer if you don't sell this plugin for profit.
 * Good luck on your raids!
 */

package com.l2.ztob;

import com.google.inject.Binder;
import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.l2.ztob.rooms.Bloat.Bloat;
import com.l2.ztob.rooms.Maiden.Maiden;
import com.l2.ztob.rooms.Nylocas.Nylocas;
import com.l2.ztob.rooms.Sotetseg.Sotetseg;
import com.l2.ztob.rooms.Verzik.Verzik;
import com.l2.ztob.rooms.Xarpus.Xarpus;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
        name = "xz_Theatre",
        description = "All-in-one plugin for Theatre of Blood",
        tags = {"ToB"},
        enabledByDefault = false
)
public class TheatrePlugin extends Plugin
{
    private Room[] rooms = null;

    @Inject
    private EventBus eventBus;

    @Inject
    private Maiden maiden;
    @Inject
    private Bloat bloat;
    @Inject
    private Nylocas nylocas;
    @Inject
    private Sotetseg sotetseg;
    @Inject
    private Xarpus xarpus;
    @Inject
    private Verzik verzik;
    @Inject
    private Client client;

    @Override
    public void configure(Binder binder)
    {
        binder.bind(TheatreInputListener.class);
    }

    @Provides
    TheatreConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(TheatreConfig.class);
    }

    @Override
    protected void startUp()
    {
        if (rooms == null)
        {
            rooms = new Room[]{maiden, bloat, nylocas, sotetseg, xarpus, verzik};
            for (Room room : rooms)
            {
                room.init();
            }
        }

        for(Room room : rooms)
        {
            room.load();
            eventBus.register(room);
        }
    }

    @Override
    protected void shutDown()
    {
        for(Room room : rooms)
        {
            eventBus.unregister(room);
            room.unload();
        }
    }
}
