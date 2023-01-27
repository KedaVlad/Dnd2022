package com.dnd.dndTable.creatingDndObject.characteristic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Executor;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.botTable.Act;
import com.dnd.botTable.ArrayActsBuilder;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PreRoll;
import com.dnd.botTable.actions.RollAction;
import com.dnd.dndTable.creatingDndObject.ClassDnd;
import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.characteristic.Stat.Stats;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Possessions;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Formula;
import com.dnd.dndTable.rolls.Dice.Roll;


public class CharactersStat implements Serializable, Executor 
{
	
	
	private static final long serialVersionUID = 7901749239721687760L;

	private Possessions proficiency;
	private Dice hp;
	private Skill initiative;
	private Stat[] stats;
	private Skill[] skills;
	private Skill[] saveRolls;


	public CharactersStat(Possessions proficiency) 
	{
		this.proficiency = proficiency;
		hp = new Dice("Hp roll", 0, Roll.NO_ROLL);
		initiative = new Skill("Initiative", Stats.DEXTERITY);
		buildStats();
		buildSkills();
		buildSaveRoll();
	}

	public int rollHp(ClassDnd clazz, boolean random)
	{
		if(random == true)
		{
			Dice dice = getHpDice(clazz);
			dice.execute();
			int answer = dice.roll() + stats[2].getModificator();
			return answer;	
		}
		else
		{
			switch(clazz.getDiceHp())
			{
			case D6:
				return 4 + stats[2].getModificator() + hp.getBuff();
			case D8:
				return 5 + stats[2].getModificator() + hp.getBuff();
			case D10:
				return 6 + stats[2].getModificator() + hp.getBuff();
			case D12:
				return 7 + stats[2].getModificator() + hp.getBuff();
			default:
				return 0;
			}
		}
	}

	public Formula execute(RollAction action)
	{
		Formula answer = new Formula("ROLL",action.getBase());
		if(action.getDepends() != null) answer.addDicesToEnd(getMainStatDice(action.getDepends().toString()));
		if(action.isProficiency()) answer.addDicesToEnd(proficiency.getDice(action.getProficiency()));
		return answer;
	}

	public int getMainStatValue(String name)
	{
		for(Stat stat: stats)
		{
			if(stat.name.toString().equals(name)) return stat.getModificator();
		}
		return 0;

	}

	private Dice getMainStatDice(String name)
	{
		for(Stat stat: stats)
		{
			if(stat.name.toString().equals(name)) return stat.getDice();
		}
		return null;

	}

	public void setStats(int str, int dex, int con, int intl, int wis, int cha)
	{
		stats[0].up(str);
		stats[1].up(dex);
		stats[2].up(con);
		stats[3].up(intl);
		stats[4].up(wis);
		stats[5].up(cha);
	}

	private void buildStats()
	{
		stats = new Stat[]
				{
						new Stat(Stats.STRENGTH),
						new Stat(Stats.DEXTERITY),
						new Stat(Stats.CONSTITUTION),
						new Stat(Stats.INTELLIGENSE),
						new Stat(Stats.WISDOM),
						new Stat(Stats.CHARISMA)
				};
	}

