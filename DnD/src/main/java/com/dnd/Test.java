package com.dnd;

import java.io.IOException;

import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.localData.JacksonReader;
import com.dnd.localData.Json;

public class Test implements Source {

	String name;
	public Test(String name) {
	this.name = name;
	}
	
	
	public String toString()
	{
		return name;
	}

	public static String getName(Object object)
	{
		return object.toString();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ClassDnd r = Json.fromFileJson(rogueSource + "file.json", Rogue.class);
		
		System.out.println(r);
		System.out.println(r);
		System.out.println(r.getGrowMap());
		System.out.println(r.getGrowMap().get(0).get(0));
		
	
		InerComand comand = r.getGrowMap().get(0).get(0);
		
		System.out.println(comand.getKey() + comand.getComand());
	}

}
