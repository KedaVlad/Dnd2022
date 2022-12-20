package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;

import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;

public class Items implements Serializable, ObjectDnd, Source{
	
	private static final long serialVersionUID = -1353539867889183740L;
	
	private String name;
	private String description;
	
	public Items() {}

	
	public String getName() {
		return name;
	}
	public String toString() {
		return getName();
	}

	@Override
	public String source() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
}
