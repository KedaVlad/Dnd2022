package com.dnd;


public class Log {

	static int index = 0;
	public static void add(Object string)
	{
 
		System.out.println(index + " " + string);

		index++;
	}



}


