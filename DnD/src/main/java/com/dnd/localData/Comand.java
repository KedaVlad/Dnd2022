package com.dnd.localData;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comand {

	@JsonProperty("field_cloud")
	private boolean cloud;
	@JsonProperty("field_searching")
	private boolean searching;
	@JsonProperty("field_influencing")
	private boolean influencing;
	@JsonProperty("field_comands")
	private List<List<Object>> comands = new ArrayList<>();
	
	public Comand(boolean cloud, boolean searching, boolean influencing)
	{
		this.cloud = cloud;
		this.searching = searching;
		this.influencing = influencing;
	}
	
	public Comand()
	{
		
	}
	
	public boolean isCloud() {
		return cloud;
	}
	public void setCloud(boolean cloud) {
		this.cloud = cloud;
	}
	public boolean isSearching() {
		return searching;
	}
	public void setSearching(boolean searching) {
		this.searching = searching;
	}
	public boolean isInfluencing() {
		return influencing;
	}
	public void setInfluencing(boolean influencing) {
		this.influencing = influencing;
	}
	public List<List<Object>> getComands() {
		return comands;
	}
	public void setComands(List<List<Object>> comands) {
		this.comands = comands;
	}
}
