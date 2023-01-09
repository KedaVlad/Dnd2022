package com.dnd.botTable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.CloudAction;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.DndAction;
import com.dnd.botTable.actions.dndAction.StartTreeAction;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.botTable.actions.factoryAction.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.factory.ControlPanel;
import com.dnd.dndTable.rolls.Dice;


public class GameTable implements KeyWallet, Serializable
{
	private static final long serialVersionUID = -8448308501857106456L;
	private long chatId;
	private Map<String, CharacterDnd> savedCharacter = new LinkedHashMap<>();
	private CharacterDnd actualGameCharacter;
	private ControlPanel controlPanel = new ControlPanel();
	private ActMachine script = new ActMachine();
	List<CloudAction> clouds;

	Action makeAction(Action action)
	{ 
		Log.add("START MAKE ACTION IN GAME TABLE");
		Action answer = null; 
		if(action instanceof DndAction)
		{
			answer = getActualGameCharacter().act(action);
		}
		else if(action instanceof FinalAction)
		{
			return controlPanel.finish((FinalAction)action, this);
		}
		else if(action instanceof FactoryAction)
		{	
			FactoryAction factory = (FactoryAction) action;
			return controlPanel.act(factory);
		}
		else if(action instanceof BotAction)
		{
			return  execute((BotAction) action);
		}
		else if(action instanceof WrappAction)
		{
			return action;
		}
		relocateClouds();
		save();
		return answer;
	}

	private void relocateClouds()
	{
		clouds = actualGameCharacter.getCloud();
	}

	private Action execute(BotAction action)
	{
		long key = action.key;
		Log.add(key);
		if(key == ControlPanel.getKey())
		{
			return apruveStats(action);
		}
		else if(key == hp)
		{
			return apruveHp(action);
		}
		else if(key == createOrDelete)
		{
			Log.add("Right place");
			return createOrDelete(action);
		}
		else if(key == downloadHero)
		{
			Log.add("DOWNLOAD");
			return download(action.getAnswer());
		}
		else if(key == toMenu)
		{
			return menu();
		}
		else if(key == toRolls)
		{
			return rollsMenu();
		}
		else if(key == rolls)
		{
			return rollsMenu(action);
		}
		Log.add("ERROR IN GAME TABLE EXECUTE");
		return null;
	}

	private Action fightMenu() 
	{
		return BotAction.create("FIGHT", NO_ANSWER, true, false, "SORY BRO, I cant give you what u want ;(", null);
	}

	private Action createOrDelete(BotAction action)
	{
		script.beackTo("START");
		String key = action.getAnswer();
		if(key.equals("CREATE"))
		{
			return controlPanel.createHero();
		}
		else if(key.equals("DELETE"))
		{

		}

		return null;
	}

