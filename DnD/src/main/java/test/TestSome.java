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
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.InerComadDeserializer;
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

	
	protected int age = 4;
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {

		TestSome test = new A();
		
		JsonNode node = Json.toJson(test);
	
		String string = Json.stingify(node);
		
		TestSome test1 = Json.fromJson(node, TestSome.class);
		
		if(test1 instanceof A)
		{
			A a = (A) test1;
			System.out.println(a.name + a.age);
		}
		else
		{
			System.out.println(false);
		}
		
		
		
		
	//	node.path("name")

		
	}
}

