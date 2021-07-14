package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;

public class HomePageRedirect {
	public static void redirect(WebDriver driver, String baseUrl) {
		String homeUrl = baseUrl + "/home";
		driver.get(homeUrl);
	}
}
