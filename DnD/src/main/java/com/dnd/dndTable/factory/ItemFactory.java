package com.dnd.dndTable.factory;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.botTable.actions.factoryAction.FinalTargetAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Ammunition;
import com.dnd.dndTable.creatingDndObject.bagDnd.Ammunition.Ammunitions;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.Armors;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack.Packs;
import com.dnd.dndTable.creatingDndObject.bagDnd.Tool;
import com.dnd.dndTable.creatingDndObject.bagDnd.Tool.Tools;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;

public class ItemFactory 
{
	final static long key = 623478675;

	public static Action startCreate() 
	{
		String name = "CreateItem";
		String text = "Which item you take?";
		return FactoryAction.create(name, key, false, text, new String[][]
				{
			{"WEAPON","AMNUNITION"},
			{"TOOL","PACK"},
			{"ARMOR"},
			{"ELSE"}
			});
	}
	
	public static Action execute(FactoryAction action) 
	{
		String target = action.getLocalData().get(0);
		if(target.equals("WEAPON"))
		{
			return weaponCreator(action);
		}
		else if(target.equals("AMNUNITION"))
		{
			return ammunitionCreator(action);
		}
		else if(target.equals("TOOL"))
		{
			return toolCreator(action);
		}
		else if(target.equals("PACK"))
		{
			return packsCreator(action);
		}
		else if(target.equals("ARMOR"))
		{
			return armorCreator(action);
		}
		else if(target.equals("ELSE"))
		{
			return elseCreator(action);
		}
		Log.add("ERROR ITEM FACTORY EXECUTE");
		return null;
	}
	
