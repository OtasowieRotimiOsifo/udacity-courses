package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	Logger logger = LoggerFactory.getLogger(CloudStorageApplicationTests.class);
	
	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;
	
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		
	    baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get( baseURL  + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void getSignupPage() {
		driver.get( baseURL  + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
	
	@Test
	public void getLogoutRedirect() {
		driver.get( baseURL  + "/login?logout");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void getLandingPage() {
		driver.get("http://localhost:" + this.port);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getUserSignup()  {
		
		RegistrationManager.registerUserAndRedirectToLogin(driver, baseURL);
		Assertions.assertEquals("Login", driver.getTitle()); 
	}
	
	@Test
	public void getSignupAndLogin() {
		
		//Register a user
		RegistrationManager.registerUserAndRedirectToLogin(driver, baseURL);
		Assertions.assertEquals("Login", driver.getTitle()); 
		
		//login for the registered user
		LoginManager.loginRegisteredUser(driver);
		Assertions.assertEquals("Home", driver.getTitle()); 
	}
	
	@Test
	public void getSignupAndLoginAndLogout() {
		
		// Register a user
		RegistrationManager.registerUserAndRedirectToLogin(driver, baseURL);
		Assertions.assertEquals("Login", driver.getTitle());

		// allow login for the registered user
		LoginManager.loginRegisteredUser(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		// logout the user
		LogoutManager.logoutUser(driver);
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void checkTabsOnHomePage() {
		
		// Register a user
		RegistrationManager.registerUserAndRedirectToLogin(driver, baseURL);
		Assertions.assertEquals("Login", driver.getTitle());

		// allow login for the registered user
		LoginManager.loginRegisteredUser(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		
		//get tab elements by name after login
		List<WebElement> tabs = new ArrayList<> ();
		String [] tabNames = {"nav-files-tab", "nav-notes-tab", "nav-credentials-tab"};
		for(String name: tabNames) {
			 WebElement tab = getTabByName(driver, name);
			 tabs.add(tab);
		}
		Assertions.assertEquals(3, tabs.size());
		
		// logout the user
		LogoutManager.logoutUser(driver);
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	private WebElement getTabByName(WebDriver driverIn, String name) {
		return driverIn.findElement(By.id(name));
	}
	
	
}
