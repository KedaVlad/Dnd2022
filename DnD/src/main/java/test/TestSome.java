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
		String some = "012948375SOME";
		String second = "asfasfafs";
		String[] pool = {some, second};
		
		for(String key: pool)
		{
		String regex = "(\\d{9})(.+)";
		
		System.out.println(key);
		if(key.matches(regex))
		{
			System.out.println(key.replaceAll(regex, "$2"));
		}
		else
		{
			System.out.println(key);
		}
	}
}
}
