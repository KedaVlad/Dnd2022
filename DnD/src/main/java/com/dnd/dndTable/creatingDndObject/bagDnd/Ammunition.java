package com.dnd.dndTable.creatingDndObject.bagDnd;

public class Ammunition extends Items
{
	private static final long serialVersionUID = 1L;

	public Ammunition(AmmunitionType type)
	{
		this.setName(type.name);
	}
	public enum AmmunitionType 
	{
		SLING_BULLETS("Sling bullets"),
		ARROWS("Arrows"),
		BLOWWGUN_NEEDLES("Blowwgun needles"),
		CROSSBOW_BOLTS("Crossbow bolts");
		
		AmmunitionType(String name)
		{
			this.name = name;
		}
		private String name;
		public String toString()
		{
			return name;
		}
	}
}
