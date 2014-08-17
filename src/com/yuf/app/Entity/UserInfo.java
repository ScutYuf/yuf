package com.yuf.app.Entity;

public class UserInfo {

	private static UserInfo instance;
	public static UserInfo getInstance() {
		if (instance==null) {
			instance=new UserInfo();
			return instance;
		}
		else {
			return instance;
		}
		
		
	}
	private double leveldiscout;
	private String username;
	private int userfollows;
	private int userfans;
	private int userpoints;
	private String useraccount;
	private String levelname;
	public double getLeveldiscout() {
		return leveldiscout;
	}
	public void setLeveldiscout(double leveldiscout) {
		this.leveldiscout = leveldiscout;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public int getUserpoints() {
		return userpoints;
	}
	public void setUserpoints(int userpoints) {
		this.userpoints = userpoints;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	
	
	
}
