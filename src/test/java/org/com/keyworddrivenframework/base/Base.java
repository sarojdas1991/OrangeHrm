package org.com.keyworddrivenframework.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.com.keyworddrivenframework.executer.ExtentReportOrangeHrm;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
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
        System.out.println(driver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(locatorType.equals("xpath"))
        {
        element=driver.findElement(By.xpath(locator));
        element.clear();
        element.sendKeys(key);
        }else if(locatorType.equals("id"))
        {
         element=driver.findElement(By.id(locator)) ;
         element.clear();
         element.sendKeys(key);
        }
        else if(locatorType.equals("name"))
        {
            element=driver.findElement(By.name(locator)) ;
            element.clear();
            element.sendKeys(key);
        }else if(locatorType.equals("className"))
        {
            element=driver.findElement(By.className(locator)) ;
            element.clear();
            element.sendKeys(key);
        }else if(locatorType.equals("css"))
        {
            element=driver.findElement(By.cssSelector(locator)) ;
            element.clear();
            element.sendKeys(key);
        }

    }
    public void click(String locatorType,String locator){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(locatorType.equals("xpath"))
        {
            element=driver.findElement(By.xpath(locator));
            element.click();
        }else if(locatorType.equals("id"))
        {
            element=driver.findElement(By.id(locator)) ;
            element.click();
        }
        else if(locatorType.equals("name"))
        {
            element=driver.findElement(By.name(locator)) ;
            element.click();
        }else if(locatorType.equals("className"))
        {
            element=driver.findElement(By.className(locator)) ;
            element.click();
        }else if(locatorType.equals("css"))
        {
            element=driver.findElement(By.cssSelector(locator)) ;
            element.click();
        }

    }
    public  void waitForElementIntractable(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        element=driver.findElement(By.xpath(locator));
        Select select=new Select(element);
        select.selectByVisibleText(Text);

    }
    public void validateText(String locator,String Text){
        element= driver.findElement(By.xpath(locator));
        String textValue=element.getText();
        Assert.assertEquals(textValue,Text);
    }
    public void isDisplayed(String locator){
        element= driver.findElement(By.xpath(locator));
        boolean flag=element.isDisplayed();
        Assert.assertEquals(flag,true);
    }
    public void isEnabled(String locator){
        element= driver.findElement(By.xpath(locator));
        boolean flag=element.isEnabled();
        Assert.assertEquals(flag,true);
    }

    public void alert(String status){
        Alert alert=driver.switchTo().alert();
        if(status.equals("accept")){
            alert.accept();
        }else if(status.equalsIgnoreCase("reject")){
            alert.dismiss();
        }else if(status.equalsIgnoreCase("getText")){
            alert.getText();
        }
    }
    public  List<String> getListData(String locator) {
        List<String> listName = new ArrayList<>();

            List<WebElement> links = driver.findElements(By.xpath(locator));
        System.out.println(links.size());

            for (int index = 0; index < links.size(); index++) {
                WebElement ele = links.get(index);
                String title = ele.getText();
                System.out.println(title);
                listName.add(title);
            }
            System.out.println("Size of List listName = "+listName.size());


        return listName;

    }
    public void verifyListDataAndDelete(String locator,String input){
        List<String> DataList =getListData(locator);
        for (int i=0;i< DataList.size();i++){
            String value=DataList.get(i);
            System.out.println("Validating Data of List");
            if(value.equalsIgnoreCase(input)){
                System.out.println("Data already exit in list");
                String locator1="//div[text()='"+value+"']//preceding::input[1]";
                element=driver.findElement(By.xpath(locator1));
                element.click();
                element=driver.findElement(By.xpath("//button[text()=' Delete Selected ']"));
                element.click();
            }
        }


    }
    public void verifyListData(String locator,String input){
        List<String> DataList =getListData(locator);
        for (int i=0;i< DataList.size();i++){
            String value=DataList.get(i);
            if(value.equalsIgnoreCase(input)){
                System.out.println("Data already exit in list");
               Assert.assertEquals(value,input);
            }
        }


    }
    public void clickCheckBox(String locatorType,String locator){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(locatorType.equals("xpath"))
        {
            element=driver.findElement(By.xpath(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }else if(locatorType.equals("id"))
        {
            element=driver.findElement(By.id(locator)) ;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
        else if(locatorType.equals("name"))
        {
            element=driver.findElement(By.name(locator)) ;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }else if(locatorType.equals("className"))
        {
            element=driver.findElement(By.className(locator)) ;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }else if(locatorType.equals("css"))
        {
            element=driver.findElement(By.cssSelector(locator)) ;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }


    }

    /*Method Description: This method is used to take the screenshot and save in
                         specified path in .png file.
       Returns: null
     */
    public void captureScreenshot(String screenshotName) {
//        driver=initializeDriver();
        String UserDir = System.getProperty("user.dir");
        String PathSep = System.getProperty("file.separator");
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework" + PathSep + "screenshot"+PathSep;

        System.out.println(driver);
        try {
// To create reference of TakesScreenshot
//            TakesScreenshot screenshot = (TakesScreenshot) driver;
// Call method to capture screenshot
//            File src = screenshot.getScreenshotAs(OutputType.FILE);
// Copy files to specific location
// result.getName() will return name of test case so that screenshot name will be same as test case name
            System.out.println(driver);
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
}

