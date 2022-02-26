package com.l2.shiftwalker;

import java.awt.event.KeyEvent;
import javax.inject.Inject;
import net.runelite.client.input.KeyListener;

public class ShiftWalkerInputListener implements KeyListener
{
    @Inject
    private ShiftWalkerPlugin plugin;

    public ShiftWalkerInputListener() {
    }

    public void keyTyped(KeyEvent event) {
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == 16) {
            this.plugin.setHotKeyPressed(true);
        }

    }

    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == 16) {
            this.plugin.setHotKeyPressed(false);
        }

    }
}
