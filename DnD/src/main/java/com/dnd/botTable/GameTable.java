package com.dnd.botTable;


import java.util.Optional;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.ButtonName;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.dndTable.factory.ControlPanel;
import com.dnd.dndTable.rolls.Formalizer;


class GameTable extends Table 
{
	CharactersPool characters;
	ControlPanel controlPanel;
	ScriptMachine scriptMachine;
	CloudMachine cloudMachine;

	GameTable(long tableKey) 
	{
		super(tableKey);
	}

	@Override
	protected boolean execute(User user, Update update) 
	{
		this.characters = user.CHARACTERS;
		this.scriptMachine.initialize(user.SCRIPT);
		this.controlPanel.setCharactersPool(user.CHARACTERS);
		this.cloudMachine.setClouds(user.CHARACTERS.clouds);

		Act act = null;

		if(update.hasCallbackQuery())
		{
			act = handleCallback(update.getCallbackQuery());
		}
		else
		{
			act = handleMessage(update.getMessage());
		}

		if(act == null) 
		{
			return true;
		}
		else if(act.hasCloud())
		{
			cloudMachine.getClouds().clouds.add(act);
			act = Act.builder().name("DeatEnd").text("ELIMINATE after no longer need").build();
		}
		
		if(act.returnCall != null)
		{
			if(act.returnCall.equals(act.returnTarget))
			{
				scriptMachine.targetByName(act.returnTarget);
				scriptMachine.toTarget();
				scriptMachine.cleanTarget();
				return true;
			}
			else
			{
				scriptMachine.targetByName(act.returnTarget);
				act = makeAction(scriptMachine.script.target.action.continueAction(act.returnCall));
				scriptMachine.toScript(act);
				return true;
			}
		}
		else
		{
			scriptMachine.toScript(act);
			return true;
		}
	}

