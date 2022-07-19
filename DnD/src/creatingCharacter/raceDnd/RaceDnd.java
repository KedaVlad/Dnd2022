package creatingCharacter.raceDnd;

import java.io.Serializable;

abstract public class RaceDnd implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	private int age;
	private int weight;
	private int growth;
	protected int speed = 30;
	
	RaceDnd(int age, int weight, int growth) {
		this.age = age;
		this.weight = weight;
		this.growth = growth;
		
	}
	public int getStr(int str) {
		return str;
	}
	
	public int getAgl(int agl) {
		return agl;
	}
	public int getDex(int dex) {
		return dex;
	}
	public int getIntl(int intl) {
		return intl;
	}
	public int getWis(int wis) {
		return wis;
	}
	public int getCha(int cha) {
		return cha;
	}
	public int getAge() {
		return age;
	}
	public int getWeight() {
		return weight;
	}
	public int getGrowth() {
		return growth;
	}
	public int getSpeed() {
		return speed;
	}
	public boolean getDarkVision() {
		return false;
	}

}

