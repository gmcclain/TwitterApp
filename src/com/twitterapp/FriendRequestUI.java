package com.twitterapp;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope("session")
public class FriendRequestUI {

	@Autowired
	private DatabaseBean dbBean;
	
	@Transactional
	public void friendRequestMessage() {
		addMessage("Request Accepted","You are now friends with this user!");
		sendRequest();
	}
	@Transactional
	public void sendRequest() {
		dbBean.setTestString(dbBean.getCurrentPendingFriend());
		dbBean.createFriendRequest();
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
