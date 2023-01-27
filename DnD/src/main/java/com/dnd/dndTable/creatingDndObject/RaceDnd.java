package com.dnd.dndTable.creatingDndObject;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.factory.inerComands.UpComand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


public class RaceDnd implements Serializable, KeyWallet{

	private static final long serialVersionUID = -7603608846317166137L;

	private int speed;
	private String raceName;
	private String subRace;
	private InerComand[] specials;


	public RaceDnd(String raceName, String subRace, int speed) 
	{
		this.raceName = raceName;
		this.subRace = subRace;
		this.speed = speed;
	}

	public RaceDnd() {}

	public String getRaceName() 
	{
		return raceName;
	}

	public String getSubRace() 
	{
		return subRace;
	}

	public InerComand[] getSpecials() 
	{
		return specials;
	}

	public void setSpecials(InerComand[] specials) 
	{
		this.specials = specials;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}


	public static void main(String[] args) throws IOException {

		RaceDnd gnome = new RaceDnd("Gnome", "Forest", 25);

		gnome.specials = new InerComand[]
				{
						UpComand.create("Intelligence", STAT, 2),
						UpComand.create("Dexterity", STAT, 1),
						AddComand.create(
								Feature.build().name("Natural Illusion").description("Natural Illusion"),
								Feature.build().name("Communication with small animals").description("Communication with small animals"),
								Feature.build().name("Dark vision").description("Accustomed to life underground, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray."),
								Feature.build().name("Gnome cunning").description("You have advantage on all Intelligence, Wisdom, and Charisma saving throws against magic.")
								)
				};

		JsonNode node = Json.toJson(gnome);
		System.out.println(Json.stingify(node));
		
		RaceDnd some = Json.fromJson(node, RaceDnd.class);
		System.out.println(some);

	}
}

