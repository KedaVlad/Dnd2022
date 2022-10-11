package com.dnd;


public class Test implements KeyWallet, Source {

	
	public static String getStr(String str)
	{
		
		int i = 0;
		switch(i)
		{
		case 0:
			str += "Kakaha";
			break;
		}
		str += " Dura";
		return str;
	}
	
	public static void main(String[] args) throws Exception {


		System.out.println(Test.getStr("Biba "));
		
		
	}
	}

	

