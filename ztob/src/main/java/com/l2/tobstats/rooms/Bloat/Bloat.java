package com.l2.tobstats.rooms.Bloat;

import java.util.Arrays;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import com.l2.tobstats.Room;
import com.l2.tobstats.RoomData;
import com.l2.tobstats.RoomOverlay;
import com.l2.tobstats.TobstatsConfig;
import com.l2.tobstats.TobstatsPlugin;
import net.runelite.client.ui.overlay.components.LineComponent;

@Slf4j
public class Bloat extends Room
{
    private BloatData data;

    @Inject
    protected Bloat(TobstatsPlugin plugin, TobstatsConfig config)
    {
        super(plugin, config);
    }

    @Override
    protected boolean isInRegion()
    {
        return client.getMapRegions() != null && client.getMapRegions().length > 0 && Arrays.stream(client.getMapRegions()).anyMatch(s -> s == 13125);
    }

    @Override
    protected void setIncomplete(Integer incomplete)
    {
        log.debug("Setting " + this.getClass().getSimpleName() + " incomplete to " + incomplete);
        data.setIncomplete(incomplete);
    }

    @Override
    protected RoomData getData()
    {
        return data;
    }

    @Override
    protected void init()
    {
        super.init();
        int id = 0;
        log.debug("Initializing " + this.getClass().getSimpleName() + " id " + id);
        data = new BloatData();
        data.setId(id);
    }

    @Override
    protected void reset()
    {
        super.reset();

        isActive = false;
        log.debug("resetting " + this.getClass().getSimpleName());
    }

    @Override
    protected void preRender(RoomOverlay roomOverlay)
    {
        LineComponent lineComponent = LineComponent.builder().left("Bloat").right(millisToTime(getCurrentMillis(), true, config.precisionTimers())).build();
        super.preRender(roomOverlay, lineComponent);
    }
}
