package com.l2.ztob.rooms.Nylocas;

import java.util.HashMap;
import lombok.Getter;

enum NylocasType
{
	MELEE_SMALL(8342, 10774, 10791, 8348, 10780, 10797),
	MELEE_BIG(8345, 10777, 10794, 8351, 10783, 10800),
	RANGE_SMALL(8343, 10775, 10792, 8349, 10781, 10798),
	RANGE_BIG(8346, 10778, 10795, 8352, 10784, 10801),
	MAGE_SMALL(8344, 10776, 10793, 8350, 10782, 10799),
	MAGE_BIG(8347, 10779, 10796, 8353, 10785, 10802);

	@Getter
	private final int id;
	@Getter
	private final int id_sm;
	@Getter
	private final int id_hm;
	@Getter
	private final int aggroId;
	@Getter
	private final int aggroId_sm;
	@Getter
	private final int aggroId_hm;
	@Getter
	private static final HashMap<Integer, NylocasType> lookupMap;
	static
	{
		lookupMap = new HashMap<>();
		for (NylocasType v : NylocasType.values())
		{
			lookupMap.put(v.getId(), v);
			lookupMap.put(v.getId_sm(), v);
			lookupMap.put(v.getId_hm(), v);
			lookupMap.put(v.getAggroId(), v);
			lookupMap.put(v.getAggroId_sm(), v);
			lookupMap.put(v.getAggroId_hm(), v);
		}
	}

	NylocasType(int id, int id_sm, int id_hm, int aggroId, int aggroId_sm, int aggroId_hm)
	{
		this.id = id;
		this.id_sm = id_sm;
		this.id_hm = id_hm;
		this.aggroId = aggroId;
		this.aggroId_sm = aggroId_sm;
		this.aggroId_hm = aggroId_hm;
	}
}
