package com.dnd;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class Dice {

	public static int d4()
	{
		return (int) Math.round(Math.random()*3+1);
	}

	public static int d6()
	{
		return (int) Math.round(Math.random()*5+1);
	}

	public static int d8()
	{
		return (int) Math.round(Math.random()*7+1);
	}

	public static int d10()
	{
		return (int) Math.round(Math.random()*9+1);
	}

	public static int d12()
	{
		return (int) Math.round(Math.random()*11+1);
	}

	public static int d20()
	{
		return (int) Math.round(Math.random()*19+1);
	}

	public static int d100()
	{
		return (int) Math.round(Math.random()*99+1);
	}

	public static int randomStat()
	{

		int[] allDice = {d6(), d6(), d6(), d6()};

		int answer = 0;
		int smaller = 0;

		for(int i = 0; i < allDice.length; i++)
		{

			if(i == 0)
			{
				smaller = allDice[i];

			}
			else if(smaller < allDice[i])
			{
				answer += allDice[i];
			}
			else 
			{
				answer += smaller;
				smaller = allDice[i];
			}


		}

		return answer;

	}

	public static int stableHp(CharacterDnd characterDnd)
	{ 


		int start = characterDnd.getClassDnd().getDiceHits();
		int con = (characterDnd.getMyStat().getValue(0, 2) - 10)/2;
		switch(start)
		{
		case 6:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 4 + con;
			}
			break;
		case 8:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 5 + con;
			}
			break;
		case 10:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 6 + con;
			}
			break;
		case 12:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 7 + con;
			}
			break;
		}
		return start;
	}

	public static int randomHp(CharacterDnd characterDnd)
	{

		int start = characterDnd.getClassDnd().getDiceHits();
		int con = (characterDnd.getMyStat().getValue(0, 2) - 10)/2;
		switch(start)
		{
		case 6:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d6() + con;
			}
			break;
		case 8:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d8() + con;
			}
			break;
		case 10:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d10() + con;
			}
			break;
		case 12:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d12() + con;
			}
			break;

		}

		return start;
	}



}