	private static Action elseCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseNameItems(action);
		case 2:
			return  chooseDescriptionItems(action);
		case 3:
			return  agreedItems(action);
		
		}
		return null;
	}
	
	private static Action chooseNameItems(FactoryAction action) 
	{
		action.setName("chooseNameItems");
		action.setText("How would you name this item?(Write)");
		action.setNextStep(null);
		return action.setMediator();
	}

	private static Action chooseDescriptionItems(FactoryAction action) 
	{
		action.setName("chooseDescriptionItems");
		action.setText(action.getLocalData().get(1) + "? okey... Give me some description which you want to see when you will look in your bag.(Write)");
		action.setNextStep(null);
		return action.setMediator();
	}

	private static Action agreedItems(FactoryAction action) 
	{
		Items item = new Items();
		item.setName(action.getLocalData().get(1));
		item.setDescription(action.getLocalData().get(2));
		action.setName("checkCondition");
		action.setText(item.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, item);
	}


	private static Action ammunitionCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseTypeAmmunition(action);
		case 2:
			return  agreedAmmunition(action);
		
		}
		return null;
	}

	private static Action chooseTypeAmmunition(FactoryAction action) 
	{
		action.setName("chooseTypeAmmunition");
		action.setText("What ammunition is it?");
		
		action.setNextStep(getArray(ammunitionArrayConvertor(Ammunitions.values())));
		return action;
	}
	
	private static String[] ammunitionArrayConvertor(Ammunitions[] array)
	{
		String[] answer = new String[array.length];
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = array[i].toString();
		}
		return answer;
	}
	
	private static Action agreedAmmunition(FactoryAction action) 
	{
		Ammunition weapon = new Ammunition(targetAmmunition(action.getLocalData().get(1)));
		action.setName("checkCondition");
		action.setText(weapon.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, weapon);
	}
	
	private static Ammunitions targetAmmunition(String name)
	{
		for(Ammunitions type: Ammunitions.values())
		{
			if(type.toString().equals(name)) return type;
		}
		Log.add("ERROR TARGET WEAPON IN ITEM FACTORY");
		return null;
	}
	
	
	private static Action weaponCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseTypeWeapon(action);
		case 2:
			return  agreedWeapon(action);
		
		}
		return null;
	}

	private static Action chooseTypeWeapon(FactoryAction action) 
	{
		action.setName("ChooseTypeWeapon");
		action.setText("What weapon is it?");
		
		action.setNextStep(getArray(weaponArrayConvertor(Weapons.values())));
		return action;
	}
	
	private static String[] weaponArrayConvertor(Weapons[] array)
	{
		String[] answer = new String[array.length];
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = array[i].toString();
		}
		return answer;
	}
	
	private static Action agreedWeapon(FactoryAction action) 
	{
		Weapon weapon = new Weapon(targetWeapon(action.getLocalData().get(1)));
		action.setName("checkCondition");
		action.setText(weapon.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, weapon);
	}
	
	private static Weapons targetWeapon(String name)
	{
		for(Weapons type: Weapons.values())
		{
			if(type.toString().equals(name)) return type;
		}
		Log.add("ERROR TARGET WEAPON IN ITEM FACTORY");
		return null;
	}
	
	
	private static Action toolCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseTypeTool(action);
		case 2:
			return  agreedTool(action);
		
		}
		return null;
	}

	private static Action chooseTypeTool(FactoryAction action) 
	{
		action.setName("chooseTypeTool");
		action.setText("What tool is it?");
		
		action.setNextStep(getArray(toolArrayConvertor(Tools.values())));
		return action;
	}
	
	private static String[] toolArrayConvertor(Tools[] array)
	{
		String[] answer = new String[array.length];
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = array[i].toString();
		}
		return answer;
	}
	
	private static Action agreedTool(FactoryAction action) 
	{
		Tool weapon = new Tool(targetTool(action.getLocalData().get(1)));
		action.setName("checkCondition");
		action.setText(weapon.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, weapon);
	}
	
	private static Tools targetTool(String name)
	{
		for(Tools type: Tools.values())
		{
			if(type.toString().equals(name)) return type;
		}
		Log.add("ERROR TARGET WEAPON IN ITEM FACTORY");
		return null;
	}
	
	
	private static Action packsCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseTypePack(action);
		case 2:
			return  agreedPack(action);
		
		}
		return null;
	}

	private static Action chooseTypePack(FactoryAction action) 
	{
		action.setName("ChooseTypePack");
		action.setText("What pack is it?");
		
		action.setNextStep(getArray(packsArrayConvertor(Packs.values())));
		return action;
	}
	
	private static String[] packsArrayConvertor(Packs[] array)
	{
		String[] answer = new String[array.length];
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = array[i].toString();
		}
		return answer;
	}
	
	private static Action agreedPack(FactoryAction action) 
	{
		Pack weapon = new Pack(targetPack(action.getLocalData().get(1)));
		action.setName("checkCondition");
		action.setText(weapon.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, weapon);
	}
	
	private static Packs targetPack(String name)
	{
		for(Packs type: Packs.values())
		{
			if(type.toString().equals(name)) return type;
		}
		Log.add("ERROR TARGET WEAPON IN ITEM FACTORY");
		return null;
	}
	

	private static Action armorCreator(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseTypeArmor(action);
		case 2:
			return  agreedArmor(action);
		
		}
		return null;
	}

	private static Action chooseTypeArmor(FactoryAction action) 
	{
		action.setName("ChooseTypeWeapon");
		action.setText("What armor is it?");
		
		action.setNextStep(getArray(armorArrayConvertor(Armors.values())));
		return action;
	}
	
	private static String[] armorArrayConvertor(Armors[] array)
	{
		String[] answer = new String[array.length];
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = array[i].toString();
		}
		return answer;
	}
	
	private static Action agreedArmor(FactoryAction action) 
	{
		Armor weapon = new Armor(targetArmor(action.getLocalData().get(1)));
		action.setName("checkCondition");
		action.setText(weapon.getDescription());
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalTargetAction.create(action, weapon);
	}
	
	private static Armors targetArmor(String name)
	{
		for(Armors type: Armors.values())
		{
			if(type.toString().equals(name)) return type;
		}
		Log.add("ERROR TARGET WEAPON IN ITEM FACTORY");
		return null;
	}
	
	
	public static void finish(FinalTargetAction action, CharacterDnd actualGameCharacter) 
	{
		actualGameCharacter.getBody().getMyBags().get(0).add((Items)action.getTarget());
	}
	
	public static String[][] getArray(String[] array) 
	{
		String[] all =  array;
		List<String[]> buttons = new ArrayList<>();
		
		for(int i = 1; i <= all.length; i += 3)
		{
			if(((i + 1) > all.length)
					&&((i + 2) > all.length)) 
			{
				buttons.add(new String[]{all[i - 1]});	
			}
			else if((i + 2) > all.length)
			{
				buttons.add(new String[]{all[i - 1], all[i]});	
			}
			else
			{
				buttons.add(new String[]{all[i - 1], all[i], all[i+1]});		
			}
		}
		
		String[][] allRaces = buttons.toArray(new String[buttons.size()][]);
		
		return allRaces;
	}
}
