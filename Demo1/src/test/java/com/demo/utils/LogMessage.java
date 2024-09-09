package com.demo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;

import io.cucumber.java.Scenario;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


public class LogMessage {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private WebDriver driver;
    private Scenario scenario;
    private static final Logger logger = Logger.getLogger(LogMessage.class);

    public LogMessage(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
    }

    public void takeScreenshot(Scenario scenario) {
        try {
            String relativePath = System.getProperty("user.dir") + FILE_SEPARATOR + "target" + FILE_SEPARATOR + "cucumber-html-reports"+FILE_SEPARATOR;

            // Take a full-page screenshot and save it to the specified path
            Shutterbug.shootPage(driver, Capture.FULL, true).save(relativePath);
            System.out.println("Screenshot saved to: " + relativePath);
            logger.info("Screenshot saved to: " + relativePath);
            
            refreshProject(relativePath);


            // Attach the saved screenshot file to the Cucumber report
            if (scenario != null) {
                scenario.attach(Files.readAllBytes(Paths.get(relativePath)), "image/png", scenario.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Exception occurred while capturing/embedding screenshot", e);
            if (scenario != null) {
                scenario.log("Exception occurred while capturing/embedding screenshot");
            }
        }
    }
    
    private void refreshProject(String directoryPath) {
        try {
            // Create a new File object for the directory
            File directory = new File(directoryPath);

            // Refresh the directory by listing files to ensure it's updated
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Just accessing file.exists() is enough to refresh the file system
                        file.exists(); 
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error refreshing directory: " + directoryPath, e);
        }
    }
    
    
    public void takeScreenshot1() throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String path = System.getProperty("user.dir") + FILE_SEPARATOR + "target" + FILE_SEPARATOR + "cucumber-html-reports" + FILE_SEPARATOR + timeStamp + ".png";
        
//        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File scrFile = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        
        try {
            FileUtils.copyFile(scrFile, new File(path));
            logger.info("Screenshot saved to: " + path);

            BufferedImage image = ImageIO.read(scrFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] res = baos.toByteArray();
            scenario.attach(res, "image/png", scenario.getName());
        } catch (IOException e) {
            logger.error("Exception occurred while capturing/embedding screenshot", e);
            scenario.log("Exception occurred while capturing/embedding screenshot");
        }
    }
    
    public void captureFullPageScreenshot() {
        // Create an AShot screenshot with a scrollable strategy
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String path = System.getProperty("user.dir") + FILE_SEPARATOR + "target" + FILE_SEPARATOR + "cucumber-html-reports" + FILE_SEPARATOR + timeStamp + ".png";
        Screenshot screenshot = new AShot()
            .shootingStrategy(ShootingStrategies.viewportPasting(1000))
            .takeScreenshot(driver);
        
        // Save the screenshot to a file
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File(path));
            System.out.println("Full-page screenshot saved at: " + path);
            scenario.attach(Files.readAllBytes(Paths.get(path)), "image/png", scenario.getName());
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  
    public void write(String result) throws Exception {
        logger.info(result);
        if (result.startsWith("Failed") || result.contains("Failed")) {
            scenario.log(result);
            logger.error("Failure details: " + driver.getCurrentUrl());
            takeScreenshot(scenario);
        } else if(result.startsWith("Passed") || result.contains("Passed")){
        	scenario.log(result);
//        	captureFullPageScreenshot();
//            takeScreenshot(scenario);
            takeScreenshot1();            
        }else {
            scenario.log(result);
        }
    }
}
