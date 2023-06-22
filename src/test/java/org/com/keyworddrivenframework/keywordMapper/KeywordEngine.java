package org.com.keyworddrivenframework.keywordMapper;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.com.keyworddrivenframework.base.Base;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.com.keyworddrivenframework.base.Base.PathSep;
import static org.com.keyworddrivenframework.base.Base.UserDir;

public class KeywordEngine  {
    public Workbook book;
    public Sheet sheet;
     Base base=new Base();
    private static final Logger logger = Logger.getLogger(KeywordEngine.class);
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    public void startExecution(String sheetName)

    {
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework"+ PathSep+"testdata" + PathSep + "InputKeywords.xlsx";
        File file=new File(filePath);
        try {
            FileInputStream input =new FileInputStream(file);
            book= WorkbookFactory.create(input);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        sheet=book.getSheet(sheetName);
        int cell=0;
        for(int row=0;row<sheet.getLastRowNum();row++) {
            String actionName = sheet.getRow(row + 1).getCell(cell).toString().trim();
            String LocatorName = sheet.getRow(row + 1).getCell(cell + 1).toString().trim();
            String LocatorValue = sheet.getRow(row + 1).getCell(cell + 2).toString().trim();
            String Value = sheet.getRow(row + 1).getCell(cell + 3).toString().trim();
            String Data = sheet.getRow(row + 1).getCell(cell + 4).toString().trim();
            String Property=sheet.getRow(row + 1).getCell(cell + 5).toString().trim();
            String LocVal=base.initializePropertyFromFile(Property,LocatorValue);

            switch (actionName) {
                case "openBrowser":
                    logger.info("initializing the browser");
                    if(Value.equalsIgnoreCase("NA")){
                        base.setupDriver(base.initializePropertyFromFile(Property,"browser"));
                    }else {
                        base.setupDriver(Value);
                    }
                    break;
                case "lunchUrl":
                    logger.info("Application getting start");
                    if(Value.equalsIgnoreCase("NA")){
                        base.lunchUrl(base.initializePropertyFromFile(Property,"url"));
                    }else {
                    base.lunchUrl(Value);
                    }
                    break;
                case "sendKey":

                    base.waitForElementIntractable(LocatorName,LocVal);
                    boolean flag=base.isDisplayed(LocatorName,LocVal);
                    if (flag){
                        base.enterText(LocatorName,LocVal, Value);
                    }else {
                        base.captureScreenshot(timeStamp);
                        base.quit();
                        Assert.assertTrue(flag);
                    }

                    break;
                case "click":
                    base.waitForElementIntractable(LocatorName,LocVal);
                    boolean flag1=base.isDisplayed(LocatorName,LocVal);
                    if (flag1==true){
                        base.click(LocatorName,LocVal);
                    }else {
                        base.captureScreenshot(timeStamp);
                        base.quit();
                        Assert.assertTrue(flag1);
                    }
                    break;
                case "selectDropdown":
                    if (Value.equals("index")){
                        base.selectDropDownByIndex(LocatorValue, Integer.parseInt(Data));
                    }else if(Value.equals("value")){
                        base.selectDropDownByValue(LocatorValue,Data);
                    } else if (Value.equals("text")) {
                        base.selectDropDownByVisibleText(LocatorValue,Data);
                    }
                    break;
                case "alert":
                    base.alert(Value);
                case "verifyListDataAndDelete"   :
                    base.verifyListDataAndDelete(LocatorValue,Value);
                    break;
                case "verifyListData"    :
                    base.verifyListData(LocatorValue,Value);
                    break;
                case "clickCheckBox"    :
                    base.waitForElementIntractable(LocatorName,LocVal);
                    boolean flag2=base.isSelected(LocatorName,LocVal);
                    if(flag2==false) {
                        base.clickCheckBox(LocatorName, LocVal);
                    }
                    break;
                case "quit":
                    base.quit();
                    break;
                case "screenshot" :
                    base.captureScreenshot(Value);
                    break;
                case "wait"    :
                    base.waitForElementIntractable(LocatorName,LocatorValue);
                    break;
                case"validateText"    :
                    base.validateText(LocatorName,LocatorValue,Value);
                    break;
                case "refreshBrowser" :
                    base.refreshBrowser();
                    break;
                default:
                    break;

            }


        }


    }
}
