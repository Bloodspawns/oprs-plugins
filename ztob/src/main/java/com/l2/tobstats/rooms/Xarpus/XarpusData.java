package com.l2.tobstats.rooms.Xarpus;

import com.l2.tobstats.RoomData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class XarpusData extends RoomData
{
    private Integer heal_amount = 0;
    private Integer p1 = null;
    private Integer p2 = null;

    static final String UPDATE_VALUES_QUERY = "insert into Xarpus (id, heal_amount, p1, p2) values (:id, :heal_amount, :p1, :p2)";
}
