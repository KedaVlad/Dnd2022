package com.dnd.dndTable.creatingDndObject.bagDnd;

import com.dnd.Names.Stat;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ARMOR")
public class Armor extends Items
{
	private static final long serialVersionUID = 1L;
	private TypeArmor type;
	
	public Armor(TypeArmor type)
	{
		this.setName(type.name);
		this.type = type;
	}
	
	public TypeArmor getType() 
	{
		return type;
	}

	public enum ClassArmor
	{
		LIGHT, HEAVY, MEDIUM, SHIELD
	}
	
	public enum TypeArmor
	{
		SHIELD("Shield",ClassArmor.SHIELD, 2, 0, 0),
		STUDDED_LEATHER_ARMOR("Studded Leather Armor", ClassArmor.LIGHT, 12, 0, 7),
		LEATHER_ARMOR("Leather Armor",ClassArmor.LIGHT , 11, 0, 7),
		RING_MAIL("Ring Mail",ClassArmor.HEAVY , 14, 0, 0),
		CHAIN_MAIL("Chain Mail",ClassArmor.HEAVY , 16, 13, 7),
		CHAIN_SHORT("Chain Shirt", ClassArmor.MEDIUM , 13, 0, 2),
		PLATE("Plate",ClassArmor.HEAVY , 18, 15, 0),
		PREASTPLATE("Breastplate", ClassArmor.MEDIUM ,14, 0, 2),
		SPLINT_ARMOR("Splint armor", ClassArmor.HEAVY , 17,15,0),
		HALF_PLATE("Half plate", ClassArmor.MEDIUM , 15, 15, 2),
		PADDED_ARMOR("Padded armor", ClassArmor.LIGHT , 11, 0, 7),
		SCALE_MAIL("Scale Mail", ClassArmor.MEDIUM , 14, 0 , 2),
		HIDE_ARMOR("Hide armor", ClassArmor.MEDIUM,12, 0 ,2);
		
		
		
		TypeArmor(String name, ClassArmor clazz, int base, int requirment, int max)
		{
			this.name = name;
			this.setClazz(clazz);
			this.baseArmor = base;
			this.requitment = requirment;
			this.statDependBuff = base + max;
		}
		
		private String name;
		private ClassArmor clazz;
		private int requitment;
		private int baseArmor;
		private int statDependBuff;
		public String toString()
		{
			return name;
		}
		public int getRequitment() {
			return requitment;
		}
		public int getStatDependBuff() {
			return statDependBuff;
		}
		public int getBaseArmor() {
			return baseArmor;
		}
		public ClassArmor getClazz() {
			return clazz;
		}
		public void setClazz(ClassArmor clazz) {
			this.clazz = clazz;
		}
		
	}
}
