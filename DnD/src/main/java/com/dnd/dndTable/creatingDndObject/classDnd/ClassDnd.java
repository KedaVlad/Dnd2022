package com.dnd.dndTable.creatingDndObject.classDnd;

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
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.InerFeature;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.Mechanics;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.CloudComand;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.rolls.AttackModification;
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

		assasin.className = "Rogue";
		assasin.myArchetypeClass = "Assasin";
		assasin.growMap = new InerComand[21][];

		assasin.growMap[0] = new InerComand[] { 
				AddComand.create(
				new Possession("Light Armor", armor),
				new Possession("Simple Weapon", weapon),
				new Possession("Hand Crossbows", weapon),
				new Possession("Long Swords", weapon),
				new Possession("Rapiers", weapon),
				new Possession("Short Swords", weapon),
				new Possession("Short Swords", weapon),
				new Possession("Thieves' Tools", item),
				new Possession("SR Dexterity", stat),
				new Possession("SR Intelligense", stat),
				new Possession("SR Intelligense", stat),
				Armor.create("Leather Armor"),
				Weapon.build(WeaponType.DAGGER),
				Pack.create("Thieves' Tools")),

				CloudComand.create(4, "Choose skill", false,
				AddComand.create(new Possession("Acrobatics", stat)),
				AddComand.create(new Possession("Investigation", stat)),
				AddComand.create(new Possession("Athletics", stat)),
				AddComand.create(new Possession("Mindfulness", stat)),
				AddComand.create(new Possession("Performance", stat)),
				AddComand.create(new Possession("Intimidation", stat)),
				AddComand.create(new Possession("Sleight of hand", stat)),
				AddComand.create(new Possession("Deception", stat)),
				AddComand.create(new Possession("Insight", stat)),
				AddComand.create(new Possession("Stelth", stat)),
				AddComand.create(new Possession("Persuasion", stat))),
				
				CloudComand.create(1, "Choose item", false,
				AddComand.create(Weapon.build(WeaponType.RAPIER)),
				AddComand.create(Weapon.build(WeaponType.SHORTSWORD))),
		
				CloudComand.create(1, "Choose item", false,
				AddComand.create(Weapon.build(WeaponType.SHORTBOW)), //+20 arrow
				AddComand.create(Weapon.build(WeaponType.SHORTSWORD))),
				
				CloudComand.create(1, "Choose Pack", false,
				AddComand.create(Pack.create("Dungeoneer's Pack")),
				AddComand.create(Pack.create("Burglar's Pack")),
				AddComand.create(Pack.create("Explorer's Pack")))};
		
		
		assasin.growMap[1] = new InerComand[] { AddComand.create(
				InerFeature.create("Competence", "Blavlavla", CloudComand.create(2, "Choose", false,
						AddComand.create(new Possession("Thieves' Tools", item).competence()),
						AddComand.create(new Possession("", skill).competence()))),
				InerFeature.build(Feature.build().name("Sneak Attack").description("Y HIT WERY WELL")).comand(
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
				InerFeature.create("Thieves Jargon", "Blaasfeefvla", 
						AddComand.create(new Possession("Thieves Jargon", language))))};

		assasin.growMap[2] = new InerComand[] { AddComand.create(Feature.build().name("Tricky Action").description("Bimbombom"))};

		assasin.growMap[3] = new InerComand[] { AddComand.create(Feature.build().name("Additional Holdings").description("Bimweweom"),
				new Possession("Poisn Tools", item),
				new Possession("Grimm Tools", item),
				InerFeature.build(Feature.build().name("Liquidation").description("Liquidation dsksdlflkdsf")).comand(
						AddComand.create(AttackModification.build()
								.name("Liquidation")
								.crit()
								.requirement(WeaponProperties.FENCING),
								AttackModification.build()
								.name("Liquidation")
								.crit()
								.requirement(WeaponProperties.THROWING))),
				Feature.build().name("Precise Aiming").description("Some des"))};
		
		assasin.growMap[4] = new InerComand[] {  CloudComand.lvlUp(null) };
		
		assasin.growMap[5] = new InerComand[] { AddComand.create(Feature.build().name("Incredible Evasion").description("Y HasdIT WERY WasdELL"))};

		assasin.growMap[6] = new InerComand[] { CloudComand.create(2, "Choose", false,
				AddComand.create(new Possession("Thieves' Tools", item).competence()),
				AddComand.create(new Possession("", skill).competence()))};
		
		assasin.growMap[7] = new InerComand[] { AddComand.create(Feature.build().name("Evasiveness").description("Y HIT WEasdRY WELL"))};
		
		assasin.growMap[8] = new InerComand[] { CloudComand.lvlUp(null)};

		assasin.growMap[9] = new InerComand[] { AddComand.create(Feature.build().name("Penetration Master").description("Y HIT WEasweweewdRY WELL"))};

		assasin.growMap[10] = new InerComand[] { CloudComand.lvlUp(null)};
		
		assasin.growMap[11] = new InerComand[] { AddComand.create(Feature.build().name("Reliable Talent").description("Y HfefefeIT WEasdRY WELL"))};//!!!!!!!!!!!!!!!!!!!!11

		assasin.growMap[12] = new InerComand[] { CloudComand.lvlUp(null)};
		
		assasin.growMap[13] = new InerComand[] {  AddComand.create(Feature.build().name("Impostor").description("Y HfefefeIT WEasdRY WELL"))};
		
		assasin.growMap[14] = new InerComand[] { AddComand.create(Feature.build().name("Blind Sight").description("Y HfefefeIT WEasdRY WELL"))};
		
		assasin.growMap[15] = new InerComand[] { AddComand.create(
				InerFeature.create("Slippery Mind", "Blavlavla", 
						AddComand.create(new Possession("SR Wisdom", stat))))};
		
		assasin.growMap[16] = new InerComand[] { CloudComand.lvlUp(null)};
		
		assasin.growMap[17] = new InerComand[] { AddComand.create(Feature.build().name("Death Blow").description("Y HfefefeIT WEasdRY WELL"))};
		
		assasin.growMap[18] = new InerComand[] { AddComand.create(Feature.build().name("Intangibility").description("Y HfefefeIT WEasdRY WELL"))};
		
		assasin.growMap[19] = new InerComand[] {  CloudComand.lvlUp(null)};
		
		assasin.growMap[20] = new InerComand[] {  AddComand.create(Feature.build().name("Luck").description("Y HfefefeIT WEasdRY WELL"))};
		



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