	boolean readiness()
	{
		Log.add("Check readiness");
		if(getActualGameCharacter() == null)
		{
			return false;
		}
		else if(controlPanel.readiness(getActualGameCharacter()) == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private Action download(String string)
	{
		setActualGameCharacter(savedCharacter.get(string));
		if(controlPanel.readiness(getActualGameCharacter()) != null)
		{
			script.beackTo("START");
			return controlPanel.readiness(getActualGameCharacter());
		}
		relocateClouds();
		return menu();
	}

	Action characterCase()
	{

		String name = "characterCase";
		if(getSavedCharacter().size() != 0) 
		{
			String[][] buttons = new String[getSavedCharacter().size()][];
			int i = 0;
			for(String character: getSavedCharacter().keySet())
			{
				buttons[i] = new String[] {getSavedCharacter().get(character).getName()};
				i++;
			}
			Action[] pool = new Action[] {
					BotAction.create("", createOrDelete, true, false, "Choose the Hero or CREATE new one... or DELETE some.", new String[][]{{"CREATE", "DELETE"}}).replyButtons(),
					BotAction.create("", downloadHero, true, false, "Your Heroes", buttons),


			};
			return ArrayAction.create(name, NO_ANSWER, pool);
		}	
		else
		{
			String text = "You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.";
			String[][] buttons = new String[][]{{"CREATE"}};
			return BotAction.create(name, createOrDelete, true, false, text, buttons).replyButtons();
		}

	}

	Action menu()
	{
		save();
		script.beackTo(start);
		Action[][] pool = new Action[][]
				{
			{
				StartTreeAction.create("ABILITY", ABILITY),
				StartTreeAction.create("CHARACTERISTIC", CHARACTERISTIC),
				StartTreeAction.create("STUFF", STUFF)
			},
			{
				BotAction.create("ROLLS", toRolls, true, false, null, null),
				StartTreeAction.create("(DE)BUFF", DEBUFF)
			},
			{
				StartTreeAction.create("REST", REST),
				StartTreeAction.create("MEMOIRS", MEMOIRS),
				fightMenu()
			}

				};
				return WrappAction.create("Menu", chatId, actualGameCharacter.getMenu(), pool).replyButtons();
	}

	private Action rollsMenu()
	{
		String name = "ROLLS";
		return BotAction.create(name, rolls, true, false, rollsText, new String[][] {
			{
				"D4","D6","D8","D10","D12","D20","D100"
			}
		});
	}

	private Action rollsMenu(BotAction action)
	{
		String text = "";
		switch(action.getAnswer())
		{
		case "D4":
			text = Dice.d4() + "";
			break;
		case "D6":
			text = Dice.d6() + "";
			break;
		case "D8":
			text = Dice.d8() + "";
			break;
		case "D10":
			text = Dice.d10() + "";
			break;
		case "D12":
			text = Dice.d12() + "";
			break;
		case "D20":
			text = Dice.d20() + "";
			break;
		case "D100":
			text = Dice.d20() + "";
			break;
		}
		return BotAction.create("EndTree", NO_ANSWER, true, false, text, null);
	}

	private Action apruveHp(BotAction action)
	{
		String name;
		String text;
		if(action.getAnswer().contains("Stable"))
		{
			getActualGameCharacter().getHp().grow(Dice.stableStartHp(getActualGameCharacter()));
			name = "finishCreatingHero";
			text = "Congratulations, you are ready for adventure.";
			relocateClouds();
			return BotAction.create(name, toMenu, true, false, text , new String[][]{{"Let's go"}});
		}
		else if(action.getAnswer().contains("Random"))
		{
			getActualGameCharacter().getHp().grow(Dice.randomStartHp(getActualGameCharacter()));
			name = "finishCreatingHero";
			text = "Congratulations, you are ready for adventure.";
			relocateClouds();
			return BotAction.create(name, toMenu, true, false, text, new String[][]{{"Let's go"}});
		}
		else
		{
			Pattern pat = Pattern.compile(keyNumber);
			Matcher matcher = pat.matcher(action.getAnswer());
			int hp = 0;
			while (matcher.find()) 
			{
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}

			if(hp <= 0)
			{
				hp = Dice.stableStartHp(getActualGameCharacter());
				getActualGameCharacter().getHp().grow(Dice.stableStartHp(getActualGameCharacter()));
				name = "finishCreatingHero";
				text = "Nice try... I see U very smart, but you will get stable " + hp + " HP. You are ready for adventure.";
				relocateClouds();
				return BotAction.create(name, toMenu, true, false ,text , new String[][]{{"Let's go"}});

			}
			else
			{
				getActualGameCharacter().getHp().grow(hp);
				name = "finishCreatingHero";
				text = "Congratulations, you are ready for adventure.";
				relocateClouds();
				return BotAction.create(name, toMenu, true, false ,text , new String[][]{{"Let's go"}});

			}

		}
	}

	private Action apruveStats(BotAction action) 
	{

		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(action.getAnswer());
		while (matcher.find()) 
		{
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}

		String name = "apruveStats";
		String text;

		if(stats.size() != 6)
		{

			text = "Instructions not followed, please try again. Make sure there are 6 values.\r\n"
					+ "Examples:\r\n"
					+ " 11, 12, 13, 14, 15, 16 \r\n"
					+ " str 11 dex 12 con 13 int 14 wis 15 cha 16 ";

			return BotAction.create(name, 0 , true, true, text, null);

		}
		else
		{
			getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
			getActualGameCharacter().addMemoirs("My start rolled stats: " + stats.get(0) + " " + stats.get(1) + " " + 
					stats.get(2) + " " + stats.get(3) + " " + stats.get(4) + " " + stats.get(5));
			int stableHp = Dice.stableStartHp(getActualGameCharacter());
			String[][] nextStep = {{"Stable " + stableHp, "Random ***"}};
			text = "How much HP does your character have?\r\n"
					+ "\r\n"
					+ "You can choose stable or random HP count \r\n"
					+ "\r\n"
					+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)";

			return BotAction.create(name, hp , true, true, text, nextStep);
		}
	}

	ControlPanel getControlPanel() 
	{
		return controlPanel;
	}

	static GameTable create(Message message) 
	{
		GameTable gameTable = new GameTable();
		gameTable.clouds = new ArrayList<>();
		gameTable.setChatId(message.getChatId());
		gameTable.getScript().up(BotAction.create(message.getChatId()+"", message.getChatId() , true, false, null, null));	
		return gameTable;

	}

	Map<String, CharacterDnd> getSavedCharacter()
	{
		return savedCharacter;
	}

	public void save() 
	{
		savedCharacter.put(getActualGameCharacter().getName(), getActualGameCharacter());
	}

	public void delete(String name) 
	{
		savedCharacter.remove(name);
	}

	public ActMachine getScript() {
		return script;
	}

	public long getChatId() 
	{
		return chatId;
	}

	public void setChatId(long chatId) 
	{
		this.chatId = chatId;
	}

	public CharacterDnd getActualGameCharacter() 
	{
		return actualGameCharacter;
	}

	public void setActualGameCharacter(CharacterDnd actualGameCharacter) 
	{
		this.actualGameCharacter = actualGameCharacter;
	}

}
