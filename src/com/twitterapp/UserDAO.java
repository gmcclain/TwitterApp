package com.twitterapp;

/**
 * DAO for User object
 *
 */
public interface UserDAO {

	public Integer getUserId();
	
	public String getUserName();
	
	public String getPassword();
	
	public void setUserId(Integer userid);
	
	public void setUserName(String userName);
	
	public void setPassword(String password);
}
