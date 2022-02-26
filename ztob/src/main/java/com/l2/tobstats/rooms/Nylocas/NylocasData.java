package com.l2.tobstats.rooms.Nylocas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.l2.tobstats.RoomData;

@Data
@EqualsAndHashCode(callSuper = true)
public class NylocasData extends RoomData
{
    private Integer wave31 = null;
    private Integer end_waves = null;
    private Integer boss_spawn = null;

    static final String UPDATE_VALUES_QUERY = "insert into Nylocas (id, wave31, end_waves, boss_spawn) values (:id, :wave31, :end_waves, :boss_spawn)";
}
