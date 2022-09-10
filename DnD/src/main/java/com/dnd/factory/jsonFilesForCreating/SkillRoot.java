package com.dnd.factory.jsonFilesForCreating;

import java.util.Map;

import com.dnd.creatingCharacter.skills.Skill;

public class SkillRoot {
private String name;
private Map<String, Skill> skills;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Map<String, Skill> getSkills() {
	return skills;
}
public void setSkills(Map<String,Skill> skills) {
	this.skills = skills;
}
public String toString() {
	return "Root - name("+name+") Skills:" + skills;
}
}
