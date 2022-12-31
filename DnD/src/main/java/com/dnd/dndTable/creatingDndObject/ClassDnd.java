package com.dnd.dndTable.creatingDndObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.Armors;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack;
import com.dnd.dndTable.creatingDndObject.bagDnd.Tool;
import com.dnd.dndTable.creatingDndObject.bagDnd.Tool.Tools;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.InerFeature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Mechanics;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.CloudComand;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "CLASS_DND")
public class ClassDnd implements Serializable, DndKeyWallet, Source{

	private static final long serialVersionUID = 3219669745475635442L;

	private String className;
	private String myArchetypeClass;
	@JsonIgnore
	private int lvl;
	private Roll diceHp;
	private int firstHp;


	private InerComand[][] growMap;

	@Override
	public String source()
	{
		return classSource + I + className + I + myArchetypeClass + json;

	}

	public int getFirstHp()
	{
		return firstHp;
	}

	public String getMyArchetypeClass() 
	{
		return myArchetypeClass;
	}

	public int getLvl() 
	{
		return lvl;
	}

	public void setLvl(int lvl) 
	{
		this.lvl = lvl;
	}

	public InerComand[][] getGrowMap() 
	{
		return growMap;
	}

	public void setGrowMap(InerComand[][] growMap) 
	{
		this.growMap = growMap;
	}

	public void setMyArchetypeClass(String myArchetypeClass) 
	{
		this.myArchetypeClass = myArchetypeClass;
	}

	public Roll getDiceHp() {
		return diceHp;
	}

