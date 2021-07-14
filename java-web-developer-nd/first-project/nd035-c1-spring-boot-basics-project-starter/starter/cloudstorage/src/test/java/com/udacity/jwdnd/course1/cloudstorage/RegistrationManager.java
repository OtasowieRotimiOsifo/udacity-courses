package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationManager {

	public static void registerUserAndRedirectToLogin(WebDriver driver, String baseURL) {
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(UserData.firstname, UserData.lastname, UserData.username, UserData.password);
		
		WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Login')]"));
    	loginLink.click(); //redirection after signup
    	
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
	}
}
