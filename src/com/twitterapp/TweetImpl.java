package com.twitterapp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of the TweetDAO
 *
 */
@Component
@Scope("session")
public class TweetImpl implements TweetDAO {

	private String messageBody;
	private UserImpl user;
	
	@Override
	public void setMessageBody(String message) {
		this.messageBody = message;
	}

	@Override
	public boolean setUser(UserImpl user) {
		this.user = user;
		return true;
	}

	@Override
	public String getMessageBody() {
		return messageBody;
	}

	@Override
	public UserImpl getUserImpl() {
		return user;
	}

}
