package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;

public class LoginManager {
	public static void loginRegisteredUser(WebDriver driver) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(UserData.username, UserData.password);
		
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
	}
}
