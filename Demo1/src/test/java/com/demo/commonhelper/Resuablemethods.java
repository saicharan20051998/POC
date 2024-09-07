package com.demo.commonhelper;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Resuablemethods {
	
	public WebDriver driver;
	WebDriverWait wait;
	
	public Resuablemethods(WebDriver driver) {
		this.driver = driver;
		wait=new WebDriverWait(driver,Duration.ofSeconds(90));
	}
	
	public void wait(int wait) {
		try {
			Thread.sleep(wait);
		}
		catch(InterruptedException ie) {
		}
	}
	
	public void click(String xpath) throws InterruptedException{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		Thread.sleep(700);
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public List<WebElement> findElements(String xpath) throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		wait(800);
		List<WebElement> element = driver.findElements(By.xpath(xpath));
		return element;
	}
	
	public WebElement findElement(String xpath) throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		wait(800);
	    WebElement element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	public WebElement findElementbyid(String id) throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		wait(800);
	    WebElement element = driver.findElement(By.id(id));
		return element;
	}
	
	public void windowhandles() {
		String parent = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String win:windows) {
			if(!win.equals(parent)) {
				driver.switchTo().window(win);
				break;
			}
		}
	}
	
	
}
