package com.demo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;

public class LogMessage {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private WebDriver driver;
    private Scenario scenario;
    private static final Logger logger = Logger.getLogger(LogMessage.class);

    public LogMessage(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
    }

    public void takeScreenshot() throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String path = System.getProperty("user.dir") + FILE_SEPARATOR + "target" + FILE_SEPARATOR + "cucumber-html-reports" + FILE_SEPARATOR + timeStamp + ".png";
        
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

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

    public void write(String result) throws Exception {
        logger.info(result);
        if (result.startsWith("Failed") || result.contains("Failed")) {
            scenario.log(result);
            logger.error("Failure details: " + driver.getCurrentUrl());
            takeScreenshot();
        } else {
            scenario.log(result);
        }
    }
}
