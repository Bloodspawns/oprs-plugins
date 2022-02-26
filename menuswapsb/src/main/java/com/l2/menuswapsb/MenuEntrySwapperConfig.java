/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package com.l2.menuswapsb;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.menuentryswapper.FairyRingMode;
import net.runelite.client.plugins.menuentryswapper.GEItemCollectMode;
import net.runelite.client.plugins.menuentryswapper.HouseAdvertisementMode;
import net.runelite.client.plugins.menuentryswapper.HouseMode;
import net.runelite.client.plugins.menuentryswapper.ShiftDepositMode;
import net.runelite.client.plugins.menuentryswapper.ShiftWithdrawMode;

@ConfigGroup("menuentryswapperb")
public interface MenuEntrySwapperConfig extends Config
{
	@ConfigItem(
			keyName = "hideAttack",
			name = "Hide attack on dead npcs",
			description = "Hide attack on dead npcs"
	)
	default boolean hideAttack()
	{
		return true;
	}

	@ConfigItem(
			keyName = "swapTobBuys",
			name = "Swap value with buy 1",
			description = "Swap value and buy 1 on tob chest items"
	)
	default boolean swapTobBuys()
	{
		return false;
	}

	@ConfigItem(
			keyName = "removeCastOnPlayers",
			name = "remove cast on players",
			description = "Remove the cast ice b.. and blood b.. options on players"
	)
	default boolean removeCastOnPlayers()
	{
		return false;
	}

	@ConfigItem(
			keyName = "swapImps",
			name = "Swaps loot and use on imps",
			description = "based on if u have the type of clue"
	)
	default boolean swapImps(){ return false; }
}
