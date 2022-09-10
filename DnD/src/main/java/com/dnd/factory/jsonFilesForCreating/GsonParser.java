package com.dnd.factory.jsonFilesForCreating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.dnd.creatingCharacter.skills.Skill;
import com.google.gson.Gson;

public class GsonParser {

	public SkillRoot parse()  {

		Gson gson = new Gson();
		try(FileReader reader = new FileReader("Skills.json")) 
		{
			SkillRoot skill = gson.fromJson(reader, SkillRoot.class);
			return skill;
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		

		
		return null;
	}
	public static void main(String[] args) 
	{
		GsonParser parser = new GsonParser();
		SkillRoot skill = parser.parse();
	

		
		System.out.println(skill.toString());

	}

}

