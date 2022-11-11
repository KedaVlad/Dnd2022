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

		Test test = new Test();
		test.setName("bebra");
		test.setAge(5);
		test.setB(false);
		
		Test test2 = new Test();
		test2.setName("Bimbom");
		test2.setAge(222);
		test2.setB(true);
		
		Test[] tt = {test, test2};
		JsonNode j = Json.toJson(tt);
		System.out.println(j);
		String name = "Bimbom";
	System.out.println(j.findValue("name"));
	
	System.out.println(j.get(name));
	
	System.out.println(j.findParent("name"));
	
	
	
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
