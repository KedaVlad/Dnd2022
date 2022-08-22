package com.dnd.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.creatingCharacter.skills.Skill;
import com.dnd.creatingCharacter.skills.SkillsSource;
import com.dnd.creatingCharacter.skills.Spells;


public class SkillFactory implements Factory, SkillsSource {

	
	public static Spells createSpell(String name) {
		File spells = new File(spellsSource);
		Scanner skillsScaner = null;
		try {
			skillsScaner = new Scanner(spells);
			Pattern findDescriptionSkills = Pattern.compile("^"+name+"", Pattern.CASE_INSENSITIVE);
			while(skillsScaner.hasNextLine()) {
				String nextSkill = skillsScaner.nextLine();

				Matcher rightSkill = findDescriptionSkills.matcher(nextSkill);
				if(rightSkill.find()) {
					
                    String description = nextSkill.replaceAll("^"+name+" (.*)","$1");
					return new Skill(name, description);

				}

			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			skillsScaner.close();
		}
		return null;
	}
	
	public static Skill createSkill(String name) {

		File skills = new File(skillsSource);
		Scanner skillsScaner = null;
		try {
			skillsScaner = new Scanner(skills);
			Pattern findDescriptionSkills = Pattern.compile("^*"+name+"", Pattern.CASE_INSENSITIVE);
			while(skillsScaner.hasNextLine()) {
				String nextSkill = skillsScaner.nextLine();

				Matcher rightSkill = findDescriptionSkills.matcher(nextSkill);
				if(rightSkill.find()) {
					
                    String description = nextSkill.replaceAll("^"+name+" (.*)","$1");
					return new Skill(name, description);

				}

			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			skillsScaner.close();
		}
		return null;

	}

}
