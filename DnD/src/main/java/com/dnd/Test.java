package com.dnd;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test implements KeyWallet, Source {

	
	
	public void someMethod(Map<String, Integer> map)
	{
		map.put("Dura", 75);
	}
	
	public static void main(String[] args) throws Exception {

Test test = new Test();
Test2 test2 = new Test2();

test2.getSomeMap().put("Daun", 122);

		test.someMethod(test2.getSomeMap());
		 
		
		System.out.println(test2.getSomeMap());
	}
	}

	

