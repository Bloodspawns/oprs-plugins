package com.l2.tobstats.rooms.Bloat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.l2.tobstats.RoomData;

@Data
@EqualsAndHashCode(callSuper = true)
public class BloatData extends RoomData
{
    static final String UPDATE_VALUES_QUERY = "insert into Bloat (id) values (:id)";
}
