package com.dnd.dndTable.factory.inerComands;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "COMAND")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "ADD_COMAND"),
	@JsonSubTypes.Type(value = UpComand.class, name = "UP_COMAND"),
	@JsonSubTypes.Type(value = ProfComand.class, name = "PROF_COMAND"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "CLOUD_COMAND")})

public abstract class InerComand implements Serializable {

	
	private static final long serialVersionUID = -5446546498879076199L;
/*
	private boolean cloud;
	private boolean effect;
	private boolean back;
	private long key;

	private List<List<Object>> comand;


	public long getKey() 
	{
		return this.key;
	}

	public ObjectDnd getTarget() 
	{
		return target;
	}
	
	public InerComand(boolean cloud, boolean effect, String key) 
	{
		this.cloud = cloud;
		this.effect = effect;
		this.key = key;
		comand = new ArrayList<>();
		comand.add(new ArrayList<>());
	}
	
	public InerComand() {}

	public boolean isCloud() 
	{
		return cloud;
	}

	public void setCloud(boolean cloud) 
	{
		this.cloud = cloud;
	}


	public void setKey(String key) 
	{
		this.key = key;
	}

	public List<List<Object>> getComand() 
	{
		return comand;
	}

	public void setComand(List<List<Object>> comand) 
	{
		this.comand = comand;
	}

	public boolean isEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}
*/
}
