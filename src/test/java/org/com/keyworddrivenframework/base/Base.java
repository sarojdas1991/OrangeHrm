package org.com.keyworddrivenframework.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Base {
WebDriver driver;
//= SingletonChromeDriver.getInstance().driver;
    public Properties prop;
    public WebElement element;
    public static String UserDir = System.getProperty("user.dir");
    public static String PathSep = System.getProperty("file.separator");

    public WebDriver initializeDriver() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();

        WebDriverManager.edgedriver().setup();
        WebDriver edgeDriver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }
    public void setupDriver(String browserName){
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "D:" + PathSep + "Drivers" + PathSep+ "Chrome" + PathSep + "chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            } else if (browserName.equalsIgnoreCase("FF")) {
                System.setProperty("webdriver.gecko.driver", "D:" + PathSep + "Drivers" + PathSep+ "FF"+ PathSep + "geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String initializeProperty(String data) {
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework" + PathSep + "properties" + PathSep + "Data.properties";

        prop = new Properties();
        try {
            FileReader reader = new FileReader(filePath);
            prop.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return prop.getProperty(data);
    }
    public String initializePropertyFromFile(String Name,String data) {
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework" + PathSep + "properties" + PathSep ;
        prop = new Properties();
        try {
            FileReader reader = new FileReader(filePath+Name+".Properties");
            prop.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return prop.getProperty(data);
    }

    public void lunchUrl(String url) {
        driver.get(url);
    }

    public void quit() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
    public void close() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.close();
    }

    public void enterText(String locatorType,String locator,String key){
        waitForElementIntractable(locatorType,locator);
        switch (locatorType) {
            case "xpath" -> {
                element = driver.findElement(By.xpath(locator));
                element.clear();
                element.sendKeys(key);
            }
            case "id" -> {
                element = driver.findElement(By.id(locator));
                element.clear();
                element.sendKeys(key);
            }
            case "name" -> {
                element = driver.findElement(By.name(locator));
                element.clear();
                element.sendKeys(key);
            }
            case "className" -> {
                element = driver.findElement(By.className(locator));
                element.clear();
                element.sendKeys(key);
            }
            case "css" -> {
                element = driver.findElement(By.cssSelector(locator));
                element.clear();
                element.sendKeys(key);
            }
        }

    }
    public void click(String locatorType,String locator){
        waitForElementIntractable(locatorType,locator);
        switch (locatorType) {
            case "xpath" -> {
                element = driver.findElement(By.xpath(locator));
                element.click();
            }
            case "id" -> {
                element = driver.findElement(By.id(locator));
                element.click();
            }
            case "name" -> {
                element = driver.findElement(By.name(locator));
                element.click();
            }
            case "className" -> {
                element = driver.findElement(By.className(locator));
                element.click();
            }
            case "css" -> {
                element = driver.findElement(By.cssSelector(locator));
                element.click();
            }
        }

    }
    public  void waitForElementIntractable(String locatorType,String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            switch (locatorType) {
                case "xpath" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                case "id" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                case "name" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locator)));
                case "className" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locator)));
                case "css" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        } catch (ElementNotInteractableException e) {
            System.out.println("Element is not intractable");
        } catch (TimeoutException e) {
            System.out.println("Time out Exception");
        }
    }
    public void selectDropDownByIndex(String locator,int index){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        element=driver.findElement(By.xpath(locator));
        Select select=new Select(element);
            select.selectByIndex(index);

    }
    public void selectDropDownByValue(String locator,String value){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        element=driver.findElement(By.xpath(locator));
        Select select=new Select(element);
        select.selectByValue(value);

    }
    public void selectDropDownByVisibleText(String locator,String Text){
        element=driver.findElement(By.xpath(locator));
        Select select=new Select(element);
        select.selectByVisibleText(Text);

    }
    public void validateText(String locatorType,String locator,String Text){
        switch (locatorType) {
            case "xpath" -> {
                element = driver.findElement(By.xpath(locator));
                String textValue = element.getText();
                Assert.assertEquals(textValue, Text);
            }
            case "id" -> {
                element = driver.findElement(By.id(locator));
                String textValue = element.getText();
                Assert.assertEquals(textValue, Text);

            }
            case "name" -> {
                element = driver.findElement(By.name(locator));
                String textValue = element.getText();
                Assert.assertEquals(textValue, Text);

            }
            case "className" -> {
                element = driver.findElement(By.className(locator));
                String textValue = element.getText();
                Assert.assertEquals(textValue, Text);
            }
            case "css" -> {
                element = driver.findElement(By.cssSelector(locator));
                String textValue = element.getText();
                Assert.assertEquals(textValue, Text);
            }
        }

    }
    public boolean isDisplayed(String locatorType,String locator) {
        boolean flag=false;
        try {
            switch (locatorType) {
                case "xpath" -> {
                    element = driver.findElement(By.xpath(locator));
                    flag = element.isDisplayed();
                    return flag;
                }
                case "id" -> {
                    element = driver.findElement(By.id(locator));
                    flag = element.isDisplayed();
                    return flag;
                }
                case "name" -> {
                    element = driver.findElement(By.name(locator));
                    flag = element.isDisplayed();
                    return flag;
                }
                case "className" -> {
                    element = driver.findElement(By.className(locator));
                    flag = element.isDisplayed();
                    return flag;
                }
                case "css" -> {
                    element = driver.findElement(By.cssSelector(locator));
                    flag = element.isDisplayed();
                    return flag;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element is not present, hence not displayed as well");
        }

        return false;
    }
    public void isEnabled(String locator){
        element= driver.findElement(By.xpath(locator));
        boolean flag=element.isEnabled();
        Assert.assertTrue(flag);
    }
    public boolean isSelected(String locatorType,String locator){
        boolean flag=false;
        try {
            switch (locatorType) {
                case "xpath" -> {
                    element = driver.findElement(By.xpath(locator));
                    flag = element.isSelected();
                    return flag;
                }
                case "id" -> {
                    element = driver.findElement(By.id(locator));
                    flag = element.isSelected();
                    return flag;
                }
                case "name" -> {
                    element = driver.findElement(By.name(locator));
                    flag = element.isSelected();
                    return flag;
                }
                case "className" -> {
                    element = driver.findElement(By.className(locator));
                    flag = element.isSelected();
                    return flag;
                }
                case "css" -> {
                    element = driver.findElement(By.cssSelector(locator));
                    flag = element.isSelected();
                    return flag;
                }
            }
        }
        catch (NoSuchElementException e){
            System.out.println("Element is not present, hence not displayed as well");
        }
        return false;
    }

    public void alert(String status){
        try{
        Alert alert=driver.switchTo().alert();
        if(status.equals("accept")){
            alert.accept();
        }else if(status.equalsIgnoreCase("reject")){
            alert.dismiss();
        }else if(status.equalsIgnoreCase("getText")){
            alert.getText();
        }}
        catch (NoAlertPresentException e){
            System.out.println("No Alert Present");
        }

    }
    public  List<String> getListData(String locator) {
        List<String> listName = new ArrayList<>();

            List<WebElement> links = driver.findElements(By.xpath(locator));
        System.out.println(links.size());

        for (WebElement ele : links) {
            String title = ele.getText();
            System.out.println(title);
            listName.add(title);
        }
            System.out.println("Size of List listName = "+listName.size());


        return listName;

    }
    public void verifyListDataAndDelete(String locator,String input){
        List<String> DataList =getListData(locator);
        for (String value : DataList) {
            System.out.println("Validating Data of List");
            if (value.equalsIgnoreCase(input)) {
                System.out.println("Data already exit in list");
                String locator1 = "//div[text()='" + value + "']//preceding::input[1]";
                element = driver.findElement(By.xpath(locator1));
                element.click();
                element = driver.findElement(By.xpath("//button[text()=' Delete Selected ']"));
                element.click();
            }
        }


    }
    public void verifyListData(String locator,String input){
        List<String> DataList =getListData(locator);
        for (String value : DataList) {
            if (value.equalsIgnoreCase(input)) {
                System.out.println("Data already exit in list");
                Assert.assertEquals(value, input);
            }
        }


    }
    public void clickCheckBox(String locatorType,String locator){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        switch (locatorType) {
            case "xpath" -> {
                element = driver.findElement(By.xpath(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
            case "id" -> {
                element = driver.findElement(By.id(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
            case "name" -> {
                element = driver.findElement(By.name(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
            case "className" -> {
                element = driver.findElement(By.className(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
            case "css" -> {
                element = driver.findElement(By.cssSelector(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }
        }


    }

    /*Method Description: This method is used to take the screenshot and save in
                         specified path in .png file.
       Returns: null
     */
    public void captureScreenshot(String screenshotName) {
        String UserDir = System.getProperty("user.dir");
        String PathSep = System.getProperty("file.separator");
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework" + PathSep + "screenshot"+PathSep;

        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath + screenshotName + ".png"));
            System.out.println("Successfully captured a screenshot");
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());

        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }

    }
    public Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
    public void configLog(){
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }
    public void refreshBrowser(){
        driver.navigate().refresh();
    }
}

