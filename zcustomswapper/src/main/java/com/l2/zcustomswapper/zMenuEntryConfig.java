package com.l2.zcustomswapper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("zmenuentryswapper")
public interface zMenuEntryConfig extends Config
{
    @ConfigSection(
            name = "Custom Swaps",
            description = "Configuration for custom Swaps",
            position = 0,
            closedByDefault = true
    )

    String customSwapsSection = "customSwapsSection";

    @ConfigSection(
            name = "[Shift Click] Custom Swaps",
            description = "Configuration for shift click custom Swaps",
            position = 1,
            closedByDefault = true
    )

    String shiftCustomSwapsSection = "shiftCustomSwapsSection";

    @ConfigSection(
            name = "Remove Options",
            description = "Configuration for removing swaps",
            position = 2,
            closedByDefault = true
    )

    String removeOptionsSection = "removeSwapsSection";

    @ConfigItem(
            name = "Custom Swaps Toggle",
            keyName = "customSwapsToggle",
            description = "Toggles the use of the Custom Swaps",
            section = customSwapsSection,
            position = 3
    )
    default boolean customSwapsToggle() { return false; }

    @ConfigItem(
            name = "Custom Swaps",
            keyName = "customSwapsStr",
            description = "",
            section = customSwapsSection,
            position = 4
    )
    default String customSwapsString() { return ""; }

    @ConfigItem(
            name = "Bank Custom Swaps",
            keyName = "bankCustomSwapsStr",
            description = "",
            section = customSwapsSection,
            position = 5
    )
    default String bankCustomSwapsString() { return ""; }

    @ConfigItem(
            name = "Shift - Custom Swaps Toggle",
            keyName = "shiftCustomSwapsToggle",
            description = "Toggles the use of the Shift Custom Swaps",
            section = shiftCustomSwapsSection,
            position = 6
    )
    default boolean shiftCustomSwapsToggle() { return false; }

    @ConfigItem(
            name = "Shift - Custom Swaps",
            keyName = "shiftCustomSwapsStr",
            description = "",
            section = shiftCustomSwapsSection,
            position = 7
    )
    default String shiftCustomSwapsString() { return ""; }

    @ConfigItem(
            name = "Shift - Bank Custom Swaps",
            keyName = "bankShiftCustomSwapsStr",
            description = "",
            section = shiftCustomSwapsSection,
            position = 8
    )
    default String bankShiftCustomSwapsString() { return ""; }

    @ConfigItem(
            name = "Remove Options Toggle",
            keyName = "removeOptionsToggle",
            description = "Toggles the use of the removing options",
            section = removeOptionsSection,
            position = 9
    )
    default boolean removeOptionsToggle() { return false; }

    @ConfigItem(
            name = "Remove Options",
            keyName = "removeOptionsStr",
            description = "",
            section = removeOptionsSection,
            position = 10
    )
    default String removeOptionsString() { return ""; }
}
