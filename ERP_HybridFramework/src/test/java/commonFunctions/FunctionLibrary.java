package commonFunctions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {

	public static Properties conpro;
	public static WebDriver driver;
	

	public static WebDriver startBrowser() throws Throwable {

		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertiesFile/Environment.properties"));

		if (conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize(); 
			
		} else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if (conpro.getProperty("Browser").equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		} else {
			Reporter.log("browser is not matching ", true);

		}
		return driver;

	}

	public static void openUrl() {

		driver.get(conpro.getProperty("url"));

	}

	public static void waitForElement(String LocatersType, String LocaterValue, String TestData)

	{

		WebDriverWait MyWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if (LocatersType.equalsIgnoreCase("xpath")) {
			MyWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocaterValue)));

		} else if (LocatersType.equalsIgnoreCase("name")) {
			MyWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocaterValue)));
		} else if (LocatersType.equalsIgnoreCase("id")) {
			MyWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocaterValue)));

		}

	}

	public static void clickAction(String LocatersType, String LocaterValue)

	{
		if (LocatersType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocaterValue)).click();
		} else if (LocatersType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocaterValue)).click();

		} else if (LocatersType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocaterValue)).sendKeys(Keys.ENTER);
		}

	}

	public static void typeAction(String LocatersType, String LocaterValue, String TestData) {

		if (LocatersType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocaterValue)).clear();
			driver.findElement(By.name(LocaterValue)).sendKeys(TestData);
		}
		if (LocatersType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocaterValue)).clear();
			driver.findElement(By.xpath(LocaterValue)).sendKeys(TestData);

		}
		if (LocatersType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocaterValue)).clear();
			driver.findElement(By.id(LocaterValue)).sendKeys(TestData);

		}

	}

	public static void validateTitle(String Actual_Title) {

		String Expected_Title = driver.getTitle();

		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Title not Mtched");

		} catch (AssertionError e) {

			System.out.println(e.getMessage());
		}
	}

	public static void closeBrowser() {
		driver.quit();

	}

	
	
}
