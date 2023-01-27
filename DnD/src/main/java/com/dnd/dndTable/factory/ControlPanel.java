package com.dnd.dndTable.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.CharactersPool;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.rolls.Formalizer;

public class ControlPanel implements Executor
{

	private static final long serialVersionUID = -231330030795056098L;
	private CharactersPool charactersPool;

	public ControlPanel(CharactersPool charactersPool)
	{
		this.charactersPool = charactersPool;
	}
	
	@Override
	public Act execute(Action action)
	{
		int condition = 0;
		if(action.getAnswers() != null) condition = action.getAnswers().length;
		if(action.getKey() == CharacterFactory.KEY)
		{
			if(condition == CharacterFactory.END)
			{
				charactersPool.setActualCharacter(CharacterFactory.finish(action));
				charactersPool.getActualCharacter().addMemoirs(charactersPool.getActualCharacter().getName()+"\n");
				charactersPool.save();
				return RaceFactory.execute(Action.builder().build());
			}
			return CharacterFactory.execute(action);
		}
		else if(action.getKey() == RaceFactory.KEY)
		{
			if(condition == RaceFactory.END)
			{
				RaceFactory.finish(action, charactersPool.getActualCharacter());
				return ClassFactory.execute(Action.builder().build());
			}
			return RaceFactory.execute(action);
		}
		else if(action.getKey() == ClassFactory.KEY)
		{
			if(condition == ClassFactory.END)
			{
				ClassFactory.finish(action, charactersPool.getActualCharacter());
				return  buildStat();
			}
			return ClassFactory.execute(action);
		}
		else if(action.getKey() == STAT)
		{
			return apruveStats(action);
		}
		else if(action.getKey() == hp)
		{
			return apruveHp(action);
		}
		else if(action.getKey() == ItemFactory.KEY)
		{
			return ItemFactory.execute(action, charactersPool.getActualCharacter());
		}
		else
		{
			return null;
		}
	}

	public Act readiness()
	{
		if(charactersPool.getActualCharacter().getMyRace() == null)
		{
			return RaceFactory.execute(Action.builder().build());
		}
		else if(charactersPool.getActualCharacter().getClassDnd() == null)
		{
			return ClassFactory.execute(Action.builder().build());
		}
		else if(charactersPool.getActualCharacter().getHp().getNow() == 0)
		{
			return buildStat();
		}
		else
		{
			return null;
		}
	}

	private Act buildStat()
	{
		String name = "ChooseStat";
		String godGift = Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " 
				+ Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " 
				+ Formalizer.randomStat() + ", " + Formalizer.randomStat();

		String text =  "Now let's see what you have in terms of characteristics.\r\n"
				+ "\r\n"
				+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
				+ "1.Each stat cannot be higher than 20.\r\n"
				+ "2. Write down stats without taking into account buffs from race / class.\r\n"
				+ "\r\n"
				+ "Use the random god gift in the order you want your stats to be.\r\n"
				+ "\r\n" + godGift + "\r\n"
				+ "\r\n"
				+ "Or write down those values that are agreed with your game master.\r\n"
				+ "Examples:\r\n"
				+ " str 11 dex 12 con 13 int 14 wis 15 cha 16\r\n"
				+ " 11, 12, 13, 14, 15, 16";

		return Act.builder()
				.name(name)
				.text(text)
				.action(Action.builder()
						.location(Location.FACTORY)
						.key(STAT)
						.mediator()
						.build())
				.returnTo(START_B)
				.build();
	}

	private Act apruveStats(Action action) 
	{

		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(action.getAnswers()[0]);
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

			return Act.builder().name("DeadEnd").text(text).build();

		}
		else
		{
			charactersPool.getActualCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
			charactersPool.getActualCharacter().addMemoirs("My start rolled stats: " + stats.get(0) + " " + stats.get(1) + " " + 
					stats.get(2) + " " + stats.get(3) + " " + stats.get(4) + " " + stats.get(5));
			int stableHp = Formalizer.stableStartHp(charactersPool.getActualCharacter());
			String[][] nextStep = {{"Stable " + stableHp, "Random ***"}};
			text = "How much HP does your character have?\r\n"
					+ "\r\n"
					+ "You can choose stable or random HP count \r\n"
					+ "\r\n"
					+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)";

			return Act.builder()
					.name(name)
					.text(text)
					.action(Action.builder()
							.mediator()
							.buttons(nextStep)
							.location(Location.FACTORY)
							.key(hp)
							.replyButtons()
							.build())
					.build();
		}
	}

	private Act apruveHp(Action action)
	{
		String name;
		String text;
		String answer = action.getAnswers()[0];
		CharacterDnd character = charactersPool.getActualCharacter();
		if(answer.contains("Stable") || answer.contains("Random"))
		{
			if(answer.contains("Stable"))
			{
				character.getHp().grow(Formalizer.stableStartHp(character));
			}
			else
			{
				character.getHp().grow(Formalizer.randomStartHp(character));
			}
			name = "finishCreatingHero";
			text = "Congratulations, you are ready for adventure.";
			return Act.builder()
					.name(name)
					.text(text)
					.action(Action.builder()
							.location(Location.BOT)
							.key(toMenu)
							.buttons(new String[][]{{"Let's go"}})
							.build())
					.build();

		}
		else
		{
			Pattern pat = Pattern.compile(keyNumber);
			Matcher matcher = pat.matcher(answer);
			int hp = 0;
			while (matcher.find()) 
			{
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}

			if(hp <= 0)
			{
				hp = Formalizer.stableStartHp(character);
				character.getHp().grow(Formalizer.stableStartHp(character));
				name = "finishCreatingHero";
				text = "Nice try... I see U very smart, but you will get stable " + hp + " HP. You are ready for adventure.";
				return Act.builder()
						.name(name)
						.text(text)
						.action(Action.builder()
								.location(Location.BOT)
								.key(toMenu)
								.buttons(new String[][]{{"Let's go"}})
								.build())
						.build();

			}
			else
			{
				character.getHp().grow(hp);
				name = "finishCreatingHero";
				text = "Congratulations, you are ready for adventure.";
				return Act.builder()
						.name(name)
						.text(text)
						.action(Action.builder()
								.location(Location.BOT)
								.key(toMenu)
								.buttons(new String[][]{{"Let's go"}})
								.build())
						.build();

			}

		}
	}

	
	public Act inerAct(Action inerAction, CharacterDnd actualGameCharacter)
	{
		ScriptReader.execute(actualGameCharacter, inerAction.getComand());
		return Act.builder()
				.name("EndTree")
				.text("casted")
				.build();
	}

	public CharactersPool getCharactersPool() {
		return charactersPool;
	}

	public void setCharactersPool(CharactersPool charactersPool) {
		this.charactersPool = charactersPool;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return ControlPanel;
	}


}