	private void buildSkills()
	{
		skills = new Skill[]
				{
						new Skill(Skills.ACROBATICS.toString(), Stats.DEXTERITY),
						new Skill(Skills.ANIMAL_HANDING.toString(), Stats.WISDOM),
						new Skill(Skills.ARCANA.toString(), Stats.INTELLIGENSE),
						new Skill(Skills.ATHLETIX.toString(), Stats.STRENGTH),
						new Skill(Skills.DECEPTION.toString(), Stats.CHARISMA),
						new Skill(Skills.HISTORY.toString(), Stats.INTELLIGENSE),		
						new Skill(Skills.INSIGHT.toString(), Stats.WISDOM),
						new Skill(Skills.INTIMIDATION.toString(), Stats.CHARISMA),
						new Skill(Skills.INVESTIGATION.toString(), Stats.INTELLIGENSE),
						new Skill(Skills.MEDICINE.toString(), Stats.WISDOM),
						new Skill(Skills.NATURE.toString(), Stats.INTELLIGENSE),
						new Skill(Skills.PERCEPTION.toString(), Stats.WISDOM),
						new Skill(Skills.PERFORMANCE.toString(), Stats.CHARISMA),
						new Skill(Skills.PERSUASION.toString(), Stats.CHARISMA),
						new Skill(Skills.RELIGION.toString(), Stats.INTELLIGENSE),
						new Skill(Skills.SLEIGHT_OF_HAND.toString(), Stats.DEXTERITY),
						new Skill(Skills.STELTH.toString(), Stats.DEXTERITY),
						new Skill(Skills.SURVIVAL.toString(), Stats.WISDOM)
				};

	}

	private void buildSaveRoll()
	{
		saveRolls = new Skill[]
				{
						new SaveRoll(SaveRolls.SR_STRENGTH.toString(), Stats.STRENGTH),
						new SaveRoll(SaveRolls.SR_DEXTERITY.toString(), Stats.DEXTERITY),
						new SaveRoll(SaveRolls.SR_CONSTITUTION.toString(), Stats.CONSTITUTION),
						new SaveRoll(SaveRolls.SR_INTELLIGENSE.toString(), Stats.INTELLIGENSE),
						new SaveRoll(SaveRolls.SR_WISDOM.toString(), Stats.WISDOM),
						new SaveRoll(SaveRolls.SR_CHARISMA.toString(), Stats.CHARISMA)
				};
	}

	public void up(String name, int value)
	{
		for(Stat stat: stats)
		{
			if(stat.name.toString().equals(name))
			{
				stat.up(value);
			}
		}
	}

	public void spesialize(String name, String buff)
	{
		boolean breaker = false;
		if(initiative.getName().equals(name))
		{
			initiative.addSpesial(buff);
		}
		else if(breaker == false)
		{
			for(Skill article: saveRolls)
			{
				if(article.getName().equals(name))
				{
					article.addSpesial(buff);
					breaker = true;
					break;
				}
			}
		}
		else if(breaker == false)
		{
			for(Skill article: skills)
			{
				if(article.getName().equals(name))
				{
					article.addSpesial(buff);
					breaker = true;
					break;
				}
			}
		}
	}

	public Skill[] getSkills() 
	{
		return skills;
	}

	public Skill[] getSaveRolls() 
	{
		return saveRolls;
	}

	public Dice getHpDice(ClassDnd clazz) 
	{
		hp.setCombo(clazz.getDiceHp());
		return hp;
	}

	@Override
	public Act execute(Action action)
	{
		int condition = Executor.condition(action);
		if(action.getObjectDnd() == null)
		{
			switch(condition)
			{
			case 0:
				return characteristic();
			case 1:
				return getMenu(action);
			default:
				return Act.builder().returnTo(CHARACTERISTIC_B, CHARACTERISTIC_B).build();
			}
		}
		else
		{
			switch(condition)
			{
			case 0:
				return objectCase(action);
			case 1:
				return objectChange(action);
			default:
				return Act.builder().returnTo(CHARACTERISTIC_B, CHARACTERISTIC_B).build();
			}
		}
	}

	private Act characteristic()
	{
		return Act.builder()
					.name(CHARACTERISTIC_B)
					.text(info())
					.action(Action.builder()
							.location(Location.CHARACTER)
							.key(key())
							.buttons(new String[][]{{STAT_B, SAVE_ROLL_B, SKILL_B},{RETURN_TO_MENU}})
							.replyButtons()
							.build())
					.returnTo(MENU_B)
					.build();
	}


	private Act getMenu(Action action)
	{
		String targetMenu = action.getAnswers()[0];
		if(targetMenu.equals(STAT_B))
		{
			return statMenu();
		}
		else if(targetMenu.equals(SAVE_ROLL_B))
		{
			return saveRollMenu();
		}
		else if(targetMenu.equals(SKILL_B))
		{
			return skillMenu();
		}
		else
		{
			return Act.builder().returnTo(MENU_B, MENU_B).build();
		}
	}

