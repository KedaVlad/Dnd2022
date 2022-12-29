package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.dnd.Names.TypeDamage;
import com.dnd.botTable.Action;
import com.dnd.botTable.CharacterDndBot;
import com.dnd.botTable.GameTable;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.botTable.actions.factoryAction.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.ClassDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.ClassArmor;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.InerFeature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Mechanics;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.rolls.DamageDice;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.dnd.dndTable.rolls.Formula;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = A.class, name = "some")})

public class TestSome {

	public A a;
	
	
	
	
	
	static void chekStatRandomiser()
	{
		int i = 0;
		while(true)
		{
			i++;
			int j = Dice.randomStat();
			System.out.println(j);
			if(j < 3) break;
			if(i >= 1000) break;
		}
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		A a = new A();
		a.name = "AAAAAAAAAAAAAAA";
		System.out.println(a.name);
		TestSome test = new TestSome();
		test.a = a;
		test.a.name = "BBBBBBBBBBBBBBBB";
		System.out.println(a.name);
		System.out.println(test.a.name);
		B b = new B();
		b.a = test.a;
		b.a.name = "CCCCCCCCCCCCCCCCCC";
		System.out.println(a.name);
		System.out.println(b.a.name);
		C c = new C();
		c.t = test;
		c.t.a.name = "DDDDDDDDDDDDDDDDDDDD";
		System.out.println(a.name);
		System.out.println(test.a.name);
		System.out.println(c.t.a.name);
		a.name = "EEEEEEEEEEEEEEE";
		System.out.println(a.name);
		System.out.println(test.a.name);
		System.out.println(b.a.name);
		System.out.println(c.t.a.name);
		System.out.println(ClassArmor.HEAVY);
		System.out.println(ClassArmor.HEAVY.toString());
	}
}

class A
{
	String name;
}

class B
{
	A a;
	String second;
	
}

class C
{
	TestSome t;
}