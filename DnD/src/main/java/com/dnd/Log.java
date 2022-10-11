package com.dnd;


import java.util.ArrayList;
import java.util.List;


public class Log {

	static List<Object> log = new ArrayList<>();

	static int index = 0;

	public enum Place
	{
		DND, CHARACTER, CLASS, RACE, WORKMANSHIP, FACTORY, MEDIATOR, ITEM, BOT, CREATING, PLAY, COMMAND, CALLBACK, METHOD, GAMETABLE, TRASHCAN
	}



	public static void add(Object string, Place... system)
	{
		String logged = "";
		for(Place place: system)
		{
			logged += String.format("%-15s", place);
		}
      

		log.add(index + "  " + logged + "  " + string);
		System.out.printf("%d %-45s %-20s \n" , index, logged, string);

		index++;
	}


	public static void allLog()
	{
		for(int i = 0; i < log.size(); i++)
		{
			System.out.println(log.get(i));
		}
	}




}


