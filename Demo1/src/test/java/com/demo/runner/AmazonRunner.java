package com.demo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/Amazon/Product.feature", 
		glue = "com.demo.stepdefs",
	    plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/cucumber-reports/cucumber.json"}
//		dryRun = true
		)

public class AmazonRunner extends AbstractTestNGCucumberTests {

}
