import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserTest {

	private static boolean online() {
		 try { 
	            URL url = new URL("https://www.google.com/"); 
	            URLConnection connection = url.openConnection(); 
	            connection.connect(); 
	            return true;
	        } 
	        catch (Exception e) { 
	            return false;
	        } 
	}
	
	public static void main(String[] args) throws Exception{
		
		/*
		 * Before even starting the program, it would be a nice idea to check if the file already exists...
		 * This will save a ton of processing power
		 * I'll be manipulating some files for this...
		 * So refer to:	https://stackabuse.com/java-list-files-in-a-directory/ 
		 */
		
		LocalDate localDate=LocalDate.now();
		String date=localDate.toString();
		
		String UserName=System.getProperty("user.name");
		
		String[] pathnames=new File("C:/Users/"+UserName+"/Pictures").list();
		//Checking if file exists...
		for(String pathname : pathnames) {
			if(pathname.equals(date+".jpg")) {
				System.out.println("File to be downloaded already exists..."+"\n"+"Quitting...");
				Thread.sleep(2000);
				System.exit(0);
			}
		}
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
		boolean shown=false;
		while(true) {
			if(online()) {
				System.out.println("Device is online..."+"\n"+"Starting...");
				break;
			}else {
				if(!shown) {
				System.out.println("Device is offline..."+"\n"+"Retrying...");
				}
				shown=true;
			}
		}
		
		driver.manage().window().maximize();
		driver.get("https://www.bing.com/");
		Thread.sleep(10000);	//this waits for the page to load properly, else the required elements fail to load
		
		System.out.println("Browser window title is: " + driver.getTitle());
		WebElement element = driver.findElement(By.xpath("//a[@id='hideShowCaro']"));
		element.click();
		element.sendKeys(Keys.PAGE_DOWN);
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.DOWN);
		
		System.out.println("Element found!!");
		WebElement element1 = driver.findElement(By.xpath("//li[@class='item download']//a"));
		System.out.println("Download Link found...");
		System.out.println(element1.getAttribute("href"));
		System.out.println("Current date : "+date);
		URL url=new URL(element1.getAttribute("href"));
		System.out.println("Download initialized...");
		File file=new File("C:/Users/"+UserName+"/Pictures/"+date+".jpg");
		FileUtils.copyURLToFile(url, file);
		System.out.println("Task Completed Successfully!"+"\n"+"Quitting Browser Session...");
		Thread.sleep(2000);
		driver.close();
		Thread.sleep(3000);
		driver.quit();
	}
}
