package com.dnd.dndTable.rolls;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Log;
import com.dnd.dndTable.rolls.Dice.Roll;


public class Formalizer 
{

	public static String formalize(String string)
	{
		String firstFix = string.replaceAll("\\s", "").toLowerCase();
		if(firstFix.matches("^-")||firstFix.matches("^\\+"))
		{
			
		}
		else
		{
			firstFix = "+" + firstFix;
		}
		Pattern pattern = Pattern.compile("(\\+|-)[^\\+-]+");
		Matcher matcher = pattern.matcher(firstFix);
		List<Dice> dices = new ArrayList<>();
		while (matcher.find()) 
		{
			if(buildDice(matcher.group()) == null)
			{
				return "There is a mistake in the dice roll formula, try again following the rules.";
			}
			else
			{
			dices.add(buildDice(matcher.group()));
			}
		}
		
		Formula formula = new Formula(dices);
	
		return formula.execute();
	}
	
	private static Roll buildRoll(String str)
	{
		switch(str)
		{
		case "d4":
			return Roll.D4;
		case "d6":
			return Roll.D6;
		case "d8":
			return Roll.D8;
		case "d10":
			return Roll.D10;
		case "d12":
			return Roll.D12;
		case "d20":
			return Roll.D20;
		case "d100":
			return Roll.D100;
			default:
				return null;
		}
	}
	
	private static Dice buildDice(String str)
	{
		Dice answer;
		Log.add("BuildDice ------------ " + str);
		if(str.matches("(\\+|-)(\\d+(d4|d6|d8|d10|d12|d20|d100))"))
		{
			int times = ((Integer) Integer.parseInt(str.replaceAll("(\\+|-)(\\d*)(d4|d6|d8|d10|d12|d20|d100)", "$2")));
			Roll roll = buildRoll(str.replaceAll("(\\+|-)(\\d*)(d4|d6|d8|d10|d12|d20|d100)", "$3"));
			
			Roll[] combo = new Roll[times];
			for(int i = 0; i < times; i++)
			{
				combo[i] = roll;
			}
			
			answer = new Dice(str, 0, combo);
			
			if(str.contains("-"))
			{
				answer.execute();
				return new Dice(str, answer.summ()*-1, Roll.NO_ROLL);
			}
			else
			{
				return answer;
			}
			
		}
		else if(str.matches("(\\+|-)(d4|d6|d8|d10|d12|d20|d100)"))
		{
			Roll roll = buildRoll(str.replaceAll("(\\+|-)(d4|d6|d8|d10|d12|d20|d100)", "$2"));
              answer = new Dice(str, 0, roll);
			
			if(str.contains("-"))
			{
				answer.execute();
				return new Dice(str, answer.summ()*-1, Roll.NO_ROLL);
			}
			else
			{
				return answer;
			}
		}
		else if(str.matches("(\\+|-)\\d+"))
		{
			int value = ((Integer) Integer.parseInt(str.replaceAll("(\\+|-)(\\d+)", "$2")));
			if(str.contains("-"))
			{
				return new Dice(str, value*-1, Roll.NO_ROLL);
			}
			else
			{
				return new Dice(str, value, Roll.NO_ROLL);
			}
		}
		else
		{
		return null;
		}
	}
	
}
