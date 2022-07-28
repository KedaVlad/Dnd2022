package com.dnd;

public interface Dise {
	public final static int d100 = (int) Math.round(Math.random()*99+1);
	public final static int d20 = (int) Math.round(Math.random()*19+1);
	public final static int d12 = (int) Math.round(Math.random()*11+1);
	public final static int d10 = (int) Math.round(Math.random()*9+1);
	public final static int d8 = (int) Math.round(Math.random()*7+1);
	public final static int d6 = (int) Math.round(Math.random()*5+1);
	public final static int d4 = (int) Math.round(Math.random()*3+1);
	
}
