package com.dnd.dndTable.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InerComand implements Serializable {

	
	private static final long serialVersionUID = -5446546498879076199L;
	
	private boolean cloud;
	private boolean effect;
	private String key;
	
	private List<List<Object>> comand = new ArrayList<>();
	
	public InerComand(boolean cloud, String key) 
	{
		this.cloud = cloud;
		this.key = key;
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

	public String getKey() 
	{
		return key;
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

}
