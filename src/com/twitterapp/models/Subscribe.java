package com.twitterapp.models;

// Generated Aug 18, 2014 11:29:33 AM by Hibernate Tools 3.4.0.CR1

/**
 * Subscribe generated by hbm2java
 */
public class Subscribe implements java.io.Serializable {

	private Integer subscribeId;
	private String userName;
	private String subscribedUserName;

	public Subscribe() {
	}

	public Subscribe(String userName, String subscribedUserName) {
		this.userName = userName;
		this.subscribedUserName = subscribedUserName;
	}

	public Integer getSubscribeId() {
		return this.subscribeId;
	}

	public void setSubscribeId(Integer subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubscribedUserName() {
		return this.subscribedUserName;
	}

	public void setSubscribedUserName(String subscribedUserName) {
		this.subscribedUserName = subscribedUserName;
	}

}
