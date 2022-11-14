package com.dnd;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.dnd.botTable.CharacterDndBot;
import com.dnd.botTable.GameTable;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test implements Source {

	private String name;
	private int age;
	private boolean b;
	public Test() {
	
	}
	
	
	public String toString()
	{
		return getName();
	}

	public static String getName(Object object)
	{
		return object.toString();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		InerComand comand = new InerComand();
		comand.setCloud(true);
		comand.setKey("-+");
		
		
	if(comand.getClass().equals(Object.class))
	{
		System.out.println("YeeeahRight");
	}
	
	
	}
	
	


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public boolean isB() {
		return b;
	}


	public void setB(boolean b) {
		this.b = b;
	}

}
