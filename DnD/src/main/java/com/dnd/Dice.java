package com.dnd;

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
			
			if(smaller < allDice[i])
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
	
}
