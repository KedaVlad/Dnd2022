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
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;

public class Rolls implements Serializable, Names, KeyWallet {

	private static final long serialVersionUID = 7901749239721687760L;

	private Dice proficiency;
	private Dice hp;
	private Article initiative;
	private List<MainStat> stats;
	private List<Article> skills;
	private List<Article> saveRolls;
	private AttackMachine attackMachine;
	private Attack targetAttack;

	public Rolls() 
	{
		proficiency = new Dice("Proficiency bonus", 2, Roll.NO_ROLL);
		hp = new Dice("Hp roll", 0, Roll.NO_ROLL);
		stats = new ArrayList<>();
		skills = new ArrayList<>();
		saveRolls = new ArrayList<>();
		initiative = new Article("Initiative", Stat.DEXTERITY);
		attackMachine = new AttackMachine();
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
			dice.setBuff(getValue(Stat.CONSTITUTION).getBuff());
			return dice.roll();	
		}
		else
		{
			switch(clazz.getDiceHp())
			{
			case D6:
				return 4 + getValue(Stat.CONSTITUTION).getBuff() + hp.roll();
			case D8:
				return 5 + getValue(Stat.CONSTITUTION).getBuff() + hp.roll();
			case D10:
				return 6 + getValue(Stat.CONSTITUTION).getBuff() + hp.roll();
			case D12:
				return 7 + getValue(Stat.CONSTITUTION).getBuff() + hp.roll();
			default:
				return 0;
			}
		}
	}

	public Formula attack(int target)
	{
		this.targetAttack = attackMachine.getAttacks().get(target);
		return buildFormula(targetAttack.attack);
	}

	public Formula hit()
	{
		Formula answer = new Formula(targetAttack.hit.name);
		answer.getFormula().add(buildValue(targetAttack.hit));
		answer.getFormula().addAll(targetAttack.hit.permanentBuff);
		return answer;
	}

	public Dice getDice(Stat name)
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

	public Formula getFormula(String name)
	{
		Article targetArticle = null;
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

		return buildFormula(targetArticle);
	}

	private Formula buildFormula(Article targetArticle)
	{
		Formula answer = new Formula(targetArticle.name);
		answer.getFormula().add(getDice(targetArticle.depends));

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

	public Dice getValue(Stat stat)
	{

		Dice answer = new Dice(stat.name(),0, Roll.NO_ROLL);
		switch(stat)
		{
		case STRENGTH:
			answer.setBuff(stats.get(0).dice.getBuff());
			break;
		case DEXTERITY:
			answer.setBuff(stats.get(1).dice.getBuff());
			break;
		case CONSTITUTION:
			answer.setBuff(stats.get(2).dice.getBuff());
			break;
		case INTELLIGENSE:
			answer.setBuff(stats.get(3).dice.getBuff());
			break;
		case WISDOM:
			answer.setBuff(stats.get(4).dice.getBuff());
			break;
		case CHARISMA:
			answer.setBuff(stats.get(5).dice.getBuff());
			break;
		default:
			break;
		}
		return answer;
	}

	public Dice getValue(String name)
	{
		Dice answer = null;
		boolean breaker = false;
		if(initiative.name.equals(name))
		{
			answer = buildValue(initiative);
		}
		else if(breaker == false)
		{
			for(Article article: saveRolls)
			{
				if(article.name.equals(name))
				{
					answer = buildValue(article);
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
					answer = buildValue(article);
					breaker = true;
					break;
				}
			}
		}
		return answer;
	}

	private Dice buildValue(Article targetArticle)
	{
		Dice answer = new Dice(targetArticle.name,0, Roll.NO_ROLL);

		answer.setBuff(getDice(targetArticle.depends).getBuff());

		if(targetArticle.competense == true)
		{
			answer.setBuff(proficiency.getBuff()*2);
		}
		else if(targetArticle.proficiency == true)
		{
			answer.setBuff(proficiency.getBuff());
		}
		else if(targetArticle.halfProf == true)
		{
			answer.setBuff(proficiency.getBuff()/2);
		}
		return answer;
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