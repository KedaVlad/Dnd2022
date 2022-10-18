package com.dnd;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test implements KeyWallet, Source {

	enum Some{
		
		SOME("Some"), BAM("Bam");

		String title;
		Some(String string) {
			title = string;
		}
		public String toString()
		{
			return title;
		}
	}

	public static void main(String[] args) throws Exception {

		String some = "Some";
		
		System.out.println(Some.SOME);
		
	}
}



