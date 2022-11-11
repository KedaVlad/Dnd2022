package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Log;
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
	private String act;
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

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
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
		
		feature.name = "Precise Aiming";
		feature.description = "As a bonus action, you can give yourself advantage on your next attack roll this turn. You can use this bonus action only if you didn't move during this turn, and after using the bonus action, your speed will be 0 until the end of the current turn"
				+ "";
		System.out.println( Json.stingify(Json.toJson(feature)));
		
	}
	

}
