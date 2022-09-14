package com.dnd;

import java.util.HashMap;
import java.util.Map;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.factory.CharacterFactory;

public class Test implements KeyWallet {
	
	private static String str;
	
	public static void main(String[] args) {

		setStr("sadasd");
		System.out.println(getStr());

		setStr("sadarwererwersd");
		System.out.println(getStr());
		setStr("sadas123123wqeqd");
		System.out.println(getStr());
	}

	public static String getStr() {
		return str;
	}

	public static void setStr(String str) {
		Test.str = str;
	}
	

}
