package org.com.keyworddrivenframework.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Base {
    public WebDriver driver;
    public Properties prop;
    public WebElement element;
    public static String UserDir = System.getProperty("user.dir");
    public static String PathSep = System.getProperty("file.separator");

    public void initializeDriver() {
//        if (browser.equals("chrome"))
//        {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
            driver.manage().window().maximize();
//        }
//        else if(browser.equals("firefox"))
//        {
//            WebDriverManager.firefoxdriver().setup();
//            driver=new FirefoxDriver();
//        }

    }
    public String initializeProperty(String data){
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework"+ PathSep+"properties" + PathSep + "Data.properties";

        prop = new Properties();
        try {
            FileReader reader = new FileReader(filePath);

            prop.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("IOException");

        }
        return prop.getProperty(data);

    }
    public void lunchUrl(String url){
           driver.get(url);
       }
    public void quit(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
    public void enterText(String locatorType,String locator,String key){
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
}

