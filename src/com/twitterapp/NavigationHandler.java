package com.twitterapp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CommandHandler class which handles the user commands.
 * 
 */
@Component
@Scope("session")
public class NavigationHandler {

	/**
	 * Returns a String to navigate to login page.
	 * 
	 * @return String to direct to the login page.
	 */
	public String toLoginPage() {
		return "loginPage";
	}

	/**
	 * Returns a String to navigate to registration page.
	 * 
	 * @return String to direct to the registration page.
	 */
	public String toRegistrationPage() {
		return "registrationPage";
	}

	/**
	 * Returns a String to navigate to home page.
	 * 
	 * @return String to direct to the home page.
	 */
	public String toHomePage() {
		return "homePage";
	}

	/**
	 * Returns a String to navigate to tweet page.
	 * 
	 * @return String to direct to the tweet page.
	 */
	public String toTweetPage() {
		return "wallPage";
	}

	/**
	 * Returns a String to navigate to login failed page.
	 * 
	 * @return String to direct to the login failed page.
	 */
	public String toLoginFailedPage() {
		return "loginFailedPage";
	}

	/**
	 * Returns a String to navigate to subscriptions page.
	 * 
	 * @return String to direct to the subscriptions page.
	 */
	public String toSubscriptionsPage() {
		return "subscriptionsPage";
	}

	/**
	 * Returns a String to navigate to wall page.
	 * 
	 * @return String to direct to the wall page.
	 */
	public String toWallPage() {
		return "wallPage";
	}

	/**
	 * Returns a String to navigate to foreign wall page.
	 * 
	 * @return String to direct to the foreign wall page.
	 */
	public String toForeignWallPage() {
		return "foreignWallPage";
	}
}