	private Act objectCase(Action action)
	{
		if(action.getObjectDnd() instanceof Stat)
		{
			return getStatCase(action);
		}
		else if(action.getObjectDnd() instanceof Skill)
		{
			return getArticleCase(action);
		}
		else
		{
			return Act.builder().returnTo(CHARACTERISTIC_B, CHARACTERISTIC_B).build();
		}
	}

	private Act objectChange(Action action)
	{
		if(action.getObjectDnd() instanceof Stat)
		{
			return changeStat(action);
		}
		else if(action.getObjectDnd() instanceof Skill)
		{
			return changeArticle(action);
		}
		else
		{
			return Act.builder().returnTo(CHARACTERISTIC_B, CHARACTERISTIC_B).build();
		}
	}

	private Act statMenu()
	{
		String text = "Choose stat which you want to roll or change";
		BaseAction[][] pool = new BaseAction[][]
				{{
					Action.builder().name(stats[0].toString()).objectDnd(stats[0]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[1].toString()).objectDnd(stats[1]).location(Location.CHARACTER).key(key()).build()
				},{
					Action.builder().name(stats[2].toString()).objectDnd(stats[2]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[3].toString()).objectDnd(stats[3]).location(Location.CHARACTER).key(key()).build()
				},{
					Action.builder().name(stats[4].toString()).objectDnd(stats[4]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[5].toString()).objectDnd(stats[5]).location(Location.CHARACTER).key(key()).build()
				}};

				return Act.builder()
						.name(STAT_B)
						.text(text)
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.returnTo(CHARACTERISTIC_B)
						.build();
	}

	private Act getStatCase(Action action) 
	{
		Stat stat = (Stat) action.getObjectDnd();
		action.setButtons(stat.buildStatChangeButtons());
		return ArrayActsBuilder.builder()
				.name("StatCase")
				.pool(Act.builder()
						.name("Biba")
						.action(action)
						.text(stat.toString() + ". If u have reason to change value...")
						.build(),
						Act.builder()
						.text("... or roll.")
						.name("Bebra")
						.action(PreRoll.builder()
								.roll(RollAction.buider()
										.statDepend(stat.name)
										.build())
								.build())
						.build())
				.returnTo(STAT_B)
				.build();
	}

	private Act changeStat(Action action) 
	{
		Stat stat = (Stat) action.getObjectDnd();
		String name = stat.name.toString();
		switch(action.getAnswers()[0])
		{
		case "+1":
			up(name, +1);
			break;
		case "+2":
			up(name, +2);
			break;
		case "+3":
			up(name, +3);
			break;
		case "-1":
			up(name, -1);
			break;
		case "-2":
			up(name, -2);
			break;
		case "-3":
			up(name, -3);
			break;
		}
		return Act.builder().returnTo(CHARACTERISTIC_B, STAT_B).build();
	}

	private Act saveRollMenu()
	{
		String text = "Choose Save Roll which you want to roll or change";
		BaseAction[][] pool = new BaseAction[][]
				{{
					Action.builder().name(stats[0].SR()).objectDnd(saveRolls[0]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[1].SR()).objectDnd(saveRolls[1]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[2].SR()).objectDnd(saveRolls[2]).location(Location.CHARACTER).key(key()).build()
				},{
					Action.builder().name(stats[3].SR()).objectDnd(saveRolls[3]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[4].SR()).objectDnd(saveRolls[4]).location(Location.CHARACTER).key(key()).build(),
					Action.builder().name(stats[5].SR()).objectDnd(saveRolls[5]).location(Location.CHARACTER).key(key()).build()
				}};

				return Act.builder()
						.name(SAVE_ROLL_B)
						.text(text)
						.action(PoolActions.builder()
								.actionsPool(pool)
								.build())
						.returnTo(CHARACTERISTIC_B)
						.build();
	}