	public void setDiceHp(Roll diceHp) {
		this.diceHp = diceHp;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setFirstHp(int firstHp) {
		this.firstHp = firstHp;
	}




	public static void main(String[] args) throws IOException 
	{

		ClassDnd assasin = new ClassDnd();

		assasin.diceHp = Roll.D8;
		assasin.firstHp = 8;

		assasin.className = "Rogue";
		assasin.myArchetypeClass = "Thief";
		assasin.growMap = new InerComand[21][];

		assasin.growMap[0] = new InerComand[] { 
				AddComand.create(
				new Possession("Light Armor", ARMOR),
				new Possession("Simple Weapon", WEAPON),
				new Possession("Hand Crossbows", WEAPON),
				new Possession("Long Swords", WEAPON),
				new Possession("Rapiers", WEAPON),
				new Possession("Short Swords", WEAPON),
				new Possession("Short Swords", WEAPON),
				new Possession("Thieves' Tools", ITEM),
				new Possession("SR Dexterity", STAT),
				new Possession("SR Intelligense", STAT),
				new Possession("SR Intelligense", STAT),
				new Armor(Armors.LEATHER_ARMOR),
				new Weapon(Weapons.DAGGER),
				new Tool(Tools.THIEVES)),

				CloudComand.create("Choose skill", "Choose 4 skills in STATS, from this list and Up them to Proficiency"
				+ "\n" + "Acrobatics"
				+ "\n" + "Investigation"
				+ "\n" + "Athletics"
				+ "\n" + "Mindfulness"
				+ "\n" + "Performance"
				+ "\n" + "Intimidation"
				+ "\n" + "Sleight of hand"
				+ "\n" + "Deception"
				+ "\n" + "Insight"
				+ "\n" + "Stelth"
				+ "\n" + "Persuasion"
				+ "\n" + "After this message you will no longer need - ELIMINATE this."),
				CloudComand.create("Choose item", "In your BAG ADD WEAPON Rapier or Shortsword"),
				CloudComand.create("Choose item1", "In your BAG ADD WEAPON Shortsword or Shortbow + 20 Arrows"),
				CloudComand.create("Choose item2", "In your BAG ADD PACK Dungeoneer's Pack or Burglar's Pack or Explorer's Pack")};
				
		
		assasin.growMap[1] = new InerComand[] { AddComand.create(
				InerFeature.create("Expertise", "At 1st level, choose two of your skill proficiencies, or one of your skill proficiencies and your proficiency with thieves’ tools. Your proficiency bonus is doubled for any ability check you make that uses either of the chosen proficiencies.\n"
						+ "\n"
						+ "At 6th level, you can choose two more of your proficiencies (in skills or with thieves’ tools) to gain this benefit.", CloudComand.create("Choose competense", "Choose 2 of yours SKILLS whith Proficiency and Up to competence")),
				InerFeature.build(Feature.build().name("Sneak Attack").description("Beginning at 1st level, you know how to strike subtly and exploit a foe’s distraction. Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll. The attack must use a finesse or a ranged weapon.\n"
						+ "\n"
						+ "You don’t need advantage on the attack roll if another enemy of the target is within 5 feet of it, that enemy isn’t incapacitated, and you don’t have disadvantage on the attack roll.\n"
						+ "\n"
						+ "The amount of the extra damage increases as you gain levels in this class, as shown in the Sneak Attack column of the Rogue table.")).comand(
						AddComand.create(AttackModification.build()
								.name("Sneak Attack")
								.postAttack(true)
								.damage(new Dice("Sneak Attack", 0, Roll.D6))
								.requirement(WeaponProperties.FENCING),
								AttackModification.build()
								.name("Sneak Attack")
								.postAttack(true)
								.damage(new Dice("Sneak Attack", 0, Roll.D6))
								.requirement(WeaponProperties.THROWING))),
				InerFeature.create("Thieves’ Cant", "During your rogue training you learned thieves’ cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation. Only another creature that knows thieves’ cant understands such messages. It takes four times longer to convey such a message than it does to speak the same idea plainly.\n"
						+ "\n"
						+ "In addition, you understand a set of secret signs and symbols used to convey short, simple messages, such as whether an area is dangerous or the territory of a thieves’ guild, whether loot is nearby, or whether the people in an area are easy marks or will provide a safe house for thieves on the run.", 
						AddComand.create(new Possession("Thieves Jargon", LANGUAGE))))};

		assasin.growMap[2] = new InerComand[] { AddComand.create(Feature.build().name("Cunning Action").description("Starting at 2nd level, your quick thinking and agility allow you to move and act quickly. You can take a bonus action on each of your turns in combat. This action can be used only to take the Dash, Disengage, or Hide action."))};

		assasin.growMap[3] = new InerComand[] { AddComand.create(Feature.build().name("Fast Hands").description("Starting at 3rd level, you can use the bonus action granted by your Cunning Action to make a Dexterity (Sleight of Hand) check, use your thieves’ tools to disarm a trap or open a lock, or take the Use an Object action."),
				new Possession("Poisn Tools", ITEM),
				new Possession("Grimm Tools", ITEM),
				InerFeature.build(Feature.build().name("Second-Story Work").description("When you choose this archetype at 3rd level, you gain the ability to climb faster than normal; climbing no longer costs you extra movement.\n"
						+ "\n"
						+ "In addition, when you make a running jump, the distance you cover increases by a number of feet equal to your Dexterity modifier.")).comand(
						AddComand.create(AttackModification.build()
								.name("Liquidation")
								.crit()
								.requirement(WeaponProperties.FENCING),
								AttackModification.build()
								.name("Liquidation")
								.crit()
								.requirement(WeaponProperties.THROWING))),
				Feature.build().name("Precise Aiming").description("Some des"))};
		
		assasin.growMap[4] = new InerComand[] {  CloudComand.create("lvl up 4", "(LVL 4)Up your 2 stats on 1 or 1 stat on 2"), };
		
		assasin.growMap[5] = new InerComand[] { AddComand.create(Feature.build().name("Uncanny Dodge").description("Starting at 5th level, when an attacker that you can see hits you with an attack, you can use your reaction to halve the attack’s damage against you."))};

		assasin.growMap[6] = new InerComand[] { CloudComand.create("Choose competense5", "(Expertise 6)Choose 2 of yours SKILLS whith Proficiency and Up to competence")};
		
		assasin.growMap[7] = new InerComand[] { AddComand.create(Feature.build().name("Evasion").description("Beginning at 7th level, you can nimbly dodge out of the way of certain area effects, such as an ancient red dragon’s fiery breath or an ice storm spell. When you are subjected to an effect that allows you to make a Dexterity saving throw to take only half damage, you instead take no damage if you succeed on the saving throw, and only half damage if you fail."))};
		
		assasin.growMap[8] = new InerComand[] { CloudComand.create("lvl up 8", "(LVL 8)Up your 2 stats on 1 or 1 stat on 2")};

		assasin.growMap[9] = new InerComand[] { AddComand.create(Feature.build().name("Supreme Sneak").description("Starting at 9th level, you have advantage on a Dexterity (Stealth) check if you move no more than half your speed on the same turn."))};

		assasin.growMap[10] = new InerComand[] { CloudComand.create("lvl up 10", "(LVL 10)Up your 2 stats on 1 or 1 stat on 2")};
		
		assasin.growMap[11] = new InerComand[] { AddComand.create(Feature.build().name("Reliable Talent").description("By 11th level, you have refined your chosen skills until they approach perfection. Whenever you make an ability check that lets you add your proficiency bonus, you can treat a d20 roll of 9 or lower as a 10."))};//!!!!!!!!!!!!!!!!!!!!11

		assasin.growMap[12] = new InerComand[] { CloudComand.create("lvl up 12", "(LVL 12)Up your 2 stats on 1 or 1 stat on 2")};
		
		assasin.growMap[13] = new InerComand[] {  AddComand.create(Feature.build().name("Use Magic Device").description("By 13th level, you have learned enough about the workings of magic that you can improvise the use of items even when they are not intended for you. You ignore all class, race, and level requirements on the use of magic items."))};
		
		assasin.growMap[14] = new InerComand[] { AddComand.create(Feature.build().name("Blindsense").description("Starting at 14th level, if you are able to hear, you are aware of the location of any hidden or invisible creature within 10 feet of you."))};
		
		assasin.growMap[15] = new InerComand[] { AddComand.create(
				InerFeature.create("Slippery Mind", "By 15th level, you have acquired greater mental strength. You gain proficiency in Wisdom saving throws.", 
						AddComand.create(new Possession("SR Wisdom", STAT))))};
		
		assasin.growMap[16] = new InerComand[] { CloudComand.create("lvl up 16", "(LVL 16)Up your 2 stats on 1 or 1 stat on 2")};
		
		assasin.growMap[17] = new InerComand[] { AddComand.create(Feature.build().name("Thief’s Reflexes").description("When you reach 17th level, you have become adept at laying ambushes and quickly escaping danger. You can take two turns during the first round of any combat. You take your first turn at your normal initiative and your second turn at your initiative minus 10. You can’t use this feature when you are surprised."))};
		
		assasin.growMap[18] = new InerComand[] { AddComand.create(Feature.build().name("Elusive").description("Beginning at 18th level, you are so evasive that attackers rarely gain the upper hand against you. No attack roll has advantage against you while you aren’t incapacitated."))};
		
		assasin.growMap[19] = new InerComand[] {  CloudComand.create("lvl up 19", "(LVL 19)Up your 2 stats on 1 or 1 stat on 2")};
		
		assasin.growMap[20] = new InerComand[] {  AddComand.create(Feature.build().name("Stroke of Luck").description("At 20th level, you have an uncanny knack for succeeding when you need to. If your attack misses a target within range, you can turn the miss into a hit. Alternatively, if you fail an ability check, you can treat the d20 roll as a 20.\n"
				+ "\n"
				+ "Once you use this feature, you can’t use it again until you finish a short or long rest."))};
		



		JsonNode node = Json.toJson(assasin);
		String json = Json.stingify(node);
		System.out.println(json);
		
		ClassDnd clazz = Json.fromJson(node, ClassDnd.class);
		
		System.out.println("*************************");
		int i = 0;
		for(InerComand[] pool: clazz.growMap)
		{
			System.out.println("lvl "+i);
			i++;
			for(InerComand comand: pool)
			{
				System.out.println("__________________________");
				System.out.println(comand);
			}
			
		}

		/*Rogue r = Json.fromJson(Json.parse(json), Rogue.class);

		System.out.println(r);
		System.out.println(r.getGrowMap());
		System.out.println(r.getGrowMap().get(0).get(0));

		for(int i = 0; i < r.getGrowMap().size(); i++)
		{
			for(InerComand comand: r.getGrowMap().get(i))
			{

				System.out.println(comand.getComand().get(0).get(0).toString());

			}
		}
		System.out.println(Json.parse(r.getGrowMap().get(0).get(0).toString()));
		String jj = r.getGrowMap().get(0).get(0).toString();
		System.out.println(jj);
		Possession pp = Json.fromJson(Json.parse(jj), Possession.class);
		System.out.println(pp);*/
	}


}


