package com.dnd.dndTable.creatingDndObject.bagDnd;

public class Armor extends Items
{
	private static final long serialVersionUID = 1L;

	
	public static Armor create(String name)
	{
		Armor armor = new Armor();
		armor.setName(name);
		return armor;
		
	}
}
