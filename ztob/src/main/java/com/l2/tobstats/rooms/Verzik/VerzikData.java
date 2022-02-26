package com.l2.tobstats.rooms.Verzik;

import com.l2.tobstats.RoomData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VerzikData extends RoomData
{
    private Integer p1 = null;
    private Integer p2 = null;

    static final String UPDATE_VALUES_QUERY = "insert into Verzik (id, p1, p2) values (:id, :p1, :p2)";
}
