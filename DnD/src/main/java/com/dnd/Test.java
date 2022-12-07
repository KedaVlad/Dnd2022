package com.dnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.telegram.telegrambots.meta.api.objects.Message;

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

	private Some name;
	private int age;
	private boolean b;
	public Test() {
name = new Some();
	}

	class Some
	{
		String nsame;
	}




	public static void main(String[] args) throws IOException {
		
		
		
		List<Integer> some = new ArrayList<>();
		
		
		some.add(4);
		some.add(12);
		some.add(1, 123);
		
		System.out.println(some);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
