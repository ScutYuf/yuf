package com.yuf.app.Entity;

import java.io.Serializable;
import java.lang.String;



public class UserInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static UserInfo userInfo;
	
	private float discount;
	private String nickname;
	private int userfollows;
	private int userfans;
	private int points;
	private int next_level_points;
	private String account;
	private String levelname;
	private String sessionid;
	
	public static UserInfo getUserInfo() {
		return userInfo;
	}
	public static void setUserInfo(UserInfo userInfo) {
		UserInfo.userInfo = userInfo;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getUserfollows() {
		return userfollows;
	}
	public void setUserfollows(int userfollows) {
		this.userfollows = userfollows;
	}
	public int getUserfans() {
		return userfans;
	}
	public void setUserfans(int userfans) {
		this.userfans = userfans;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getNext_level_points() {
		return next_level_points;
	}
	public void setNext_level_points(int next_level_points) {
		this.next_level_points = next_level_points;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	

}
