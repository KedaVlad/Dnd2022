package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.util.List;

import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;

public class Weapon extends Items
{

	private static final long serialVersionUID = 3883278765106047552L;

	public enum WeaponProperties
	{
		UNIVERSAL, LUNG, THROWING, FENCING, TWO_HANDED, AMMUNITION, RELOAD, AVAILABILITY, MILITARY, LONG_RANGE, MELEE, SIMPLE, HEAVY
	}
	public enum WeaponType
	{
		HALBERD, WARHAMER, QUARTERSTAFF, BATTLEAXE, MACE, GYTHKA,
		GLAIVE, DOUBLE_BLADET_SCIMITAR, GREATWSWORD, MAUL, GREATEAXE,
		LONGBOW, LONGSWORD, DART, GREATECLUB, CLUB, BLOWGUN, YKLWA, LANCE,
		DAGGER, WAR_PICK, WHIP, SPEAR, SHORTBOW, SHORTSWORD, CROSSBOW_LIGHT,
		LIGHT_HUMMER, MORNINGSTAR, PIKE, JAVELIN, SLING, RAPIER, CROSSBOW_HAND,
		SICKLE, NET, SCIMITAR, HANDAXE, TRIDEN, CROSSBOW_HEAVY, FLAIL 
	}

	private AttackModification firstType;
	private AttackModification secondType;

	public Weapon(String name) 
	{
		super(name);
	}

	public AttackModification getSecondType() {
		return secondType;
	}

	public void setSecondType(AttackModification secondType) {
		this.secondType = secondType;
	}

	public AttackModification getFirstType() {
		return firstType;
	}

	public void setFirstType(AttackModification firstType) {
		this.firstType = firstType;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return weapon;
	}
}
