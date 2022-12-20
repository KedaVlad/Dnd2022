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
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.StartTreeAction;
import com.dnd.dndTable.ActionObject;
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

	public Action makeAction(Action action)
	{ 
		if(action instanceof HeroAction)
		{
			HeroAction hero = (HeroAction) action;
			return getActualGameCharacter().act(hero);
		}
		else if(action instanceof FinalAction)
		{
			script.beackTo(start);
			return controlPanel.finish((FinalAction)action, this);
		}
		else if(action instanceof FactoryAction)
		{	
			FactoryAction factory = (FactoryAction) action;
			return controlPanel.act(factory);
		}
		else if(action instanceof BotAction)
		{
			return execute((BotAction) action);
		}
		return null;
	}

	private Action execute(BotAction action)
	{
		long key = action.key;
		
		if(key == ControlPanel.getKey())
		{
			return apruveStats(action);
		}
		else if(key == hp)
		{
			return apruveHp(action);
		}
		else if(key == characterCase)
		{
			return createOrDownload(action);
		}
		else if(key == toMenu)
		{
			return menu();
		}
		else if(key == menu)
		{
			return inMenu(action);
		}

		return null;
	}


	private Action inMenu(BotAction action) 
	{
		if(action.getAnswer().equals("SPELLS"))
		{
			return actualGameCharacter.act(StartTreeAction.create(spell));
		} 
		else if(action.getAnswer().equals("FETURE"))
		{
			return actualGameCharacter.act(StartTreeAction.create(feature));
		}
		else if(action.getAnswer().equals("POSSESSION"))
		{
			return actualGameCharacter.act(StartTreeAction.create(possession));
		}
		else if(action.getAnswer().equals("BAG"))
		{
			return actualGameCharacter.act(StartTreeAction.create(body));
		}
		else if(action.getAnswer().equals("ROLLS"))
		{
			return actualGameCharacter.act(StartTreeAction.create(stat));
		}
		else if(action.getAnswer().equals("REST"))
		{
			return actualGameCharacter.act(StartTreeAction.create(rest));
		}
		else if(action.getAnswer().equals("FIGHT"))
		{
			return fightMenu();
		}
		
		return null;
		
	}

	private Action fightMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	private Action createOrDownload(BotAction action)
	{
		String key = action.getAnswer();
		if(key.equals("CREATE"))
		{
			return controlPanel.createHero();
		}
		else if(key.equals("DELETE"))
		{
			
		}
		else
		{
			return download(key);
		}
		
		
		return null;
	}
	
	private Action download(String string)
	{
		setActualGameCharacter(savedCharacter.get(string));
		if(controlPanel.readiness(getActualGameCharacter()) != null)
		{
			return controlPanel.readiness(getActualGameCharacter());
		}
		return menu();
	}

	public Action characterCase()
	{
		
		String name = "characterCase";
		long key = characterCase;
		
		if(getSavedCharacter().size() != 0) 
		{
			String text = "Choose a Hero or CREATE new one.";
			String[][] buttons = new String[getSavedCharacter().size()+1][];
			buttons[0] = new String[]{"CREATE", "DELETE"};
			int i = 1;
			for(String character: getSavedCharacter().keySet())
			{
				buttons[i][0] = getSavedCharacter().get(character).getName();
				i++;
			}
			return BotAction.create(name, key, true, false, text, buttons);
		}	
		else
		{
			String text = "You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.";
			String[][] buttons = new String[][]{{"CREATE"}};
			return BotAction.create(name, key, true, false, text, buttons);
		}

	}

	private Action menu()
	{
		return ArrayAction.create("Menu", toMenu, new BotAction[] {
				BotAction.create("Menu", menu, true, false, actualGameCharacter.getStatus(), null), 
				BotAction.create("Menu", menu, true, false, actualGameCharacter.getMenu(), new String[][]
					{{"SPELLS", "FETURE", "POSSESSION"},
							{"BAG", "ROLLS"},
							{"MEMOIRS"},
							{"REST","FIGHT"}
					})});
	}

	private Action apruveHp(BotAction action)
	{
		String name;
		String text;
		if(action.getAnswer().contains("Stable"))
		{
			getActualGameCharacter().setHp(Dice.stableStartHp(getActualGameCharacter()));
			name = "finishCreatingHero";
			text = "Congratulations, you are ready for adventure.";
			return BotAction.create(name, toMenu, true, false, text , new String[][]{{"Let's go"}});
		}
		else if(action.getAnswer().contains("Random"))
		{
			getActualGameCharacter().setHp(Dice.randomStartHp(getActualGameCharacter()));
			name = "finishCreatingHero";
			text = "Congratulations, you are ready for adventure.";
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
				getActualGameCharacter().setHp(Dice.stableStartHp(getActualGameCharacter()));
				name = "finishCreatingHero";
				text = "Nice try... I see U very smart, but you will get stable " + hp + " HP. You are ready for adventure.";
				return BotAction.create(name, toMenu, true, false ,text , new String[][]{{"Let's go"}});

			}
			else
			{
				getActualGameCharacter().setHp(hp);
				name = "finishCreatingHero";
				text = "Congratulations, you are ready for adventure.";
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
					+ " 11 12 13 14 15 16 \r\n"
					+ " 11/12/13/14/15/16 \r\n"
					+ " str 11 dex 12 con 13 int 14 wis 15 cha 16 ";
			
			return BotAction.create(name, 0 , true, true, text, null);

		}
		else
		{
			getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
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

	public ControlPanel getControlPanel() 
	{
		return controlPanel;
	}

	static GameTable create(Message message) 
	{
		GameTable gameTable = new GameTable();
		gameTable.setChatId(message.getChatId());
		gameTable.getScript().up(BotAction.create(message.getChatId()+"", message.getChatId() , true, false, null, null));	
		return gameTable;

	}

	public Map<String, CharacterDnd> getSavedCharacter()
	{
		return savedCharacter;
	}

	public void save() 
	{

		//if(isCheckChar())
		//	{
		savedCharacter.put(getActualGameCharacter().getName(), getActualGameCharacter());
		//}
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
