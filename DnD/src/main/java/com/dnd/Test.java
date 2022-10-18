package com.dnd;

import java.util.ArrayList;
import java.util.List;
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


String str = "adklaskd;a;dksad~ sdsdfs wefsd~ wrwvwefd~jjkkk kk";
Pattern ptr = Pattern.compile(cloudPattern);
		Matcher mm = ptr.matcher(str);
		List<String> l = new ArrayList<>();
		while(mm.find())
		{
			l.add(mm.group());
		}
		
		System.out.println(l);
	}
}



