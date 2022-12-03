package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Activator;
import com.dnd.KeyWallet;
import com.dnd.Names;
import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Rolls implements Serializable, Names, KeyWallet {

	private static final long serialVersionUID = 7901749239721687760L;

	private Dice proficiency;
	private Dice hp;
	private Article initiative;
	private List<MainStat> stats;
	private List<Article> skills;
	private List<Article> saveRolls;
	private Action targetAction;
	

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
	
	
	public int getHp(ClassDnd clazz, boolean random)
	{
		if(random == true)
		{
			Dice dice = hp;
			dice.setCombo(clazz.getDiceHp());
			dice.setBuff(stats.get(2).dice.getBuff());
			return dice.roll();	
		}
		else
		{
			switch(clazz.getDiceHp())
			{
			case D6:
				return 4 + stats.get(2).dice.getBuff() + hp.roll();
			case D8:
				return 5 + stats.get(2).dice.getBuff() + hp.roll();
			case D10:
				return 6 + stats.get(2).dice.getBuff() + hp.roll();
			case D12:
				return 7 + stats.get(2).dice.getBuff() + hp.roll();
			default:
				return 0;
			}
		}
	}

	public Formula stepOne()
	{
		if(targetAction.isTrueStricke() && targetAction.isOneStep())
		{
			return buildHitFormula(targetAction.getStepOne());
		}
		else if(targetAction.isOneStep())
		{
			return buildAttackFormula(targetAction.getStepOne());
		}
		else if(targetAction.isTrueStricke())
		{
			return buildTrueStrikeFormula(targetAction.getStepOne());
		}
		else
		{
			return buildAttackFormula(targetAction.getStepOne());
		}

	}

	private Formula buildAttackFormula(Article article)
	{
		Formula answer = new Formula(article.getName());
		answer.getFormula().add(new Dice("Base", 0, Roll.D20));
		answer.getFormula().add(getValue(article.getDepends()));

		if(getProf(article) != null)
		{
			answer.getFormula().add(getProf(article));
		}

		answer.getFormula().addAll(article.permanentBuff);
		return answer;
	}

	private Formula buildHitFormula(Article article)
	{
		Formula answer = new Formula(article.getName());
		answer.getFormula().add(getValue(article.getDepends()));

		if(getProf(article) != null)
		{
			answer.getFormula().add(getProf(article));
		}

		answer.getFormula().addAll(article.permanentBuff);
		return answer;
	}

	private Formula buildTrueStrikeFormula(Article article)
	{
		Formula answer = new Formula(article.getName());
		answer.getFormula().add(new Dice("Base", 8, Roll.NO_ROLL));
		answer.getFormula().add(getValue(article.getDepends()));

		if(getProf(article) != null)
		{
			answer.getFormula().add(getProf(article));
		}

		answer.getFormula().addAll(article.permanentBuff);
		return answer;
	}

	private Dice getProf(Article article)
	{
		Dice answer = null;
		if(article.isCompetense())
		{
			answer = proficiency;
			answer.setBuff(proficiency.getBuff()*2);

		}
		else if(article.isProficiency())
		{
			answer = proficiency;
		}
		else if(targetAction.getStepOne().isHalfProf() == true)
		{
			answer = proficiency;
			answer.setBuff(proficiency.getBuff()/2);
		}

		return answer;
	}

	private Dice getValue(Stat name)
	{
		switch(name)
		{
		case CHARISMA:
			return stats.get(0).dice;
		case CONSTITUTION:
			return stats.get(1).dice;
		case DEXTERITY:
			return stats.get(2).dice;
		case INTELLIGENSE:
			return stats.get(3).dice;
		case STRENGTH:
			return stats.get(4).dice;
		case WISDOM:
			return stats.get(5).dice;
		default:
			return null;
		}
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
					this.targetAction = new Action(name, new Article(name, stat.name));
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
					this.targetAction = new Action(name, article);
				}
			}
		}
		if(breaker == false)
		{
			for(Article article: skills)
			{
				if(article.getName() == name)
				{
					this.targetAction = new Action(name, article);
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

	public void up(Stat name, int value)
	{
		for(MainStat stat: stats)
		{
			if(stat.name.equals(name))
			{
				stat.up(value);
			}
		}
	}

	public void up(String name, Dice dice)
	{
		boolean breaker = false;

		if(initiative.getName().equals(name))
		{
			initiative.permanentBuff.add(dice);
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.getName().equals(name))
				{
					article.permanentBuff.add(dice);
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
					article.permanentBuff.add(dice);
					breaker = true;
					break;
				}
			}
		}
	}

	public void toCompetense(String... names)
	{
		for(String name: names)
		{
			boolean breaker = false;
			if(initiative.getName().equals(name))
			{
				initiative.setCompetense(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.getName().equals(name))
					{
						article.setCompetense(true);
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
						article.setCompetense(true);
						breaker = true;
						break;
					}
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
				initiative.setProficiency(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.getName().equals(name))
					{
						article.setProficiency(true);
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
						article.setProficiency(true);
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
			boolean breaker = false;
			if(initiative.getName().equals(name))
			{
				initiative.setHalfProf(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.getName().equals(name))
					{
						article.setHalfProf(true);
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
						article.setHalfProf(true);
						breaker = true;
						break;
					}
				}
			}
		}
	}

	public void spesialize(String name, String buff)
	{

		boolean breaker = false;
		if(initiative.getName().equals(name))
		{
			initiative.spesial.add(buff);
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.getName().equals(name))
				{
					article.spesial.add(buff);
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
					article.spesial.add(buff);
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



	public void setTargetAct(Action targetAct) 
	{
		this.targetAction = targetAct;
	}

	class MainStat implements Serializable
	{
		private static final long serialVersionUID = 1L;
		Stat name;
		int value;
		Dice dice;

		MainStat(Stat name)
		{
			this.name = name;
			this.value = 0;
			this.dice = new Dice(name.toString(), 0, Roll.NO_ROLL);
		}

		void up(int value)
		{
			this.value += value;
			dice.setBuff((this.value - 10)/2);
		}

		public String toString()
		{
			return name.toString() + " : " + dice.getBuff();
		}

	}

}