package org.com.keyworddrivenframework.executer;

import org.com.keyworddrivenframework.base.Base;
import org.com.keyworddrivenframework.base.ExcelUtils;
import org.com.keyworddrivenframework.keywordMapper.KeywordEngine;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.com.keyworddrivenframework.base.Base.PathSep;
import static org.com.keyworddrivenframework.base.Base.UserDir;

public class TestScriptRunner {
    Base base=new Base();
    ExcelUtils excelUtils=new ExcelUtils();
    KeywordEngine engine;
    String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework"+ PathSep+"testdata" + PathSep + "InputKeywords.xlsx";

    @Test(dataProvider = "testDataMethod1")
    public void testMethod1(String username) {
        engine=new KeywordEngine();
        System.out.println("Executing testMethod1 with Scenario name: " + username);
        engine.startExecution(username);

    }
//    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp );
            String Name= "Orange"+timeStamp;
            base.captureScreenshot(Name);
        }
    }

    @DataProvider(name = "testDataMethod1")
    public Object[][] testDataMethod1() throws IOException {
        return excelUtils.getTestData(filePath, "Executor", "Y");
    }

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[] { TestScriptRunner.class });
        testng.run();
    }
}

