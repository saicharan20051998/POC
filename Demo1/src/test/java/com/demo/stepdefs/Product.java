package com.demo.stepdefs;

import org.openqa.selenium.WebDriver;

import com.demo.commonhelper.Resuablemethods;
import com.demo.helperclass.Producthelper;
import com.demo.utils.LogMessage;
import com.demo.utils.Maindriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Scenario;

public class Product {
    
    private WebDriver driver;
    private Maindriver maindriver = new Maindriver();
    private static final String BROWSER = "edge";
    private Producthelper helper;
    private Resuablemethods methods;
    private Scenario scenario;
    LogMessage logs; 

    @Before
    public void setUp(Scenario scenario) {
        driver = maindriver.getDriver(BROWSER);
        helper = new Producthelper(driver, scenario);
        methods = new Resuablemethods(driver);
        logs = new LogMessage(driver, scenario);
        this.scenario = scenario;
    }
    
    @Given("the user is logged in")
    public void the_user_is_logged_in() throws Exception {
        helper.navigate();
    }

    @Given("goes to landing page")
    public void goes_to_landing_page() throws Exception {
        logs.write(driver.getTitle());
    }

    @Given("User search for the product {string}")
    public void user_search_for_the_product(String name) throws Exception {
        helper.search(name);
    }

    @When("User click on add")
    public void user_click_on_add() throws Exception {
        helper.clickonbook();
    }

    @Then("User validate the product is added to cart or not")
    public void user_validate_the_product_is_added_to_cart_or_not() throws Exception {
        helper.addedtocart();
    }

    @Given("the user navigates to the page with username {string} and password {string}")
    public void the_user_navigates_to_the_page_with_username_and_password(String username, String password) throws Exception {
        helper.login(username, password);
    }

    @Then("the user verifies the page title")
    public void the_user_verifies_the_page_title() {
        System.out.println(driver.getTitle());
    }

    @Then("the user calculates the total profit")
    public void the_user_calculates_the_total_profit() throws Exception {
        helper.addProfit();
    }

    @Then("the user calculates the total loss")
    public void the_user_calculates_the_total_loss() throws Exception {
        helper.addLoss();
    }

    @Then("the user sums the total profit and total loss")
    public void the_user_sums_the_total_profit_and_total_loss() throws Exception {
        helper.getNetTotal();
    }

    @After
    public void tearDown() {
        maindriver.closeDriver();
    }
}
