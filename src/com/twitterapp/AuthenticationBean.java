package com.twitterapp;

import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.twitterapp.models.User;

/**
 * Authentication Bean
 * 
 *
 */
@Component
@Scope("session")
public class AuthenticationBean {

	private String userName;
	private String password;

	@Autowired
	private NavigationHandler handler;

	private boolean loggedIn;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserImpl currentUser;

	@Transactional
	public String authenticate() {

		Session session = sessionFactory.getCurrentSession();
		Query checkUser = session.createQuery("from User u where u.userName ='"
				+ userName + "' and u.userPassword ='" + password + "'");

		if (checkUser.list() == null)
			return handler.toLoginFailedPage();
		if (checkUser.list().isEmpty()) {
			return handler.toLoginFailedPage();
		}

		// create new UserImpl with user's attributes
		User u = (User) checkUser.list().get(0);
		currentUser = new UserImpl();
		currentUser.setPassword(u.getUserPassword());
		currentUser.setUserId(u.getUserId());
		currentUser.setUserName(u.getUserName());

		
		
		setLoggedIn(true);
		return handler.toWallPage();
	}

	public String logout() {

		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		setLoggedIn(false);
		return handler.toHomePage();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public UserImpl getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserImpl currentUser) {
		this.currentUser = currentUser;
	}

}