package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
		
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(UserData.firstname, UserData.lastname, UserData.username, UserData.password);
		
		WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Login')]"));
    	loginLink.click(); //redirection after signup
    	
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Assertions.assertEquals("Login", driver.getTitle()); 
	}
	
	@Test
	public void getSignupAndLogin() {
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(UserData.firstname, UserData.lastname, UserData.username, UserData.password);
		
		WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Login')]"));
    	loginLink.click(); //redirection after signup
    	
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Assertions.assertEquals("Login", driver.getTitle()); 
		
		//login from the login page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(UserData.username, UserData.password);
		
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		logger.info("currentUrl: {}", currentUrl);
		Assertions.assertEquals("Home", driver.getTitle()); 
	}
	
	@Test
	public void getSignupAndLoginAndLogout() {
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(UserData.firstname, UserData.lastname, UserData.username, UserData.password);
		
		WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Login')]"));
    	loginLink.click(); //redirection after signup
    	
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Assertions.assertEquals("Login", driver.getTitle()); 
		
		//login from the login page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(UserData.username, UserData.password);
		
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		logger.info("currentUrl: {}", currentUrl);
		Assertions.assertEquals("Home", driver.getTitle()); 
		
		//logout
		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.logout();
		
		currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		logger.info("currentUrl: {}", currentUrl);
		Assertions.assertEquals("Login", driver.getTitle()); 
	}
}
