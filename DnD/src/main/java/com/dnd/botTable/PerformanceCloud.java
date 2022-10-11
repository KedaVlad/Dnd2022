package com.dnd.botTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class PerformanceCloud {

	private Map<String, Object> performans = new LinkedHashMap<>();
	
	void setPerformans(String key, Object object)
	{
		performans.put(key, object);
	}
	
	Map<String, Object> getPerformans()
	{
		return performans;
	}
	
	
	
}
