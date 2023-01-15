package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.Names.TypeDamage;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.rolls.DamageDice;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("WEAPON")
public class Weapon extends Items
{

	private static final long serialVersionUID = 3883278765106047552L;
	
	private int attack;
	private int damage;

	
	public enum WeaponProperties
	{
		UNIVERSAL, LUNG, THROWING, FENCING, TWO_HANDED, AMMUNITION, RELOAD, AVAILABILITY, MILITARY, LONG_RANGE, MELEE, SIMPLE, HEAVY
	}
	
	private Weapons type;
	
	public Weapon() {}
	
	public Weapon(Weapons type)
	{
		this.setName(type.name);
		this.setDescription(descripter(type));
		this.type = type;
	}
	
	private String descripter(Weapons type)
	{
		String answer = type.name + "\n";
		for(AttackModification typeAttack:  type.attackTypes)
		{
			answer += typeAttack.toString() + "\n";
		}
		return answer;
	}
	
	public Weapon attack(int attack)
	{
		this.attack = attack;
		return this;
	}

	public Weapon damage(int damage)
	{
		this.damage = damage;
		return this;
	}
	
	public AttackModification[] getAttacksTypes() {
		return type.attackTypes;
	}




	public int getAttack() {
		return attack;
	}

	public void addAttack(int attack) 
	{
		this.attack += attack;
	}

	public int getDamage() {
		return damage;
	}

	public void addDamage(int damage) 
	{
		this.damage += damage;
	}
	
	public Weapons getType() {
		return type;
	}
	
	public String toString()
	{
		if(attack != 0 || damage != 0) 
		{
			return getName() + "(" + attack + "|" + damage + ")";
		}
		return getName();
	}

	public enum Weapons
	{
		HALBERD("Halberd", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
				WeaponProperties.HEAVY, WeaponProperties.AVAILABILITY, WeaponProperties.MILITARY, WeaponProperties.TWO_HANDED, WeaponProperties.MELEE)),
		
		WARHAMER("Warhamer", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		QUARTERSTAFF("Quarterstaff", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
						WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		BATTLEAXE("Battleaxe", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		MACE("Mace", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE)),
		
		GYTHKA("Gythka", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		GLAIVE("Glaive", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.AVAILABILITY, WeaponProperties.HEAVY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		DOUBLE_BLADET_SCIMITAR("Double-blade scimitar", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4, Roll.D4), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		GREATWSWORD("Greatesword", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)),
		
		MAUL("Maul", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)),
		
		GREATEAXE("Greateaxe", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED)),
		
		LONGBOW("Longbow", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED).ammunition("Arrows")),
		
		LONGSWORD("Longsword", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		DART("Dart", AttackModification.create("Throw", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.THROWING, WeaponProperties.AMMUNITION)),
		
		GREATECLUB("Greateclub", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		CLUB("Club", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG).secondStat(Stat.DEXTERITY)),
		
		BLOWGUN("Blowgun", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 1, TypeDamage.STICKING, Roll.NO_ROLL), 
				WeaponProperties.LONG_RANGE, WeaponProperties.AMMUNITION, WeaponProperties.MILITARY).ammunition("Blowwgun needles")),
		
		YKLWA("Yklwa", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE), 
				AttackModification.create("Throw", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING)),
		
		LANCE("Lance", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY)),
		
		DAGGER("Dagger", AttackModification.create("Melee attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG), 
				AttackModification.create("Throw", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING)),
		
		WAR_PICK("War pick", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE)),
		
		WHIP("Whip", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.FENCING).secondStat(Stat.DEXTERITY)),
		
		SPEAR("Spear", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
						WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED),
				AttackModification.create("Throw", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING)),
		
		SHORTBOW("Shortbow", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Arrows")),
		
		SHORTSWORD("Shortsword", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG).secondStat(Stat.DEXTERITY)),
		
		CROSSBOW_LIGHT("Crossbow, light", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Crossbow bolts")),
		
		LIGHT_HUMMER("Light hummer", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG).secondStat(Stat.DEXTERITY), 
				AttackModification.create("Throw", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING).secondStat(Stat.DEXTERITY)),
		
		MORNINGSTAR("Morningstar", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE)),
		
		PIKE("Pike", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.TWO_HANDED)),
		
		JAVELIN("Javelin", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE),
				AttackModification.create("Throw", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING)),
		
		SLING("Sling", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Sling bullets")),
		
		RAPIER("Rapier", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING).secondStat(Stat.DEXTERITY)),
		
		CROSSBOW_HAND("Crossbow, hand", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.LUNG).ammunition("Crossbow bolts")),
		
		SICKLE("Sickle", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG)),
		
		SCIMITAR("Scimitar", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG).secondStat(Stat.DEXTERITY)),
		
		HANDAXE("Handaxe", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG), 
				AttackModification.create("Throw", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6), 
						WeaponProperties.SIMPLE, WeaponProperties.THROWING)),
		
		TRIDEN("Triden", AttackModification.create("One heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.THROWING), 
				AttackModification.create("Two heand attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
			
		CROSSBOW_HEAVY("Crossbow, heavy", AttackModification.create("Range attack", Stat.DEXTERITY, new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED).ammunition("Crossbow bolts")),
		
		FLAIL("Flail", AttackModification.create("Melee attack", Stat.STRENGTH, new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE));
		
		
		Weapons(String name, AttackModification... attackTypes)
		{
			this.name = name;
			this.attackTypes = attackTypes;
		}
		
		private String name;
		private AttackModification[] attackTypes;
		
		public String toString()
		{
			return name;
		}

	}
	
}