	private Act handleCallback(CallbackQuery callbackQuery)
	{
		String target = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$1");
		long key = Long.parseLong(callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$2"));		
		String callback = callbackQuery.getData().replaceAll("([a-zA-Z `�-]+)(\\d{9})(.+)", "$3");
		if(key == CLOUD_ACT)
		{
			if(callback.equals(ELIMINATION))
			{
				cloudMachine.eliminate(target);
				scriptMachine.script.target = null;
				return null;
			}
			else
			{
				return makeAction(cloudMachine.compleat(target, callback));
			}
		}
		else if(scriptMachine.targetByName(target))
		{
			if(key == MAIN_TREE)
			{
				return makeAction(scriptMachine.script.target.action.continueAction(callback));
			}
			else
			{
				ArrayActs pool = (ArrayActs) scriptMachine.script.target;
				return makeAction(pool.getTarget(key).action.continueAction(callback));
			}
		}
		return null;
	}

	private Act handleMessage(Message message)
	{
		if(message.hasEntities()) 
		{	
			return comandPlay(message);
		}
		if(message.getText().equals(RETURN_TO_MENU) && readiness())
		{
			scriptMachine.script.trash.add(message.getMessageId());
			return menu();

		}
		else if(scriptMachine.targetByText(message.getText()))
		{
			Act act = makeAction(scriptMachine.continueTarget(message.getText()));
			if(act.returnCall != null || act.hasCloud())
			{
				scriptMachine.script.trash.add(message.getMessageId());
			}
			else
			{
			act.toCircle(message.getMessageId());
			}
			return act;
		}
		else if(readiness())
		{
			scriptMachine.script.trash.add(message.getMessageId());
			return characterTextComand(message.getText());

		}
		else
		{

			return Act.builder()
					.name("NoAnswer")
					.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
					.build();
		}
	}

	private Act comandPlay(Message message)
	{
		Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

		if (commandEntity.isPresent())
		{
			String comand = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
			switch (comand)
			{
			case "/start":
				scriptMachine.script.trash.add(message.getMessageId());
				return Act.builder().name(START_B).returnTo(message.getChatId()+"").text(startText).build();

			case "/characters":
				scriptMachine.script.trash.add(message.getMessageId());
				return characterCase();
			}
		}
		return null;
	}

	private Act characterTextComand(String text)
	{
		if(text.matches("^\\+\\d+"))
		{
			int exp = (Integer) Integer.parseInt(text.replaceAll("^\\+(\\d+)", "$1"));
			if(characters.actual.getLvl().addExp(exp))
			{
			}
			return menu();
		}
		else if(text.matches("^(hp|Hp|HP|hP)(\\+|-)\\d+"))
		{
			String num = text.replaceAll("^(hp|Hp|HP|hP)(\\+|-)(\\d+)", "$3");
			int value = (Integer) Integer.parseInt(num);
			if(text.contains("+"))
			{
				characters.actual.getHp().heal(value);
			}
			else if(text.contains("-"))
			{
				characters.actual.getHp().damaged(value);
			}
			else
			{
				return Act.builder()
						.name("MissHpFormula")
						.text("You make something vrong... I dont understand what do whith " + value + " heal(+) or damage(-)? Try again.")
						.build();
			}
			return menu();
		}
		else 
		{
			characters.actual.addMemoirs(text);
			return Act.builder()
					.name("addMemoirs")
					.text("I will put it in your memoirs")
					.build();
		}
	}

	private Act makeAction(BaseAction action)
	{ 
		Location location = action.getLocation();
		if(action instanceof Action)
		{
			if(location == Location.BOT)
			{
				return execute((Action)action);
			}
			else if(location == Location.CHARACTER)
			{
				return characters.actual.executeAction(action);
			}
			else if(location == Location.FACTORY)
			{
				return controlPanel.execute((Action)action);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return characters.actual.executeAction(action);
		}
		
	}

	public Act execute(Action action) 
	{
		long key = action.getKey();
		if(key == create)
		{
			return createCharacter(action);
		}
		else if(key == downloadHero)
		{
			return download(action);
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
			return compleatRoll(action);
		}
		else if(key == DEBUFF)
		{
			return addDebuff(action);
		}
		return null;
	}

	private Act addDebuff(Action action) 
	{
		if(action.getAnswers()[0].equals(RETURN_TO_MENU))
		{
			return menu();
		}
		else
		{
			String name = "Debuff";
			for(int i = 0; i < (cloudMachine.getClouds().cloudsWorked.size() + cloudMachine.getClouds().clouds.size()); i++)
			{
				name += "A";
			}
			
			return Act.builder()
					.name(name)
					.text(action.getAnswers()[0])
					.action(Action.builder()
							.cloud()
							.build())
					.build();
		}
	}

	private Act createCharacter(Action action)  //!!!
	{
		String key = action.getAnswers()[0];
		if(key.equals(CREATE_B))
		{
			return controlPanel.execute(Action.builder()
					.key(action.getKey())
					.build());
		}
		else if(key.equals(DELETE_B))
		{

		}
		return null;
	}

	private boolean readiness()
	{
		if(characters.actual == null)
		{
			return false;
		}
		else if(controlPanel.readiness() == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private Act download(Action action)
	{
		characters.download(action.getAnswers()[0]);
		if(controlPanel.readiness() != null)
		{
			return controlPanel.readiness();
		}

		return menu();
	}

	private Act characterCase()
	{
		String name = CHARACTER_CREATE_CASE_B;
		if(characters.savedCharacters.size() != 0) 
		{
			String[][] buttons = new String[characters.savedCharacters.size()][1];
			int i = 0;
			for(String character: characters.savedCharacters.keySet())
			{
				buttons[i][0] = characters.savedCharacters.get(character).getName();
				i++;
			}
			return ArrayActsBuilder.builder()
					.name(name)
					.returnTo(START_B)
					.pool(Act.builder()
							.text("Choose the Hero or " + CREATE_B + " new one.")
							.action(Action.builder()
									.key(create)
									.location(Location.BOT)
									.buttons(new String[][]{{CREATE_B}})
									.replyButtons()
									.build())
							.build(),
							Act.builder()
							.text("Your Heroes")
							.action(Action.builder()
									.key(downloadHero)
									.location(Location.BOT)
									.buttons(buttons)
									.build())
							.build())
					.build();
		}	
		else
		{
			return Act.builder()
					.name(name)
					.text("You don't have a Hero yet, my friend. But after you " + CREATE_B + " them, you can find them here.")
					.action(Action.builder()
							.location(Location.BOT)
							.key(create)
							.buttons(new String[][]{{CREATE_B}})
							.replyButtons()
							.build())
					.build();
		}

	}

	private Act menu()
	{
		characters.save();
		Action[][] pool = new Action[][]
				{
			{
				Action.builder().name(ABILITY_B).key(ABILITY).location(Location.CHARACTER).build(),
				Action.builder().name(CHARACTERISTIC_B).key(CHARACTERISTIC).location(Location.CHARACTER).build(),
				Action.builder().name(STUFF_B).key(STUFF).location(Location.CHARACTER).build()
			},
			{
				Action.builder().name(ROLLS_B).key(toRolls).location(Location.BOT).build(),
				Action.builder().name(DEBUFF_B).key(DEBUFF).location(Location.CHARACTER).build()
			},
			{
				Action.builder().name(REST_B).key(REST).location(Location.CHARACTER).build(),
				Action.builder().name(MEMOIRS_B).key(MEMOIRS).location(Location.CHARACTER).build()
			}
				};
				return Act.builder()
						.name(MENU_B)
						.text(characters.actual.info())
						.action(PoolActions.builder()
								.actionsPool(pool)
								.replyButtons()
								.build())
						.returnTo(START_B)
						.build();
	}

	private Act rollsMenu()
	{
		return Act.builder()
				.name(ROLLS_B)
				.text(rollsText)
				.action(Action.builder()
						.location(Location.BOT)
						.key(rolls)
						.buttons(new String[][] {
							{"D4","D6","D8","D10"},
							{"D12","D20","D100"},
							{RETURN_TO_MENU}})
						.replyButtons()
						.mediator()
						.build())
				.build();
	}

	private Act compleatRoll(Action action)
	{
		String answer = action.getAnswers()[0];
		if(answer.equals(RETURN_TO_MENU))
		{
			return menu();
		}
		else
		{
			String text = Formalizer.formalize(answer);

			return Act.builder()
					.name("DeadEnd")
					.text(text)
					.build();
		}
	}

	static GameTable create(long id) 
	{
		GameTable gameTable = new GameTable(id);
		gameTable.scriptMachine = new ScriptMachine();
		gameTable.cloudMachine = new CloudMachine();
		gameTable.controlPanel = new ControlPanel(gameTable.characters);
		return gameTable;

	}

}

class CloudMachine implements ButtonName
{
	private Clouds clouds;

	void eliminate(String actName)
	{
		Act target = null;
		for(Act act: clouds.cloudsWorked)
		{
			if(act.name.equals(actName))
			{
				target = act;
				clouds.trash.addAll(act.end());
				break;
			}
		}
		if(target != null)
		{
		clouds.cloudsWorked.remove(target);
		}
	}
	
	BaseAction compleat(String actName, String callback)
	{
		for(Act act: clouds.cloudsWorked)
		{
			if(act.name.equals(actName))
			{
				return act.action.continueAction(callback);
			}
		}
		return null;
	}

	public Clouds getClouds() 
	{
		return clouds;
	}

	public void setClouds(Clouds clouds) 
	{
		this.clouds = clouds;
	}


}

class ScriptMachine
{
	Script script;

	boolean targetByName(String actName)
	{
		for(Act act: script.mainTree)
		{
			if(act.name.equals(actName))
			{
				script.target = act;
				return true;
			}
		}
		return false;
	}
	
	public void initialize(Script script) 
	{
		this.script = script;
		if(this.script.target == null)
		{
			this.script.target = script.mainTree.get(script.mainTree.size()-1);
		}
	}

	void cleanTarget()
	{
		script.trash.addAll(script.target.end());
	}

	boolean targetByText(String text)
	{
		return findMediator() || findReply(text);
	}

	private boolean findReply(String string)
	{

		for(int i = script.mainTree.size() - 1; i > 0; i--)
		{
			if(script.mainTree.get(i).hasReply(string))
			{
					script.target = script.mainTree.get(i);
					return true;
			}
		}
		return false;
	}

	private boolean findMediator()
	{
		if(script.mainTree.get(script.mainTree.size() - 1).hasMediator())
		{
			script.target = script.mainTree.get(script.mainTree.size() - 1);
			return true;
		}
		else if(script.mainTree.size() > 1 && 
				script.mainTree.get(script.mainTree.size() - 2).hasMediator())
		{
			script.target = script.mainTree.get(script.mainTree.size() -2);
			return true;
		}
		else
		{
			return false;
		}
	}

	void toScript(Act act)
	{		
		toTarget();
		if(act.hasBack())
		{
			targetByName(act.returnTarget);
			toTarget();
		}
		
		if(targetByName(act.name))
		{ 
			targetPrevios();
			toTarget();
		}
		up(act);
	}

	void targetPrevios()
	{
		for(int i = 1; i < script.mainTree.size(); i++)
		{
			if(script.mainTree.get(i).equals(script.target))
			{
				script.target = script.mainTree.get(i - 1);
				return;
			}
		}
	}

	void toTarget()
	{
		for(int i = 0; i < script.mainTree.size(); i++)
		{
			if(script.mainTree.get(i).name.equals(script.target.name))
			{
				for(int j = script.mainTree.size() - 1; j > i; j--)
				{
					script.trash.addAll(script.mainTree.get(j).end());
					script.mainTree.remove(j);
				}
			}
		}
	}
	
	BaseAction continueTarget(String string)
	{
		if(script.target instanceof ArrayActs)
		{
			ArrayActs target = (ArrayActs) script.target;
			return target.pool[0].action.continueAction(string);
		}
		else
		{
			return script.target.action.continueAction(string);
		}
	}

	void up(Act act)
	{
		this.script.mainTree.add(act);
		this.script.target = act;
	}
}
