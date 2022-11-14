package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names.SaveRoll;
import com.dnd.Names.Skill;
import com.dnd.Names.Stat;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class Feature implements Serializable, KeyWallet
{
	
	private static final long serialVersionUID = 5053270361827778941L;
	
	private String name;
	private String description;
	private boolean active;
	private Cast cast;

	enum For 
	{
		BATTLE, SPELL, OTHER
	}
	
	private For depend;
	
	private List<InerComand> inerComands;
	

	public Feature(String name)
	{
		this.name = name;
	}
	
	public Feature() {}
	
	public String cast()
	{
		return null;
	}
	
	public String getName() 
	{
		return name;
	}

	public String getDescription() 
	{
		return description;
	}
	
	public String toString() 
	{
		return name + " - "+ description;
	}

	public List<InerComand> getInerComands() {
		return inerComands;
	}

	public void setInerComands(List<InerComand> inerComands) {
		this.inerComands = inerComands;
	}
	
	public static void main(String[] args) throws JsonProcessingException 
	{
		
		/*Map<String, Feature> features = new HashMap<>();
		
		Feature feature = new Feature();
		
		feature.name = "Competence";
		feature.description = "Choose your two skill proficiencies or one skill proficiency and thieves' tools proficiency. Your proficiency bonus is doubled for all ability checks you make using any of the chosen proficiencies. At 6th level, you can choose two more proficiencies (skills or thieves' tools) to gain the same benefit.";
		feature.inerComands = new ArrayList<>();
		InerComand comand = new InerComand();
		comand.setCloud(true);
		comand.setKey(competenseKey);
		comand.getComand().add(new ArrayList<>());
		comand.getComand().add(new ArrayList<>());
		comand.getComand().get(0).add(2);
		comand.getComand().get(0).add("Choose your two skill proficiencies or one skill proficiency and thieves' tools proficiency.Your proficiency bonus is doubled for all ability checks you make using any of the chosen proficiencies.");
		
		comand.getComand().get(1).add(Skill.ACROBATICS.toString());
		comand.getComand().get(1).add(Skill.ANIMAL_HANDING.toString());
		comand.getComand().get(1).add(Skill.ARCANA.toString());
		comand.getComand().get(1).add(Skill.ATHLETIX.toString());
		comand.getComand().get(1).add(Skill.DECEPTION.toString());
		comand.getComand().get(1).add(Skill.HISTORY.toString());
		comand.getComand().get(1).add(Skill.INSIGHT.toString());
		comand.getComand().get(1).add(Skill.INTIMIDATION.toString());
		comand.getComand().get(1).add(Skill.INVESTIGATION.toString());
		comand.getComand().get(1).add(Skill.MEDICINE.toString());
		comand.getComand().get(1).add(Skill.NATURE.toString());
		comand.getComand().get(1).add(Skill.PERCEPTION.toString());
		comand.getComand().get(1).add(Skill.PERFORMANCE.toString());
		comand.getComand().get(1).add(Skill.PERSUASION.toString());
		comand.getComand().get(1).add(Skill.RELIGION.toString());
		comand.getComand().get(1).add(Skill.SLEIGHT_OF_HAND.toString());
		comand.getComand().get(1).add(Skill.STELTH.toString());
		comand.getComand().get(1).add(Skill.SURVIVAL.toString());
		
		feature.inerComands.add(comand);
		features.put(feature.name, feature);*/
		
		Feature feature = new Feature();
		
		feature.name = "Luck";
		feature.description = "If your attack misses a target that is within range, you can change the miss to a hit. Alternatively, if you fail an ability check, you can change the result rolled on a d20 to \"20\". Once you use this skill, you cannot use it again until you complete a short or long rest.";
				/*InerComand c1 = new InerComand(false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(SaveRoll.SR_WISDOM.toString());
		feature.inerComands = new ArrayList<>();
		feature.inerComands.add(c1);*/
		System.out.println( Json.stingify(Json.toJson(feature)));
		
	}

	public For[] getDepend() {
		return depend;
	}

	public void setDepend(For[] depend) {
		this.depend = depend;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Cast getCast() {
		return cast;
	}

	public void setCast(Cast cast) {
		this.cast = cast;
	}
	

}
