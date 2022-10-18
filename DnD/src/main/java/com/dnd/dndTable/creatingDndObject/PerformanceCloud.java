package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PerformanceCloud implements Serializable{

	
	private static final long serialVersionUID = 2145986472020080908L;
	
	private Map<String, List<String>> performans = new LinkedHashMap<>();
	
	public void setPerformans(String key, List<String> object)
	{
		performans.put(key, object);
	}
	
	public Map<String, List<String>> getPerformans()
	{
		return performans;
	}
	
	
	
	
	
}

