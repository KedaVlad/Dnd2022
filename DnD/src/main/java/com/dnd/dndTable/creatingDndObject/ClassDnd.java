package com.dnd.dndTable.creatingDndObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names.Stat;
import com.dnd.Source;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable.Time;
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
import com.dnd.dndTable.creatingDndObject.modification.Matrix;
import com.dnd.dndTable.creatingDndObject.modification.pool.SimplePool;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
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

		MagicSoul spellSoul = new MagicSoul();
		Matrix cells = new Matrix();
		cells.setMatrix(new boolean[1][2]);
		spellSoul.setTime(Time.LONG);
		spellSoul.setCells(cells);
		spellSoul.setDepends(Stat.INTELLIGENSE);
		SimplePool<Spell> pool = new SimplePool<Spell>();
		pool.setActiveMaxSize(3);
		spellSoul.setPool(pool);
		SimplePool<Spell> poolCantrips = new SimplePool<Spell>();
		poolCantrips.setActiveMaxSize(3);
		spellSoul.setPoolCantrips(poolCantrips);
		
		assasin.diceHp = Roll.D8;
		assasin.firstHp = 8;

		assasin.className = "Rogue";
		assasin.myArchetypeClass = "Thief";
		assasin.growMap = new InerComand[21][];

		assasin.growMap[0] = new InerComand[] { 
				AddComand.create(
				new Possession("Light Armor"),
				new Possession("Simple Weapon"),
				new Possession("Hand Crossbows"),
				new Possession("Long Swords"),
				new Possession("Rapiers"),
				new Possession("Short Swords"),
				new Possession("Short Swords"),
				new Possession("Thieves' Tools"),
				new Possession("SR Dexterity"),
				new Possession("SR Intelligense"),
				new Possession("SR Intelligense"),
				new Armor(Armors.LEATHER_ARMOR),
				new Weapon(Weapons.DAGGER),
				new Tool(Tools.THIEVES)),

				CloudComand.create("End create", "Now let's finish with all the details, and get acquainted with the game menu.\n\n"
						+
						"1. Choose 4 skills from list below in CHARACTERISTIC > STATS, from this list and Up them to Proficiency"
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
				+ "\n" + "2. In STUFF > BAG > ADD ITEM > WEAPON (Rapier or Shortsword)"
				+ "\n" + "3. In STUFF > BAG > ADD ITEM > WEAPON (Shortsword or Shortbow and 20 Arrows)"
				+ "\n" + "4. In STUFF > BAG > ADD ITEM >  PACK (Dungeoneer's Pack or Burglar's Pack or Explorer's Pack)"
				+ "\n" + "\n" + "After this message you will no longer need - ELIMINATE this.")};
				
		
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
								.requirement(WeaponProperties.LONG_RANGE),
								AttackModification.build()
								.name("Sneak Attack")
								.postAttack(true)
								.damage(new Dice("Sneak Attack", 0, Roll.D6))
								.requirement(WeaponProperties.THROWING))),
				InerFeature.create("Thieves’ Cant", "During your rogue training you learned thieves’ cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation. Only another creature that knows thieves’ cant understands such messages. It takes four times longer to convey such a message than it does to speak the same idea plainly.\n"
						+ "\n"
						+ "In addition, you understand a set of secret signs and symbols used to convey short, simple messages, such as whether an area is dangerous or the territory of a thieves’ guild, whether loot is nearby, or whether the people in an area are easy marks or will provide a safe house for thieves on the run.", 
						AddComand.create(new Possession("Thieves Jargon"))))};
		
		assasin.growMap[2] = new InerComand[] { AddComand.create(Feature.build().name("Cunning Action").description("Starting at 2nd level, your quick thinking and agility allow you to move and act quickly. You can take a bonus action on each of your turns in combat. This action can be used only to take the Dash, Disengage, or Hide action."))};

		assasin.growMap[3] = new InerComand[] { AddComand.create(Feature.build().name("Spellcasting").description("When you reach 3rd level, you augment your martial prowess with the ability to cast spells.\n"
				+ "\n"
				+ "Cantrips\n"
				+ "You learn three cantrips: Mage Hand and two other cantrips of your choice from the wizard spell list. You learn another wizard cantrip of your choice at 10th level.\n"
				+ "\n"
				+ "Spell Slots\n"
				+ "The Arcane Trickster Spellcasting table shows how many spell slots you have to cast your wizard spells of 1st level and higher. To cast one of these spells, you must expend a slot of the spell's level or higher. You regain all expended spell slots when you finish a long rest.\n"
				+ "\n"
				+ "For example, if you know the 1st-level spell Charm Person and have a 1st-level and a 2nd-level spell slot available, you can cast Charm Person using either slot.\n"
				+ "\n"
				+ "Spells Known of 1st Level and Higher\n"
				+ "You know three 1st-level wizard spells of your choice, two of which you must choose from the enchantment and illusion spells on the wizard spell list.\n"
				+ "\n"
				+ "The Spells Known column of the Arcane Trickster Spellcasting table shows when you learn more wizard spells of 1st level or higher. Each of these spells must be an enchantment or illusion spell of your choice, and must be of a level for which you have spell slots. For instance, when you reach 7th level in this class, you can learn one new spell of 1st or 2nd level.\n"
				+ "\n"
				+ "The spells you learn at 8th, 14th, and 20th level can come from any school of magic.\n"
				+ "\n"
				+ "Whenever you gain a level in this class, you can replace one of the wizard spells you know with another spell of your choice from the wizard spell list. The new spell must be of a level for which you have spell slots, and it must be an enchantment or illusion spell, unless you're replacing the spell you gained at 3rd, 8th, 14th, or 20th level from any school of magic.\n"
				+ "\n"
				+ "Spellcasting Ability\n"
				+ "Intelligence is your spellcasting ability for your wizard spells, since you learn your spells through dedicated study and memorization. You use your Intelligence whenever a spell refers to your spellcasting ability. In addition, you use your Intelligence modifier when setting the saving throw DC for a wizard spell you cast and when making an attack roll with one.\n"
				+ "\n"
				+ "Spell save DC = 8 + your proficiency bonus + your Intelligence modifier\n"
				+ "\n"
				+ "Spell attack modifier = your proficiency bonus + your Intelligence modifier"),
				spellSoul.duplicate(),
				Feature.build().name("Mage Hand Legerdemain").description("Starting at 3rd level, when you cast Mage Hand, you can make the spectral hand invisible, and you can perform the following additional tasks with it:\n"
						+ "\n"
						+ "You can stow one object the hand is holding in a container worn or carried by another creature.\n"
						+ "You can retrieve an object in a container worn or carried by another creature.\n"
						+ "You can use thieves' tools to pick locks and disarm traps at range.\n"
						+ "You can perform one of these tasks without being noticed by a creature if you succeed on a Dexterity (Sleight of Hand) check contested by the creature's Wisdom (Perception) check.\n"
						+ "\n"
						+ "In addition, you can use the bonus action granted by your Cunning Action to control the hand."),
				
				
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Precise Aiming").description("As a bonus action, you give yourself advantage on your next attack roll this turn.\n"
						+ "\n"
						+ "You can use this ability only if you haven't moved this turn, and after using it, your speed will be reduced to 0 until the end of the current turn."))};
		
		
		cells.setMatrix(new boolean[1][3]);
		pool.setActiveMaxSize(4);
		assasin.growMap[4] = new InerComand[] {  CloudComand.create("lvl up 4", "(LVL 4)Up your 2 stats on 1 or 1 stat on 2"),
				AddComand.create(spellSoul.duplicate())};
		
		assasin.growMap[5] = new InerComand[] { AddComand.create(
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Uncanny Dodge").description("Starting at 5th level, when an attacker that you can see hits you with an attack, you can use your reaction to halve the attack’s damage against you."))};

		assasin.growMap[6] = new InerComand[] { CloudComand.create("Choose competense5", "(Expertise 6)Choose 2 of yours SKILLS whith Proficiency and Up to competence")};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true}});
		pool.setActiveMaxSize(5);
		
		assasin.growMap[7] = new InerComand[] { AddComand.create(
				spellSoul.duplicate(),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Evasion").description("Beginning at 7th level, you can nimbly dodge out of the way of certain area effects, such as an ancient red dragon’s fiery breath or an ice storm spell. When you are subjected to an effect that allows you to make a Dexterity saving throw to take only half damage, you instead take no damage if you succeed on the saving throw, and only half damage if you fail."))};
		
		pool.setActiveMaxSize(6);
		assasin.growMap[8] = new InerComand[] { CloudComand.create("lvl up 8", "(LVL 8)Up your 2 stats on 1 or 1 stat on 2"),
				AddComand.create(spellSoul.duplicate())};

		assasin.growMap[9] = new InerComand[] { AddComand.create(
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Magical Ambush").description("Starting at 9th level, if you are hidden from a creature when you cast a spell on it, the creature has disadvantage on any saving throw it makes against the spell this turn."))};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true,true}});
		pool.setActiveMaxSize(7);
		poolCantrips.setActiveMaxSize(4);
		
		assasin.growMap[10] = new InerComand[] { CloudComand.create("lvl up 10", "(LVL 10)Up your 2 stats on 1 or 1 stat on 2"),
				AddComand.create(spellSoul.duplicate())};
		
		
		pool.setActiveMaxSize(8);
		
		assasin.growMap[11] = new InerComand[] { AddComand.create(
				spellSoul.duplicate(),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Reliable Talent").description("By 11th level, you have refined your chosen skills until they approach perfection. Whenever you make an ability check that lets you add your proficiency bonus, you can treat a d20 roll of 9 or lower as a 10."))};//!!!!!!!!!!!!!!!!!!!!11

		assasin.growMap[12] = new InerComand[] { CloudComand.create("lvl up 12", "(LVL 12)Up your 2 stats on 1 or 1 stat on 2")};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true,true},{true,true}});
		pool.setActiveMaxSize(9);
		
		assasin.growMap[13] = new InerComand[] {  AddComand.create(
				spellSoul.duplicate(),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Versatile Trickster").description("At 13th level, you gain the ability to distract targets with your Mage Hand. As a bonus action on your turn, you can designate a creature within 5 feet of the spectral hand created by the spell. Doing so gives you advantage on attack rolls against that creature until the end of the turn."))};
		
		pool.setActiveMaxSize(10);
		
		assasin.growMap[14] = new InerComand[] { AddComand.create(
				spellSoul.duplicate(),
				Feature.build().name("Blindsense").description("Starting at 14th level, if you are able to hear, you are aware of the location of any hidden or invisible creature within 10 feet of you."))};
		
		assasin.growMap[15] = new InerComand[] { AddComand.create(
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				InerFeature.create("Slippery Mind", "By 15th level, you have acquired greater mental strength. You gain proficiency in Wisdom saving throws.", 
						AddComand.create(new Possession("SR Wisdom"))))};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true,true},{true,true,true}});
		pool.setActiveMaxSize(11);
		
		assasin.growMap[16] = new InerComand[] { CloudComand.create("lvl up 16", "(LVL 16)Up your 2 stats on 1 or 1 stat on 2")};
		
		assasin.growMap[17] = new InerComand[] { AddComand.create(
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.FENCING),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.LONG_RANGE),
				AttackModification.build()
				.name("Sneak Attack")
				.postAttack(true)
				.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
				.requirement(WeaponProperties.THROWING),
				Feature.build().name("Spell Thief").description("At 17th level, you gain the ability to magically steal the knowledge of how to cast a spell from another spellcaster.\n"
						+ "\n"
						+ "Immediately after a creature casts a spell that targets you or includes you in its area of effect, you can use your reaction to force the creature to make a saving throw with its spellcasting ability modifier. The DC equals your spell save DC. On a failed save, you negate the spell's effect against you, and you steal the knowledge of the spell if it is at least 1st level and of a level you can cast (it doesn't need to be a wizard spell). For the next 8 hours, you know the spell and can cast it using your spell slots. The creature can't cast that spell until the 8 hours have passed.\n"
						+ "\n"
						+ "Once you use this feature, you can't use it again until you finish a long rest."))};
		
		assasin.growMap[18] = new InerComand[] { AddComand.create(Feature.build().name("Elusive").description("Beginning at 18th level, you are so evasive that attackers rarely gain the upper hand against you. No attack roll has advantage against you while you aren’t incapacitated."))};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true,true},{true,true,true},{true}});
		pool.setActiveMaxSize(12);
		
		assasin.growMap[19] = new InerComand[] {  
				 AddComand.create(
						 spellSoul.duplicate(),
						 AttackModification.build()
							.name("Sneak Attack")
							.postAttack(true)
							.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
							.requirement(WeaponProperties.FENCING),
							AttackModification.build()
							.name("Sneak Attack")
							.postAttack(true)
							.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
							.requirement(WeaponProperties.LONG_RANGE),
							AttackModification.build()
							.name("Sneak Attack")
							.postAttack(true)
							.damage(new Dice("Sneak Attack", 0, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6, Roll.D6))
							.requirement(WeaponProperties.THROWING)),
						 
				CloudComand.create("lvl up 19", "(LVL 19)Up your 2 stats on 1 or 1 stat on 2")};
		
		cells.setMatrix(new boolean[][] {{true,true,true,true},{true,true,true},{true,true,true},{true}});
		pool.setActiveMaxSize(13);
		
		assasin.growMap[20] = new InerComand[] {  AddComand.create(
				spellSoul.duplicate(),
				Feature.build().name("Stroke of Luck").description("At 20th level, you have an uncanny knack for succeeding when you need to. If your attack misses a target within range, you can turn the miss into a hit. Alternatively, if you fail an ability check, you can treat the d20 roll as a 20.\n"
				+ "\n"
				+ "Once you use this feature, you can’t use it again until you finish a short or long rest."))};
		



		JsonNode node = Json.toJson(assasin);
		String json = Json.stingify(node);
		System.out.println(json);
		
		ClassDnd clazz = Json.fromJson(node, ClassDnd.class);
		
		System.out.println("*************************");
		int i = 0;
		for(InerComand[] poolComands: clazz.growMap)
		{
			System.out.println("lvl "+i);
			i++;
			for(InerComand comand: poolComands)
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


