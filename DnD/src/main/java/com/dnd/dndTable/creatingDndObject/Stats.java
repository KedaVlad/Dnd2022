package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.KeyWallet;
import com.dnd.Names;

public class Stats implements Serializable, Names, KeyWallet {

	private static final long serialVersionUID = 7901749239721687760L;

	private List<Article> stats;
	private List<Article> skills;
	private List<Article> saveRolls;

	
	Stats() 
	{
		stats = new ArrayList<>();
		skills = new ArrayList<>();
		saveRolls = new ArrayList<>();

		stats.add(new Article(Stat.STRENGTH.toString(), Stat.STRENGTH, 0));
		stats.add(new Article(Stat.DEXTERITY.toString(), Stat.DEXTERITY, 0));
		stats.add(new Article(Stat.CONSTITUTION.toString(), Stat.CONSTITUTION, 0));
		stats.add(new Article(Stat.INTELLIGENSE.toString(), Stat.INTELLIGENSE, 0));
		stats.add(new Article(Stat.WISDOM.toString(), Stat.WISDOM, 0));
		stats.add(new Article(Stat.CHARISMA.toString(), Stat.CHARISMA, 0));


		setStartSkills();
		setStartSaveRoll();
		updateAll();

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

	public List<Article> getStats() 
	{
		return stats;
	}

	public void buff(String name, int value)
	{
		boolean breaker = false;
		if(breaker == false) 
		{
			for(int i = 0; i < saveRolls.size(); i++)
			{
				if(name.contains(saveRolls.get(i).name))
				{
					saveRolls.get(i).setElseBuff(value);
					breaker = true;
					break;
				}
			}
		}
		else if(breaker == false) 
		{
			for(int i = 0; i < stats.size(); i++)
			{
				if(name.contains(stats.get(i).name))
				{
					stats.get(i).up(value);
					breaker = true;
					updateSome(stats.get(i).depends);
					break;
				}
			}
		}
		else if(breaker == false) 
		{
			for(int i = 0; i < skills.size(); i++)
			{
				if(name.contains(skills.get(i).name))
				{
					skills.get(i).setElseBuff(value);
					breaker = true;
					break;
				}
			}
		}	
	}

	public void spesialize(String buff)
	{

		boolean breaker = false;

		if(breaker == false)
		{
			for(Article saveRoll: this.saveRolls)
			{
				if(buff.contains(saveRoll.name))
				{
					saveRoll.spesial.add(buff);
					breaker = true;
					break;
				}
			}
		}

		if(breaker == false)
		{
			for(Article stat: this.stats)
			{
				if(buff.contains(stat.name))
				{
					stat.spesial.add(buff);
					breaker = true;
					break;
				}
			}
		}

		if(breaker == false)
		{
			for(Article skill: this.skills)
			{
				if(buff.contains(skill.name))
				{
					skill.spesial.add(buff);
					breaker = true;
					break;
				}
			}
		}
	}

	public void setSpesial(SaveRoll saveRoll, String buff)
	{
		for(Article saveRolls: this.saveRolls)
		{
			if(saveRolls.name.contains(saveRoll.toString()))
			{
				saveRolls.spesial.add(buff);
				break;
			}
		}
	}

	public void setSpesial(Stat stat, String buff)
	{
		for(Article stats: this.stats)
		{
			if(stats.name.contains(stat.toString()))
			{
				stats.spesial.add(buff);
				break;
			}
		}
	}


	private void updateAll()
	{
		updateSome(Stat.STRENGTH);
		updateSome(Stat.DEXTERITY);
		updateSome(Stat.CONSTITUTION);
		updateSome(Stat.INTELLIGENSE);
		updateSome(Stat.WISDOM);
		updateSome(Stat.CHARISMA);
	}

	private void updateSome(Stat stat)
	{
		for(Article base: stats)
		{
			for(Article article: saveRolls)
			{
				if(base.depends.equals(article.depends))

				{
					article.up(base.base - article.base);
				}
			}
			for(Article article: skills)
			{
				if(base.depends.equals(article.depends))

				{
					article.up(base.base - article.base);
				}
			}
		}
	}

	public List<Article> getSkills() {
		return skills;
	}

	public List<Article> getSaveRolls() {
		return saveRolls;
	}

	public int getValue(int place, int article)
	{
		switch(place)
		{
		case 0:
			return stats.get(article).value;
		case 1:
			return skills.get(article).value;
		case 2:
			return saveRolls.get(article).value;
		}
		return 0;
	}

	class Article implements Serializable
	{

		private static final long serialVersionUID = 8492783248077356748L;

		String name;
		int value;
		int base;
		int prof;
		int elseBuff;
		List <String> spesial;
		Stat depends;

		Article(){}
		
		Article(String name, Stat depends, int base)
		{
			this.name = name;
			this.base = base;
			this.depends = depends;
			spesial = new ArrayList<>();
			update();

		}

		Article(String name, Stat depends)
		{
			this.name = name;
			this.depends = depends;

			switch(depends)
			{
			case STRENGTH:
				base = stats.get(0).base;
				break;

			case DEXTERITY:
				base = stats.get(1).base;
				break;

			case CONSTITUTION:
				base = stats.get(2).base;
				break;

			case INTELLIGENSE:
				base = stats.get(3).base;
				break;

			case WISDOM:
				base = stats.get(4).base;
				break;

			case CHARISMA:
				base = stats.get(5).base;
				break;
			}	
			update();
		}

		void setProfBuf(int prof) 
		{

			if(this.prof == 0)
			{
				this.prof = prof;
			}
			else
			{
				this.prof += prof - this.prof;
			}
			update();
		}

		void setElseBuff(int elseBuff)
		{
			this.elseBuff = elseBuff;
			update();
		}

		void up(int value)
		{
			base += value;
			update();
		}

		void update()
		{
			value = base + prof + elseBuff;
		}

		
	}



}
