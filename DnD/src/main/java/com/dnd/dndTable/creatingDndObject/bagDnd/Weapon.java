package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.util.List;

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
		if(type.secondType == null)
		{
			answer += type.firstType.toString() + "\n";
		}
		else
		{
			answer += type.firstType.toString() + "\n";
			answer += type.secondType.toString() + "\n";
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
	
	public AttackModification getSecondType() {
		return type.secondType;
	}

	public AttackModification getFirstType() {
		return type.firstType;
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
		HALBERD("Halberd", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
				WeaponProperties.HEAVY, WeaponProperties.AVAILABILITY, WeaponProperties.MILITARY, WeaponProperties.TWO_HANDED, WeaponProperties.MELEE), null),
		
		WARHAMER("Warhamer", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		QUARTERSTAFF("Quarterstaff", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
						WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		BATTLEAXE("Battleaxe", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		MACE("Mace", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE), null),
		
		GYTHKA("Gythka", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED), null),
		
		GLAIVE("Glaive", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.AVAILABILITY, WeaponProperties.HEAVY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED), null),
		
		DOUBLE_BLADET_SCIMITAR("Double-blade scimitar", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4, Roll.D4), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED), null),
		
		GREATWSWORD("Greatesword", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED), null),
		
		MAUL("Maul", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D6, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED), null),
		
		GREATEAXE("Greateaxe", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED), null),
		
		LONGBOW("Longbow", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED).ammunition("Arrows"), null),
		
		LONGSWORD("Longsword", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D10), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		DART("Dart", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.THROWING, WeaponProperties.AMMUNITION), null),
		
		GREATECLUB("Greateclub", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED), null),
		
		CLUB("Club", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG), null),
		
		BLOWGUN("Blowgun", AttackModification.create("Attack", new DamageDice("Weapon base", 1, TypeDamage.STICKING, Roll.NO_ROLL), 
				WeaponProperties.LONG_RANGE, WeaponProperties.AMMUNITION, WeaponProperties.MILITARY).ammunition("Blowwgun needles"), null),
		
		YKLWA("Yklwa", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.THROWING, WeaponProperties.MELEE), null),
		
		LANCE("Lance", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D12), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY), null),
		
		DAGGER("Dagger", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.THROWING, WeaponProperties.FENCING, WeaponProperties.LUNG), null),
		
		WAR_PICK("War pick", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), null),
		
		WHIP("Whip", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.FENCING), null),
		
		SPEAR("Spear", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.THROWING), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
						WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
		
		SHORTBOW("Shortbow", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Arrows"), null),
		
		SHORTSWORD("Shortsword", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG), null),
		
		CROSSBOW_LIGHT("Crossbow, light", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Crossbow bolts"), null),
		
		LIGHT_HUMMER("Light hummer", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.THROWING, WeaponProperties.FENCING, WeaponProperties.LUNG), null),
		
		MORNINGSTAR("Morningstar", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), null),
		
		PIKE("Pike", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.AVAILABILITY, WeaponProperties.TWO_HANDED), null),
		
		JAVELIN("Javelin", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.THROWING), null),
		
		SLING("Sling", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE).ammunition("Sling bullets"), null),
		
		RAPIER("Rapier", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING), null),
		
		CROSSBOW_HAND("Crossbow, hand", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.LUNG).ammunition("Crossbow bolts"), null),
		
		SICKLE("Sickle", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D4), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.LUNG), null),
		
		SCIMITAR("Scimitar", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.FENCING, WeaponProperties.LUNG), null),
		
		HANDAXE("Handaxe", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CHOPPING, Roll.D6), 
				WeaponProperties.SIMPLE, WeaponProperties.MELEE, WeaponProperties.THROWING, WeaponProperties.LUNG), null),
		
		TRIDEN("Triden", AttackModification.create("One heand attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D6), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.THROWING), 
				AttackModification.create("Two heand attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D8), 
						WeaponProperties.MILITARY, WeaponProperties.MELEE, WeaponProperties.TWO_HANDED)),
			
		CROSSBOW_HEAVY("Crossbow, heavy", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.STICKING, Roll.D10), 
				WeaponProperties.MILITARY, WeaponProperties.AMMUNITION, WeaponProperties.LONG_RANGE, WeaponProperties.HEAVY, WeaponProperties.TWO_HANDED).ammunition("Crossbow bolts"), null),
		
		FLAIL("Flail", AttackModification.create("Attack", new DamageDice("Weapon base", 0, TypeDamage.CRUSHING, Roll.D8), 
				WeaponProperties.MILITARY, WeaponProperties.MELEE), null);
		
		
		Weapons(String name, AttackModification firstType, AttackModification secondType)
		{
			this.name = name;
			this.firstType = firstType;
			this.secondType = secondType;
		}
		
		private String name;
		private AttackModification firstType;
		private AttackModification secondType;
		
		public String toString()
		{
			return name;
		}
		
	}
	
}
