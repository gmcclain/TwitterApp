package com.twitterapp;

/**
 * DAO interface for Tweet object
 *
 */
public interface TweetDAO {
	
	public boolean setUser(UserImpl user);
	
	public UserImpl getUserImpl();

	public void setMessageBody(String messageBody);

	public String getMessageBody();
}
