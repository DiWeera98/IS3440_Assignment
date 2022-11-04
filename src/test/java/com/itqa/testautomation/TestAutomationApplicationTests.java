package com.itqa.testautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest
class TestAutomationApplicationTests {

	public WebDriver driver;

	@BeforeAll
	public static void setDriver() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@AfterEach
	public void quit() {
		driver.quit();
	}

	/* For the assignment, I have chosen 123apps.com which is a web based tool for
	* editing, converting and creating video, audio and PDF files */

	public class OneTwoThreeApps{

		/* Test_01: To verify that I have navigated to the web application, I am testing the web page title*/
		@Test
		@Order(1)
		public void TestPageName(){
			// Navigating to 123apps.com website
			driver.get("https://123apps.com/");

			// Comparing the page headings
			String pageHeading = driver.getTitle();
			assertEquals("Web Apps by 123apps", pageHeading);
		}

		/* Test_02: The language of the website is converted to German. To verify the completion is done,
		* I'm testing whether the web page title is in German. */
		@Test
		@Order(2)
		public void CovertLang(){
			// Accessing the language modal
			WebElement langButton = driver.findElement(By.id("language-link"));
			langButton.click();

			// Selecting German as the language
			WebElement deutschButton = driver.findElement(By.className("de"));
			deutschButton.click();

			// Comparing the page heading
			String pageHeadingDe = driver.getTitle();
			assertEquals("Web-Apps von 123apps", pageHeadingDe);

			// Navigating back to the English home page
			driver.navigate().back();
		}

		/* Test_03: To check whether the pricing changes for monthly and yearly options,
		I'm verifying the change in price in each instance. */
		@Test
		@Order(3)
		public void PricingChange(){
			// Navigating to the pricing page
			WebElement pricingPage = driver.findElement(By.xpath("//a[@href='/pricing']"));
			pricingPage.click();

			// Verifying the navigation to pricing page by checking the Page title
			String pageHeadingPricing = driver.getTitle();
			assertEquals("Pricing", pageHeadingPricing);

			// Accessing the monthly price value
			WebElement priceMonthly = driver.findElement(By.xpath("//*[@id=\"pricing-table\"]/tbody/tr[1]/td[3]/div[2]/span[2]"));
			assertEquals("5", priceMonthly.getText());

			// Changing to monthly options
			WebElement yearlyButton = driver.findElement(By.className("annual active"));
			yearlyButton.click();

			// Accessing the yearly price value
			WebElement priceYearly = driver.findElement(By.xpath("//*[@id=\"pricing-table\"]/tbody/tr[1]/td[3]/div[2]/span[2]"));
			assertEquals("4", priceYearly.getText());

			// Navigating back to the home page
			driver.navigate().back();
		}

		/* Test_04: Verifying that invalid user names receive the correct error message*/
		@Test
		@Order(4)
		public void InvalidUserName(){
			// Opening the log in modal
			WebElement LogInButton = driver.findElement(By.id("sign-in"));
			LogInButton.click();

			// Entering incorrect string to the email input
			WebElement inputEmail = driver.findElement(By.name("username"));
			inputEmail.sendKeys("184152x");

			// Entering a password
			WebElement password = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/div[2]/input"));
			password.sendKeys("randompassword");

			// Clicking the submit button
			WebElement submitButton = driver.findElement(By.className("btn btn-primary btn-block"));
			submitButton.click();

			// Verifying the error message
			WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/div[1]/div"));
			assertEquals("Please enter a valid e-mail address", errorMessage.getText());
		}

		/* Test_05: Verifying that invalid password receive the correct error message*/
		@Test
		@Order(5)
		public void InvalidPassword(){
			// Entering string to the email input
			WebElement inputEmail = driver.findElement(By.name("username"));
			inputEmail.sendKeys("184152x@uom.lk");

			// Entering a password
			WebElement password = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/div[2]/input"));
			password.sendKeys("123");

			// Clicking the submit button
			WebElement submitButton = driver.findElement(By.className("btn btn-primary btn-block"));
			submitButton.click();

			// Verifying the error message
			WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/div[2]/div"));
			assertEquals("Password must be at least 6 characters long", errorMessage.getText());

			// Close the modal
			WebElement closeButton = driver.findElement(By.className("close"));
			closeButton.click();
		}

		/* Test_06: Verifying that the PDF to PNG function works*/
		@Test
		@Order(6)
		public void PDFtoPNG(){
			// Accessing the PDF to PNG function
			WebElement pdfToPng = driver.findElement(By.xpath("//*[@id=\"body\"]/div[2]/div[4]/div[3]/div[2]/a[11]"));
			pdfToPng.click();

			// Verifying the navigation to PDF to PNG function by checking the Page title
			String pageHeading = driver.getTitle();
			assertEquals("Convert PDF to PNG", pageHeading);

			// Clicking the upload button
			WebElement uploadButton = driver.findElement(By.id("upload_button"));

			// Upload the template pdf
			uploadButton.sendKeys("Template.pdf");

			// Select CovertPages option
			WebElement covertPages = driver.findElement(By.xpath("//*[@id=\"body\"]/div[2]/div/div/div[2]/div[5]/div[1]/div[1]"));

			// Verify completion confirmation message
			WebElement completionMessage = driver.findElement(By.xpath("//*[@id=\"body\"]/div[2]/div/div/div[2]/div[4]/div[2]"));
			assertEquals(" Done! ", completionMessage.getText());
		}
	}
}
