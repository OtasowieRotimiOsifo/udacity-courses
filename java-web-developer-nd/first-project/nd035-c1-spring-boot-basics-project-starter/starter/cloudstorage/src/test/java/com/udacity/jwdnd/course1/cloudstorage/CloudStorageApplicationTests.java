package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	Logger logger = LoggerFactory.getLogger(CloudStorageApplicationTests.class);
	
	@Autowired
	private NoteService notesService;
	
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
	
	@Test
	public void testAddNote() {
		// Register a user
		RegistrationManager.registerUserAndRedirectToLogin(driver, baseURL);
		Assertions.assertEquals("Login", driver.getTitle());

		// allow login for the registered user
		LoginManager.loginRegisteredUser(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		
		WebElement notesTab = getTabByName(driver,"nav-notes-tab");
		
		WebDriverWait driverWait = new WebDriverWait (driver, 20);
		
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", notesTab);
		driverWait.withTimeout(Duration.ofSeconds(20));
		
		
		By by = getBy("newnote");
		WebElement addNoteButton = driver.findElement(by);
		driverWait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
		
		by = getBy("note-title");
		String title = "first-test";
		driverWait.until(ExpectedConditions.elementToBeClickable(by)).sendKeys(title);
		
		String description = "a test that works";
		WebElement notedescription = getTabByName(driver, "note-description");
		notedescription.sendKeys(description);
		
		WebElement savechanges = getTabByName(driver, "save-changes");
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());
		
		Note note = notesService.getNotesByTitle(title);
		Assertions.assertNotNull(note);
		Assertions.assertEquals(title, note.getNotetitle());
		Assertions.assertEquals(description, note.getNotedescription());
		
		HomePageRedirect.redirect(driver, baseURL);
		// logout the user
		LogoutManager.logoutUser(driver);
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	private By getBy(String name) {
		return By.id(name);
	}
	
	private WebElement getTabByName(WebDriver driverIn, String name) {
		By by = By.id(name);
		return driverIn.findElement(by);
	}
	
	
}
