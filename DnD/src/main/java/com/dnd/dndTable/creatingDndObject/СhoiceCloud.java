package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class СhoiceCloud implements Serializable{

	private static final long serialVersionUID = -9049967026775985904L;

	private List<Performan> cloud = new ArrayList<>();

	public Object complate(int answer)
	{	
			return cloud.get(cloud.size()-1).compleate(answer);
	}
	
	
	public List<String> prepare()
	{
		if(chek())
		{
			List<String> pool = new ArrayList<>();
		
			pool.add(cloud.get(cloud.size() - 1).key);
			pool.add(cloud.get(cloud.size() - 1).terms);
			for(Object object: cloud.get(cloud.size() - 1).performans)
			{
				pool.add(object.toString());
			}
			
			return pool;
		}
		
		return null;
	}


	private boolean chek()
	{
		if(cloud.size() != 0)
		{
			if(cloud.get(cloud.size() - 1).lifeCicle == 0)
			{
				cloud.remove(cloud.size() - 1);
			}
			if(cloud.size() != 0)
			{
				return true;
			}
		}
		return false;
	}





	public List<Performan> getCloud() 
	{
		return cloud;
	}

	public void setPerforman(String key, int lifeCicle, String terms, List<Object> performans) 
	{
		cloud.add(new Performan(key, lifeCicle, terms, performans));
	}


	class Performan implements Serializable{

		private String key;
		private int lifeCicle;
		private String terms;
		private static final long serialVersionUID = 2145986472020080908L;

		private List<Object> performans = new ArrayList<>();

		Performan(String key, int lifeCicle, String terms, List<Object> performans)
		{
			this.key = key;
			this.lifeCicle = lifeCicle;
			this.terms = terms;
			this.performans = performans;
		}
		Performan()
		{
			
		}
		
		private Object compleate(int number)
		{
			Object answer = performans.get(number);
			performans.remove(number);
			lifeCicle--;

			return answer;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		public String getTerms() {
			return terms;
		}
		public void setTerms(String terms) {
			this.terms = terms;
		}


	}

}

