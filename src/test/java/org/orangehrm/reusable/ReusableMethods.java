package org.orangehrm.reusable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ReusableMethods {
    public WebDriver driver;
    @BeforeTest
    public void setUpDriver(String url){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get(url);

    }
    @Test
    public void logInToApp(String arg1,String arg2)  {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement userId=driver.findElement(By.xpath("//input[@name='username']"));
        userId.sendKeys(arg1);

        WebElement Password=driver.findElement(By.xpath("//input[@name='password']"));
        Password.sendKeys(arg2);
        WebElement LoginBtn=driver.findElement(By.xpath("//button[@type='submit']"));
        LoginBtn.click();
        System.out.println("Login to Application");
    }
    @Test
    public void clickAdmin(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement AdminTab=driver.findElement(By.xpath("//span[text()='Admin']"));
        AdminTab.click();
        System.out.println("click on Adin Tab");

    }
    @Test
    public void clickPim(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement AdminTab=driver.findElement(By.xpath("//span[text()='PIM']"));
        AdminTab.click();
        System.out.println("click on Pim Tab");

    }
    @Test
    public void searchUser(){
        WebElement AdminTab=driver.findElement(By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name' and text()='Admin']"));
        System.out.println("Search the user");
    }
    @AfterTest
    public void tearDown(){
        driver.quit();

    }
}
