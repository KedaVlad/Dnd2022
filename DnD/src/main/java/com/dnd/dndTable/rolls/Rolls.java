package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Dice;
import com.dnd.KeyWallet;
import com.dnd.Names;
import com.dnd.Dice.Roll;
import com.dnd.Formula;
import com.dnd.Names.Stat;

public class Rolls implements Serializable, Names, KeyWallet {

	private static final long serialVersionUID = 7901749239721687760L;

	private Dice proficiency;
	private Article initiative;
	private List<MainStat> stats;
	private List<Article> skills;
	private List<Article> saveRolls;


	public Rolls() 
	{
		proficiency = new Dice("Proficiency bonus", 2, Roll.NO_ROLL);
		stats = new ArrayList<>();
		skills = new ArrayList<>();
		saveRolls = new ArrayList<>();
		initiative = new Article("Initiative", Stat.DEXTERITY);
		setStartStats();
		setStartSkills();
		setStartSaveRoll();
	}

	void setStats(int str, int dex, int con, int intl, int wis, int cha)
	{
		stats.get(0).up(str);
		stats.get(1).up(dex);
		stats.get(2).up(con);
		stats.get(3).up(intl);
		stats.get(4).up(wis);
		stats.get(5).up(cha);
	}

	private void setStartStats()
	{
		stats.add(new MainStat(Stat.STRENGTH));
		stats.add(new MainStat(Stat.DEXTERITY));
		stats.add(new MainStat(Stat.CONSTITUTION));
		stats.add(new MainStat(Stat.INTELLIGENSE));
		stats.add(new MainStat(Stat.WISDOM));
		stats.add(new MainStat(Stat.CHARISMA));
	}

	private void setStartSkills()
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

	private void setStartSaveRoll()
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

		if(initiative.name.equals(name))
		{
			initiative.permanentBuff.add(dice);
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.name.equals(name))
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
				if(article.name.equals(name))
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
			if(initiative.name.equals(name))
			{
				initiative.setCompetense(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.name.equals(name))
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
					if(article.name.equals(name))
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
			if(initiative.name.equals(name))
			{
				initiative.setProficiency(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.name.equals(name))
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
					if(article.name.equals(name))
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
			if(initiative.name.equals(name))
			{
				initiative.setHalfProf(true);
			}
			else if(breaker == false)
			{
				for(Article article: saveRolls)
				{
					if(article.name.equals(name))
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
					if(article.name.equals(name))
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
		if(initiative.name.equals(name))
		{
			initiative.spesial.add(buff);
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.name.equals(name))
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
				if(article.name.equals(name))
				{
					article.spesial.add(buff);
					breaker = true;
					break;
				}
			}
		}
	}

	public Dice roll(Stat name)
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

	public Formula roll(String name)
	{
		Article targetArticle = null;
		Formula answer = new Formula(name);

		boolean breaker = false;
		if(initiative.name.equals(name))
		{
			targetArticle = initiative;
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.name.equals(name))
				{
					targetArticle = article;
					breaker = true;
					break;
				}
			}
		}
		else if(breaker == false)
		{
			for(Article article: skills)
			{
				if(article.name.equals(name))
				{
					targetArticle = article;
					breaker = true;
					break;
				}
			}
		}

		switch(targetArticle.depends)
		{
		case STRENGTH:
			answer.getFormula().add(stats.get(0).dice);
			break;
		case DEXTERITY:
			answer.getFormula().add(stats.get(1).dice);
			break;
		case CONSTITUTION:
			answer.getFormula().add(stats.get(2).dice);
			break;
		case INTELLIGENSE:
			answer.getFormula().add(stats.get(3).dice);
			break;
		case WISDOM:
			answer.getFormula().add(stats.get(4).dice);
			break;
		case CHARISMA:
			answer.getFormula().add(stats.get(5).dice);
			break;
		default:
			break;
		}

		if(targetArticle.competense == true)
		{
			Dice changeProf = proficiency;
			changeProf.setBuff(changeProf.getBuff()*2);
			answer.getFormula().add(changeProf);
		}
		else if(targetArticle.proficiency == true)
		{
			answer.getFormula().add(proficiency);
		}
		else if(targetArticle.halfProf == true)
		{
			Dice changeProf = proficiency;
			changeProf.setBuff(changeProf.getBuff()*2);
			answer.getFormula().add(changeProf);
		}
		answer.getFormula().addAll(targetArticle.permanentBuff);
		return answer;
	}

	public List<Article> getSkills() 
	{
		return skills;
	}

	public List<Article> getSaveRolls() 
	{
		return saveRolls;
	}

	public Dice getProficiency() {
		return proficiency;
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
			this.dice = new Dice(name.toString(), 0, Roll.D20);
		}

		void up(int value)
		{
			this.value += value;
			dice.setBuff((value - 10)/2);
		}
		
		public String toString()
		{
			return name.toString() + " : " + dice.getBuff();
		}
		
	}

}



class Article implements Serializable
{
	private static final long serialVersionUID = 1L;
	String name;
	Stat depends;
	boolean proficiency;
	boolean halfProf;
	boolean competense;
	List <String> spesial;
	List <Dice> permanentBuff;

	Article(String name, Stat depends)
	{
		this.name = name;
		this.depends = depends;
		spesial = new ArrayList<>();
		permanentBuff = new ArrayList<>();
	}

	public void setProficiency(boolean proficiency) {
		this.proficiency = proficiency;
	}

	public void setHalfProf(boolean halfProf) {
		this.halfProf = halfProf;
	}

	public void setCompetense(boolean competense) {
		this.competense = competense;
	}
	
}