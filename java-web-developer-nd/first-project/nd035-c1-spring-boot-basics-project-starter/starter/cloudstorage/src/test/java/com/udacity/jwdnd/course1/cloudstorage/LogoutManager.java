package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;

public class LogoutManager {
	public static void logoutUser(WebDriver driver) {
		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.logout();

		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
	}
}
