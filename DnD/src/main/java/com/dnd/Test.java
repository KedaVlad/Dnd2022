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
		// TODO Auto-generated method stub
		Test t1 = new Test();
		t1.name.nsame ="sdsdsdssd";
		Some s = t1.name;
		s.nsame = "YFYFYFYFYFYFYF";

		System.out.println(s.nsame);
		System.out.println(t1.name.nsame);
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
