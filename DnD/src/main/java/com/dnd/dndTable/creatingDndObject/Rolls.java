package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.HeroAction;
import com.dnd.botTable.actions.dndAction.PreRoll;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.dndAction.RollAction;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.rolls.Article;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Formula;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Rolls implements Serializable, Names, KeyWallet {

	private static final long serialVersionUID = 7901749239721687760L;

	public enum Proficiency
	{
		BASE, HALF, COMPETENSE
	}

	private Dice proficiency;
	private Dice hp;
	private Article initiative;
	private List<MainStat> stats;
	private List<Article> skills;
	private List<Article> saveRolls;

	
	public Rolls() 
	{
		proficiency = new Dice("Proficiency bonus", 2, Roll.NO_ROLL);
		hp = new Dice("Hp roll", 0, Roll.NO_ROLL);
		stats = new ArrayList<>();
		skills = new ArrayList<>();
		saveRolls = new ArrayList<>();
		initiative = new Article("Initiative", Stat.DEXTERITY);
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
			int answer = dice.roll() + stats.get(2).getModificator();
			return answer;	
		}
		else
		{
			switch(clazz.getDiceHp())
			{
			case D6:
				return 4 + stats.get(2).getModificator() + hp.getBuff();
			case D8:
				return 5 + stats.get(2).getModificator() + hp.getBuff();
			case D10:
				return 6 + stats.get(2).getModificator() + hp.getBuff();
			case D12:
				return 7 + stats.get(2).getModificator() + hp.getBuff();
			default:
				return 0;
			}
		}
	}

	public Formula execute(RollAction action)
	{
		Formula answer = new Formula("ROLL");
		answer.getFormula().addAll(action.getBase());
		answer.getFormula().add(getDice(action.getDepends().toString()));
		if(action.isProficiency())
		{
			answer.getFormula().add(getProf(action.getProficiency()));
		}
		return answer;
	}

	private Dice getProf(Proficiency article)
	{
		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(article)
		{
		case COMPETENSE:
			answer.setBuff(proficiency.getBuff()*2);
			break;
		case BASE:
			answer = proficiency;
			break;
		case HALF:
			answer.setBuff(proficiency.getBuff()/2);
			break;
		}
		return answer;
	}

	public int getValue(String name)
	{
		for(MainStat stat: stats)
		{
			if(stat.name.toString().equals(name)) return stat.getModificator();
		}
		return 0;

	}

	private Dice getDice(String name)
	{
		for(MainStat stat: stats)
		{
			if(stat.name.toString().equals(name)) return stat.getDice();
		}
		return null;

	}

	public void getStatAction(String name)
	{
		boolean breaker = false;

		if(breaker == false)
		{
			for(MainStat stat: stats)
			{
				if(stat.name.toString() == name)
				{
					//this.targetAction = new Action(name, new Article(name, stat.name));
					breaker = true;
					break;
				}
			}
		}
		if(breaker == false)
		{
			for(Article article: skills)
			{
				if(article.getName() == name)
				{
					//this.targetAction = new Action(name, article);
				}
			}
		}
		if(breaker == false)
		{
			for(Article article: skills)
			{
				if(article.getName() == name)
				{
					//this.targetAction = new Action(name, article);
				}
			}
		}
	}

	public void setStats(int str, int dex, int con, int intl, int wis, int cha)
	{
		stats.get(0).up(str);
		stats.get(1).up(dex);
		stats.get(2).up(con);
		stats.get(3).up(intl);
		stats.get(4).up(wis);
		stats.get(5).up(cha);
	}

	private void buildStats()
	{
		stats.add(new MainStat(Stat.STRENGTH));
		stats.add(new MainStat(Stat.DEXTERITY));
		stats.add(new MainStat(Stat.CONSTITUTION));
		stats.add(new MainStat(Stat.INTELLIGENSE));
		stats.add(new MainStat(Stat.WISDOM));
		stats.add(new MainStat(Stat.CHARISMA));
	}

	private void buildSkills()
	{
		skills.add(new Article(Skill.ACROBATICS.toString(), Stat.DEXTERITY));
		skills.add(new Article(Skill.ANIMAL_HANDING.toString(), Stat.WISDOM));
		skills.add(new Article(Skill.ARCANA.toString(), Stat.INTELLIGENSE));
		skills.add(new Article(Skill.ATHLETIX.toString(), Stat.STRENGTH));
		skills.add(new Article(Skill.DECEPTION.toString(), Stat.CHARISMA));
		skills.add(new Article(Skill.HISTORY.toString(), Stat.INTELLIGENSE));
		skills.add(new Article(Skill.INSIGHT.toString(), Stat.WISDOM));
		skills.add(new Article(Skill.INTIMIDATION.toString(), Stat.CHARISMA));
		skills.add(new Article(Skill.INVESTIGATION.toString(), Stat.INTELLIGENSE));
		skills.add(new Article(Skill.MEDICINE.toString(), Stat.WISDOM));
		skills.add(new Article(Skill.NATURE.toString(), Stat.INTELLIGENSE));
		skills.add(new Article(Skill.PERCEPTION.toString(), Stat.WISDOM));
		skills.add(new Article(Skill.PERFORMANCE.toString(), Stat.CHARISMA));
		skills.add(new Article(Skill.PERSUASION.toString(), Stat.CHARISMA));
		skills.add(new Article(Skill.RELIGION.toString(), Stat.INTELLIGENSE));
		skills.add(new Article(Skill.SLEIGHT_OF_HAND.toString(), Stat.DEXTERITY));
		skills.add(new Article(Skill.STELTH.toString(), Stat.DEXTERITY));
		skills.add(new Article(Skill.SURVIVAL.toString(), Stat.WISDOM));

	}

	private void buildSaveRoll()
	{
		saveRolls.add(new Article(SaveRoll.SR_STRENGTH.toString(), Stat.STRENGTH));
		saveRolls.add(new Article(SaveRoll.SR_DEXTERITY.toString(), Stat.DEXTERITY));
		saveRolls.add(new Article(SaveRoll.SR_CONSTITUTION.toString(), Stat.CONSTITUTION));
		saveRolls.add(new Article(SaveRoll.SR_INTELLIGENSE.toString(), Stat.INTELLIGENSE));
		saveRolls.add(new Article(SaveRoll.SR_WISDOM.toString(), Stat.WISDOM));
		saveRolls.add(new Article(SaveRoll.SR_CHARISMA.toString(), Stat.CHARISMA));
	}

	public void up(String name, int value)
	{
		for(MainStat stat: stats)
		{
			if(stat.name.toString().equals(name))
			{
				stat.up(value);
			}
		}
	}

	public void toCompetense(String... names)
	{
		for(String name: names)
		{
			for(Article article: skills)
			{
				if(article.getName().equals(name))
				{
					article.setProficiency(Proficiency.COMPETENSE);
					break;
				}
			}
		}
	}

	public void toProficiency(String... names)
	{
		for(String name: names)
		{
			boolean breaker = false;
			if(initiative.getName().equals(name))
			{
				if(!initiative.isProficiency() ||
						initiative.getProficiency().equals(Proficiency.COMPETENSE))
				{
					initiative.setProficiency(Proficiency.BASE);
				}
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.getName().equals(name))
					{
						if(!article.isProficiency() || 
								article.getProficiency().equals(Proficiency.COMPETENSE))
						{
							article.setProficiency(Proficiency.BASE);
						}
						breaker = true;
						break;
					}
				}
			}
			else if(breaker == false)
			{
				for(Article article: skills)
				{
					if(article.getName().equals(name))
					{
						if(!article.isProficiency() || 
								article.getProficiency().equals(Proficiency.COMPETENSE))
						{
							article.setProficiency(Proficiency.BASE);
						}
						breaker = true;
						break;
					}
				}
			}
		}
	}

	public void toHalfProf(String... names)
	{
		for(String name: names)
		{
			for(Article article: skills)
			{
				if(article.getName().equals(name))
				{
					if(!article.isProficiency())
					{
						initiative.setProficiency(Proficiency.BASE);
					}
					break;
				}
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
			for(Article article: saveRolls)
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
			for(Article article: skills)
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

	public List<Article> getSkills() 
	{
		return skills;
	}

	public List<Article> getSaveRolls() 
	{
		return saveRolls;
	}

	public Dice getProficiency() 
	{
		return proficiency;
	}

	public Dice getHpDice(ClassDnd clazz) 
	{
		hp.setCombo(clazz.getDiceHp());
		return hp;
	}

	public Action getRollsMenu() 
	{
		String name = "RollsMenu";
		Action[] array = {statMenu(), SaveRollMenu(), SkillMenu()};
		return ArrayAction.create(name, NO_ANSWER, array);
	}

	private Action statMenu()
	{
		String name = "StatsMenu";
		String text = "STATS";
		long key = STAT;
		RegistrateAction[][] actions = new RegistrateAction[][]
				{{
					RegistrateAction.create(stats.get(0).toString(), stats.get(0)),
					RegistrateAction.create(stats.get(1).toString(), stats.get(1))
				},
			{
					RegistrateAction.create(stats.get(2).toString(), stats.get(2)),
					RegistrateAction.create(stats.get(3).toString(), stats.get(3)),
			},
			{
				RegistrateAction.create(stats.get(4).toString(), stats.get(4)),
				RegistrateAction.create(stats.get(5).toString(), stats.get(5)),
			}
				};
				return HeroAction.create(name, key, text, actions);
	}

	public Action getStatCase(RegistrateAction action) 
	{
		MainStat stat = (MainStat) action.getTarget();
		String changeStatText = stat.toString() + ". If u have reason to change value...";
		
		ChangeAction changeAction = ChangeAction.create(action, changeStatText, buildStatChangeButtons(stat));
		PreRoll rollAction = PreRoll.create("... or roll.", RollAction.create("RollStat", ROLL, null, stat.name, null));
		Action[] buttons = new Action[]{changeAction, rollAction};
		return ArrayAction.create("StatCase", NO_ANSWER , buttons);
	}
	
	private String[][] buildStatChangeButtons(MainStat stat)
	{
		
		String[][] base = new String[][] {{"-3","-2","-1","+1","+2","+3"}};
		int max = stat.maxValue - stat.value;
		int min = stat.value - 3;
		if(max > 3) max = 3;
		if(min > 3) min = 3;
		String[][] answer = new String[1][min+max];
		if(answer[0].length == 6)
		{
			return base;
		}
		else if(min < max)
		{
			int j = 0;
			for(int i = 6 - answer[0].length; i < 6; i++)
			{
				answer[0][j] = base[0][i];
				j++;
			}
		}
		else
		{
			for(int i = 0; i < answer[0].length; i ++)
			{
				answer[0][i] = base[0][i];
			}
		}
		return answer;
	}

	public Action changeStat(ChangeAction action) 
	{
		MainStat stat = (MainStat) action.getTarget();
		String name = stat.name.toString();

		switch(action.getAnswer())
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
		return action.beckKey("Menu").beckCall(menu +"STATS");
	}

	private Action SaveRollMenu()
	{
		String name = "SaveRollMenu";
		String text = "SAVE ROLLS";
		long key = SAVE_ROLL;
		RegistrateAction[][] actions = new RegistrateAction[][]
				{{
					RegistrateAction.create(stats.get(0).SR(), saveRolls.get(0)),
					RegistrateAction.create(stats.get(1).SR(), saveRolls.get(1)),
					RegistrateAction.create(stats.get(2).SR(), saveRolls.get(2)),
				},
			{
					RegistrateAction.create(stats.get(3).SR(), saveRolls.get(3)),
					RegistrateAction.create(stats.get(4).SR(), saveRolls.get(4)),
					RegistrateAction.create(stats.get(5).SR(), saveRolls.get(5)),
			}
				};
				return HeroAction.create(name, key, text, actions);
	}

	private Action SkillMenu()
	{
		String name = "SkillMenu";
		String text = "SKILLS";
		long key = SKILL;
		RegistrateAction[][] actions = new RegistrateAction[skills.size()/2][2];

		for(int i = 0; i < 9; i++)
		{
			int value = getValue(skills.get(i).getDepends().toString());
			if(skills.get(i).isProficiency()) 
			{
				value += getProf(skills.get(i).getProficiency()).getBuff();
			}

			actions[i][0] = RegistrateAction.create( value + 
					" " + skills.get(i).getName(), skills.get(i));
		}
		int j = 0;
		for(int i = 9; i < skills.size(); i++)
		{
			int value = getValue(skills.get(i).getDepends().toString());
			if(skills.get(i).isProficiency()) 
			{
				value += getProf(skills.get(i).getProficiency()).getBuff();
			}

			actions[j][1] = RegistrateAction.create( value + 
					" " + skills.get(i).getName(), skills.get(i));
			j++;
		}
		return HeroAction.create(name, key, text, actions);
	}

	public Action getArticleCase(RegistrateAction action)
	{
		Article article = (Article) action.getTarget();

		String changeStatText = articleInfo(article) + ". If u have reasons up your possession of this...";
		String[][] buttonsChange = buildArticleChangeButtons(article);
		if(buttonsChange == null) {
			return PreRoll.create(articleInfo(article), RollAction.create("RollArticle", ROLL, null, article.getDepends(), article.getProficiency()));
		}
		else
		{
		ChangeAction changeAction = ChangeAction.create(action, changeStatText, buttonsChange);
		PreRoll rollAction = PreRoll.create("... or roll.", RollAction.create("RollArticle", ROLL, null, article.getDepends(), article.getProficiency()));
		Action[] buttons = new Action[]{changeAction, rollAction};
		return ArrayAction.create("ArticleCase", NO_ANSWER , buttons);
		}
	}

	private String[][] buildArticleChangeButtons(Article article)
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
	
	private String articleInfo(Article article)
	{
		String answer = article.getName() + "depends on " + article.getDepends().toString();
		if(article.getProficiency() != null)
		{
			if(article.getProficiency().equals(Proficiency.COMPETENSE))
			{
				return answer += " + competense";
			}
			else if(article.getProficiency().equals(Proficiency.BASE))
			{
				return answer += " + proficiency";
			}
			else
			{
				return answer += " + half proficiency";
			}
		}
		else
		{
			return answer;
		}
	}

	public Action changeArticle(ChangeAction action) 
	{
		Article stat = (Article) action.getTarget();
		switch(action.getAnswer())
		{
		case "Up to COMPETENSE":
			stat.setProficiency(Proficiency.COMPETENSE);
			break;
		case "Up to PROFICIENCY":
			stat.setProficiency(Proficiency.BASE);
			break;
		}
		return action.beckKey("Menu").beckCall(menu +"STATS");
	}
	
	List<MainStat> getStats()
	{
		return stats;
	}

	class MainStat implements Serializable, ObjectDnd
	{
		private static final long serialVersionUID = 1L;
		private Stat name;
		private int value;
		private int maxValue = 20;
		private int[] elseSourceValue = {0, 0};
		
		//Dice dice;

		int getValue()
		{
			int target = value + elseSourceValue[1];
			if(elseSourceValue[0] != 0)
			{
				return elseSourceValue[0];
			}
			else
			{
				return target;
			}
		}
		
		int getModificator()
		{
			return (getValue()- 10)/2;
		}
		
		Dice getDice()
		{
			return new Dice(name.toString(), getModificator(), Roll.NO_ROLL);
		}
		
		private MainStat(Stat name)
		{
			this.name = name;
			this.value = 0;
		}

		void up(int value)
		{
			this.value += value;
		}

		private String SR()
		{
			return getModificator() + " " + name.toString();
		}

		public String toString()
		{
			return value +"(" + getModificator() + ")" + " " +name.toString();
		}

		@Override
		public long key()
		{
			return STAT;
		}

		void setMaxValue(int maxValue) 
		{
			this.maxValue = maxValue;
		}

	}






}