	private Act skillMenu()
	{
		String name = SKILL_B;
		String text = "Choose skill which you want to roll or change";
		BaseAction[][] pool = new BaseAction[skills.length/2][2];
		int j = 0;
		for(int i = 0; i < skills.length; i++)
		{
			int value = getMainStatValue(skills[i].getDepends().toString());
			if(skills[i].isProficiency()) 
			{
				value += proficiency.getDice(skills[i].getProficiency()).getBuff();
			}
			if(i < 9) {
				pool[i][0] = Action.builder().name(value + skills[i].getName()).location(Location.CHARACTER).key(key()).objectDnd(skills[i]).build();
			}
			else
			{
				pool[j][1] = Action.builder().name(value + skills[i].getName()).location(Location.CHARACTER).key(key()).objectDnd(skills[i]).build();
				j++;
			}
		}
		return Act.builder()
				.name(name)
				.text(text)
				.action(PoolActions.builder()
						.actionsPool(pool)
						.build())
				.returnTo(CHARACTERISTIC_B)
				.build();
	}

	private Act getArticleCase(Action action)
	{
		Skill article = (Skill) action.getObjectDnd();
		String[][] buttonsChange = buildArticleChangeButtons(article);
		String returnTo = SKILL_B;
		if(article instanceof SaveRoll)
		{
			returnTo = SAVE_ROLL_B;
		}
		if(buttonsChange == null) {
			return Act.builder()
					.name("ArticleCase")
					.text(article.skillInfo())
					.action(PreRoll.builder()
							.roll(RollAction.buider()
									.statDepend(article.getDepends())
									.proficiency(article.getProficiency())
									.build())
							.build())
					.returnTo(returnTo)
					.build();
		}
		else
		{
			action.setButtons(buttonsChange);
			return ArrayActsBuilder.builder()
					.name("ArticleCase")
					.pool(Act.builder()
							.action(action)
							.text(article.skillInfo() + ". If u have reasons up your possession of this...")
							.build(),
							Act.builder()
							.text("... or roll.")
							.action(PreRoll.builder()
									.roll(RollAction.buider()
											.statDepend(article.getDepends())
											.proficiency(article.getProficiency())
											.build())
									.build())
							.build())
					.returnTo(returnTo)
					.build();
		}
	}

	private String[][] buildArticleChangeButtons(Skill article)
	{
		if(article.getProficiency() != null)
		{
			if(article.getProficiency().equals(Proficiency.BASE))
			{
				return new String[][] {{"Up to COMPETENSE"}};
			}
			else if(article.getProficiency().equals(Proficiency.COMPETENSE))
			{
				return null;
			}
			else
			{
				return new String[][] {{"Up to PROFICIENCY"}};
			}
		}
		else
		{
			return new String[][] {{"Up to PROFICIENCY"}};
		}
	}

	

	private Act changeArticle(Action action) 
	{
		Skill skill = (Skill) action.getObjectDnd();
		switch(action.getAnswers()[0])
		{
		case "Up to COMPETENSE":
			skill.setProficiency(Proficiency.COMPETENSE);
			proficiency.add(new Possession(skill.getName(), Proficiency.COMPETENSE));
			break;
		case "Up to PROFICIENCY":
			skill.setProficiency(Proficiency.BASE);
			proficiency.add(skill.getName());
			break;
		}
		if(skill instanceof SaveRoll)
		{
			return Act.builder().returnTo(CHARACTERISTIC_B, SAVE_ROLL_B).build();
		}
		else
		{
			return Act.builder().returnTo(CHARACTERISTIC_B, SKILL_B).build();
		}
	}

	public Stat[] getStats()
	{
		return stats;
	}

	
	@Override
	public long key() 
	{
		return CHARACTERISTIC;
	}


	public String info() 
	{
		return "Some instruction for CHARACTERISTIC";
	}
}








