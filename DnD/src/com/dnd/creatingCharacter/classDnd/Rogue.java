package com.dnd.creatingCharacter.classDnd;

public class Rogue extends ClassDnd{

	private int hits = 8;
	private int DiceHits = d8;
	private static final long serialVersionUID = 1L;
	
	private String[] allArchetypeClass = {"Thief","Assassin","Arcane Trickster"};
	private String myArchetypeClass;

	private String[][] lvlSkills = {
			
	/*LvL 1*/	{"Competence","Sneak Attack", "Thieves' Jargon"}, 
	/*LvL 2*/	{"Tricky Action"},
	/*LvL 3*/	{"Precise Aiming", null, null}, //archetype
	/*LvL 4*/	{}, //States
	/*LvL 5*/	{"Incredible Evasion"}, 
	/*LvL 6*/	{"Competence 2"},	
	/*LvL 7*/	{"Evasiveness"}, 
	/*LvL 8*/	{}, //States
	/*LvL 9*/	{null}, //archetype
	/*LvL 10*/	{}, //States
	/*LvL 11*/	{"Reliable Talent"},
	/*LvL 12*/	{}, //States
	/*LvL 13*/	{null}, //archetype
	/*LvL 14*/	{"Blind Sight"},
	/*LvL 15*/	{"Slippery Mind"}, 
	/*LvL 16*/	{}, //States
	/*LvL 17*/	{null}, //archetype
	/*LvL 18*/	{"Elusiveness 2"},
	/*LvL 19*/	{}, //States
	/*LvL 20*/	{"Luck"}
	
	};
	

	public Rogue(int subClass) {
		super(subClass);
		this.myArchetypeClass = allArchetypeClass[subClass];
		setSubClassSkills();
	
	}

	public void setSubClassSkills() {
		if(getMyArchetypeClass().equals("Thief")) {
			//LvL 3
			getLvlSkills()[2][1] = "Quick Hands";
		    getLvlSkills()[2][2] = "Window Clerk";
		    //LvL 9
		    getLvlSkills()[8][0] = "High Stealth";
		    //LvL 13
		    getLvlSkills()[12][0] = "Use of magic items";
		    //LvL 17
		    getLvlSkills()[16][0] = "Thief reflexes";
							
		} else if(getMyArchetypeClass().equals("Assassin")) {
			//LvL 3
			getLvlSkills()[2][1] = "Additional Holdings";
		    getLvlSkills()[2][2] = "Liquidation";
		    //LvL 9
		    getLvlSkills()[8][0] = "Penetration Master";
		    //LvL 13
		    getLvlSkills()[12][0] = "Impostor";
		    //LvL 17
		    getLvlSkills()[16][0] = "Death Blow";
							
			
		} else if(getMyArchetypeClass().equals("Arcane Trickster")){
			//LvL 3
			getLvlSkills()[2][1] = "Spell Use";
		    getLvlSkills()[2][2] = "Improved magic hand";
		    //LvL 9
		    getLvlSkills()[8][0] = "Magical ambush";
		    //LvL 13
		    getLvlSkills()[12][0] = "Multifaceted dodger";
		    //LvL 17
		    getLvlSkills()[16][0] = "Spell thief";
							
		} else {
			System.out.println("You act like a thief, the choice is clear");
			//LvL 3
			getLvlSkills()[2][1] = "Quick Hands";
		    getLvlSkills()[2][2] = "Window Clerk";
		    //LvL 9
		    getLvlSkills()[8][0] = "High Stealth";
		    //LvL 13
		    getLvlSkills()[12][0] = "Use of magic items";
		    //LvL 17
		    getLvlSkills()[16][0] = "Thief reflexes";
							
		}
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}

	public String[][] getLvlSkills() {
		return lvlSkills;
	}
	

}
