package com.dnd.dndTable.factory.inerComands;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("UP_COMAND")
public class UpComand extends InerComand {

	private static final long serialVersionUID = 1L;

	private String name;
	private long key;
	private int value;
	
	public static UpComand create(String name, long key, int value)
	{
		UpComand comand = new UpComand();
		comand.name =name;
		comand.key = key;
		comand.value = value;
		return comand;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
