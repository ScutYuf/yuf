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
	private int levelpoints;
	private String useraccount;
	private String levelname;
	public  String sessionid;
	public  String userid;
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
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
	
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public int getLevelpoints() {
		return levelpoints;
	}
	public void setLevelpoints(int levelpoints) {
		this.levelpoints = levelpoints;
	}
	private boolean isLogin;
	
}
