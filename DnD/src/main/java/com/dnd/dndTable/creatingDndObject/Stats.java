package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.Names;

public class Stats implements Serializable, Names {
	
	private static final long serialVersionUID = 7901749239721687760L;

	private List<Article> stats;
	private List<Article> skills;
    private List<Article> saveRolls;
	
	Stats(int str, int dex, int con, int intl, int wis, int cha) 
	{
		stats = new ArrayList<>();
		skills = new ArrayList<>();
		saveRolls = new ArrayList<>();
		
		stats.add(new Article(Stat.STRENGTH.toString(), str));
		stats.add(new Article(Stat.DEXTERITY.toString(), dex));
		stats.add(new Article(Stat.CONSTITUTION.toString(), con));
		stats.add(new Article(Stat.INTELLIGENSE.toString(), intl));
		stats.add(new Article(Stat.WISDOM.toString(), wis));
		stats.add(new Article(Stat.CHARISMA.toString(), cha));
		
		
		setStartSkills();
		setStartSaveRoll();
		
	}

	private void setStartSkills()
	{
		skills.add(new Article("Acrobatics", stats.get(1).value));
		skills.add(new Article("Animal Handing", stats.get(4).value));
		skills.add(new Article("Arcana", stats.get(3).value));
		skills.add(new Article("Athletix", stats.get(0).value));
		skills.add(new Article("Deception", stats.get(5).value));
		skills.add(new Article("History", stats.get(3).value));
		skills.add(new Article("Insight", stats.get(4).value));
		skills.add(new Article("Intimidation", stats.get(5).value));
		skills.add(new Article("Investigation", stats.get(3).value));
		skills.add(new Article("Medicine", stats.get(4).value));
		skills.add(new Article("Nature", stats.get(3).value));
		skills.add(new Article("Perception", stats.get(4).value));
		skills.add(new Article("Performance", stats.get(5).value));
		skills.add(new Article("Persuasion", stats.get(5).value));
		skills.add(new Article("Religion", stats.get(3).value));
		skills.add(new Article("Sleight of Hand", stats.get(1).value));
		skills.add(new Article("Stealth", stats.get(1).value));
		skills.add(new Article("Survival", stats.get(4).value));
		
	}
	
	private void setStartSaveRoll()
	{
		saveRolls.add(new Article("SR Strength", stats.get(0).value));
		saveRolls.add(new Article("SR Dexterity", stats.get(1).value));
		saveRolls.add(new Article("SR Constitution", stats.get(2).value));
		saveRolls.add(new Article("SR Intelligense" , stats.get(3).value));
		saveRolls.add(new Article("SR Wisdom" , stats.get(4).value));
		saveRolls.add(new Article("SR Charisma" , stats.get(5).value));
	}

	public List<Article> getStats() 
	{
		return stats;
	}
	
	public void setStat(String name, int number) 
	{
	
		switch(name)
		{
		case "Strength":
			stats.get(0).value += number;
			break;
		case "Dexterity":
			stats.get(1).value += number;
			break;
		case "Constitution":
			stats.get(2).value += number;
			break;
		case "Intelligense":
			stats.get(3).value += number;
			break;
		case "Wisdom":
			stats.get(4).value += number;
			break;
		case "Charisma":
			stats.get(5).value += number;
			break;
		}
	}
	
	public void buff(String name, int value)
	{
		
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
		
		final String name;
		int value;
		int prof;
		
		
		
		Article(String name, int value)
		{
			this.name = name;
			this.value = value;
		}
		

		void setProfBuf(int prof) 
{
			
			if(this.prof == 0)
			{
			this.prof = prof;
			value += this.prof;
			}
			else
			{
			this.prof += prof - this.prof;
			value += prof - this.prof;
			}
		}
	}
	
	

}
