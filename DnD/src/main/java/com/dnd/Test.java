package com.dnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.dnd.Names.TypeDamage;
import com.dnd.botTable.Action;
import com.dnd.botTable.CharacterDndBot;
import com.dnd.botTable.GameTable;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.rolls.DamageDice;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.dnd.dndTable.rolls.Formula;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

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


	
	class Dura extends Test
	{
		
	}
	
	class Debil extends Dura
	{
		
	}


	public static void main(String[] args) throws IOException {

		
		FactoryAction a = new FactoryAction();
		FinalAction b = new FinalAction();
		Action c = b;
		Action d = a;
		System.out.println(a instanceof FinalAction); //
		System.out.println(a instanceof FactoryAction);
		System.out.println(b instanceof FinalAction);
		System.out.println(b instanceof FactoryAction);
		System.out.println(c instanceof FinalAction);
		System.out.println(c instanceof FactoryAction);
		System.out.println(d instanceof FinalAction); //
		System.out.println(d instanceof FactoryAction);
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
