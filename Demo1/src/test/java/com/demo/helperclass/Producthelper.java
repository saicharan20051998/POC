package com.demo.helperclass;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.demo.utils.LogMessage;
import com.demo.commonhelper.Resuablemethods;
import com.demo.pageobjects.ProductPageobjects;

import io.cucumber.java.Scenario;

public class Producthelper {
	private Scenario scenario;
	public WebDriver driver;
	public Resuablemethods methods;
	LogMessage logs; 
	
	public Producthelper(WebDriver driver,Scenario scenario) {
		this.driver=driver;
        this.scenario = scenario;
		methods = new Resuablemethods(driver);
		logs = new LogMessage(driver, scenario);
		
	}
	
	
	public void navigate() throws Exception {
		driver.navigate().to("https://www.amazon.in/");
		logs.write("Passed: Navigated to url");
	}
	
	public void search(String name) throws Exception {
		WebElement search = methods.findElementbyid(ProductPageobjects.searchbox);
		search.sendKeys(name);
		search.sendKeys(Keys.ENTER);
	}
	String bookname;
	public void clickonbook() throws Exception {
		List<WebElement> books =  methods.findElements(ProductPageobjects.bookname);
		for (WebElement book : books) {
			bookname = book.getText();
			System.out.println(bookname);
			book.click();
			break;
		}
		methods.windowhandles();
		Addtocart();
	}
	String title;
	public void bookvalidation() throws Exception {
		WebElement booktitle = methods.findElement(ProductPageobjects.producttitle);
		title = booktitle.getText();
		System.out.println(title);
		org.testng.Assert.assertEquals(bookname, title);
	}
	
	public void Addtocart() throws Exception {
		WebElement search = methods.findElement(ProductPageobjects.addtocart);
		search.click();
	}
	
	public void addedtocart() throws Exception {
		try {
			WebElement search = methods.findElement(ProductPageobjects.addedtocart);
			if(search.isDisplayed()) {
				logs.write("Passed: Product is added to the cart");
			}
		}catch(Exception e) {
			e.printStackTrace();
			logs.write("Failed: Product is not added to the cart");
		}
		
	}
	
	public void login(String username,String password) throws Exception {
		driver.navigate().to("https://demo.applitools.com/");
		methods.findElementbyid(ProductPageobjects.username).sendKeys(username);
		methods.findElementbyid(ProductPageobjects.password).sendKeys(password);
		methods.findElementbyid(ProductPageobjects.login).click();
		logs.write("Passed: Clicked on signin");

	}
	
	
	private List<String> profit = new ArrayList<>();
    private List<String> loss = new ArrayList<>();
    private double totalSum = 0;
    private double totalLoss = 0;

    // Method to add and calculate total profit
    public void addProfit() throws Exception {
        List<WebElement> profitElements = methods.findElements(ProductPageobjects.profit);
        profit.clear();
        totalSum = 0;
        for (WebElement element : profitElements) {
            profit.add(element.getText());
        }
        System.out.println("Profit List: " + profit);
        for (String amount : profit) {
        	String cleanedAmount = amount.replace("USD", "").replaceAll(",", "").replace("+", "").trim();
            totalSum += parseAmount(cleanedAmount,true);
        }
        System.out.println("Total Profit Sum: $" + totalSum);
    }

    // Method to add and calculate total loss
    public void addLoss() throws Exception {
        List<WebElement> lossElements = methods.findElements(ProductPageobjects.loss);
        loss.clear(); 
        totalLoss = 0;
        for (WebElement element : lossElements) {
            loss.add(element.getText());
        }
        System.out.println("Loss List: " + loss);
        for (String amount : loss) {
        	String cleanedAmount = amount.replace("USD", "").replaceAll(",", "").replace("-", "").trim();

            totalLoss -= parseAmount(cleanedAmount, false); 
        }
        System.out.println("Total Loss Sum: $" + totalLoss);
    }

    private double parseAmount(String cleanedAmount, boolean isProfit) {
        try {
           
            // If it's a profit, ensure it starts with a "+"
            if (isProfit && !cleanedAmount.startsWith("+")) {
                cleanedAmount = "+" + cleanedAmount;
            }

            // If it's a loss, ensure it starts with a "-"
            if (!isProfit && !cleanedAmount.startsWith("-")) {
                cleanedAmount = "-" + cleanedAmount;
            }

            // Parse the cleaned amount string to double
            // Convert to double manually handling both cases
            return Double.parseDouble(cleanedAmount);
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount format: " + cleanedAmount);
            return 0;
        }
    }
    // Method to get the net total
    public double getNetTotal() throws Exception {
    	double totalprofit = totalSum - totalLoss;
		logs.write("Passed: Total Profit is :"+totalprofit);
        return totalprofit;
    }
}
