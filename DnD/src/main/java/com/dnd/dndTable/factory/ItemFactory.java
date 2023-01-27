package com.dnd.dndTable.factory;

import java.util.ArrayList;
import java.util.List;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
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

public class ItemFactory implements KeyWallet, ButtonName
{
	final static long KEY = ITEM_FACTORY_K;

	public static Act execute(Action action, CharacterDnd character) 
	{
		int condition = 0;
		if(action.getAnswers() != null) condition = action.getAnswers().length;
		if(condition == 0) return startCreate();
		String target = action.getAnswers()[0];
		if(condition == 4)
		{
			return finish(action, character);
		}
		else if(condition == 3 && !target.equals(ELSE_B))
		{
			return finish(action, character);
		}	
		else if(target.equals(WEAPON_B))
		{
			return weaponCreator(action);
		}
		else if(target.equals(AMNUNITION_B))
		{
			return ammunitionCreator(action);
		}
		else if(target.equals(TOOL_B))
		{
			return toolCreator(action);
		}
		else if(target.equals(PACK_B))
		{
			return packsCreator(action);
		}
		else if(target.equals(ARMOR_B))
		{
			return armorCreator(action);
		}
		else if(target.equals(ELSE_B))
		{
			return elseCreator(action);
		}
		return null;
	}

	public static Act startCreate() 
	{
		return Act.builder()
				.name("CreateItem")
				.text("Which item you take?")
				.action(Action.builder()
						.location(Location.FACTORY)
						.buttons(new String[][]
								{{WEAPON_B, AMNUNITION_B},
							{TOOL_B, PACK_B},
							{ARMOR_B},
							{ELSE_B}})
						.key(KEY)
						.build())
				.build();
	}


	private static Act elseCreator(Action action)
	{
		switch(action.getAnswers().length)
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

	private static Act chooseNameItems(Action action) 
	{
		action.setMediator();
		return Act.builder()
				.name("chooseNameItems")
				.text("How would you name this item?(Write)")
				.action(action)
				.build();
	}

	private static Act chooseDescriptionItems(Action action) 
	{
		action.setMediator();
		return Act.builder()
				.name("chooseDescriptionItems")
				.text(action.getAnswers()[1]+ "? okey... Give me some description which you want to see when you will look in your bag.(Write)")
				.action(action)
				.build();
	}

	private static Act agreedItems(Action action) 
	{
		Items item = new Items();
		item.setName(action.getAnswers()[1]);
		item.setDescription(action.getAnswers()[2]);

		action.setButtons(new String[][] {{"Yeah, right"}});
		action.setMediator();
		action.setObjectDnd(item);
		return Act.builder()
				.name("checkItemCondition")
				.text(item.getDescription())
				.action(action)
				.build();
	}


	private static Act ammunitionCreator(Action action)
	{
		switch(action.getAnswers().length)
		{
		case 1:
			return	chooseTypeAmmunition(action);
		case 2:
			return  agreedAmmunition(action);

		}
		return null;
	}

	private static Act chooseTypeAmmunition(Action action) 
	{
		action.setButtons(getArray(ammunitionArrayConvertor(Ammunitions.values())));
		return Act.builder()
				.name("chooseTypeAmmunition")
				.text("What ammunition is it?")
				.action(action).build();
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

	private static Act agreedAmmunition(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		Ammunition ammunition = new Ammunition(targetAmmunition(action.getAnswers()[1]));
		action.setObjectDnd(ammunition);
		return Act.builder()
				.name("checkCondition")
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private static Ammunitions targetAmmunition(String name)
	{
		for(Ammunitions type: Ammunitions.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}


	private static Act weaponCreator(Action action)
	{
		switch(action.getAnswers().length)
		{
		case 1:
			return	chooseTypeWeapon(action);
		case 2:
			return  agreedWeapon(action);

		}
		return null;
	}

	private static Act chooseTypeWeapon(Action action) 
	{
		action.setButtons(getArray(weaponArrayConvertor(Weapons.values())));
		return Act.builder()
				.name("ChooseTypeWeapon")
				.text("What weapon is it?")
				.action(action).build();
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

	private static Act agreedWeapon(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		Weapon weapon = new Weapon(targetWeapon(action.getAnswers()[1]));
		action.setObjectDnd(weapon);
		return Act.builder()
				.name("checkCondition")
				.text(weapon.getDescription())
				.action(action)
				.build();
	}

	private static Weapons targetWeapon(String name)
	{
		for(Weapons type: Weapons.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}


	private static Act toolCreator(Action action)
	{
		switch(action.getAnswers().length)
		{
		case 1:
			return	chooseTypeTool(action);
		case 2:
			return  agreedTool(action);

		}
		return null;
	}

	private static Act chooseTypeTool(Action action) 
	{
		action.setButtons(getArray(toolArrayConvertor(Tools.values())));
		return Act.builder()
				.name("chooseTypeTool")
				.text("What tool is it?")
				.action(action).build();
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

	private static Act agreedTool(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		Tool tool = new Tool(targetTool(action.getAnswers()[1]));
		action.setObjectDnd(tool);
		return Act.builder()
				.name("checkCondition")
				.text(tool.getDescription())
				.action(action)
				.build();
	}

	private static Tools targetTool(String name)
	{
		for(Tools type: Tools.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}


	private static Act packsCreator(Action action)
	{
		switch(action.getAnswers().length)
		{
		case 1:
			return	chooseTypePack(action);
		case 2:
			return  agreedPack(action);

		}
		return null;
	}

	private static Act chooseTypePack(Action action) 
	{
		action.setButtons(getArray(packsArrayConvertor(Packs.values())));
		return Act.builder()
				.name("ChooseTypePack")
				.text("What pack is it?")
				.action(action).build();
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

	private static Act agreedPack(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		Pack pack = new Pack(targetPack(action.getAnswers()[1]));
		action.setObjectDnd(pack);
		return Act.builder()
				.name("checkCondition")
				.text(pack.getDescription())
				.action(action)
				.build();
	}

	private static Packs targetPack(String name)
	{
		for(Packs type: Packs.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}


	private static Act armorCreator(Action action)
	{
		switch(action.getAnswers().length)
		{
		case 1:
			return	chooseTypeArmor(action);
		case 2:
			return  agreedArmor(action);

		}
		return null;
	}

	private static Act chooseTypeArmor(Action action) 
	{
		action.setButtons(getArray(armorArrayConvertor(Armors.values())));
		return Act.builder()
				.name("ChooseTypeWeapon")
				.text("What armor is it?")
				.action(action).build();

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

	private static Act agreedArmor(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		Armor armor = new Armor(targetArmor(action.getAnswers()[1]));
		action.setObjectDnd(armor);
		return Act.builder()
				.name("checkCondition")
				.text(armor.getDescription())
				.action(action)
				.build();
	}

	private static Armors targetArmor(String name)
	{
		for(Armors type: Armors.values())
		{
			if(type.toString().equals(name)) return type;
		}
		return null;
	}


	public static Act finish(Action action, CharacterDnd actualGameCharacter) 
	{
		actualGameCharacter.getBody().getBag().add((Items)action.getObjectDnd());
		return Act.builder()
				.returnTo(STUFF_B, BAG_B)
				.build();
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
