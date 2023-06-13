package org.com.keyworddrivenframework.keywordMapper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.com.keyworddrivenframework.base.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.com.keyworddrivenframework.base.Base.PathSep;
import static org.com.keyworddrivenframework.base.Base.UserDir;

public class KeywordEngine  {
    public Workbook book;
    public Sheet sheet;
     Base base=new Base();
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

            switch (actionName) {
                case "openBrowser":
                    base.initializeDriver();
                    break;
                case "lunchUrl":

                    System.out.println(Value);
                    base.lunchUrl(Value);
                    break;
                case "sendKey":
                    base.enterText(LocatorName, LocatorValue, Value);
                    break;
                case "click":
                    base.click(LocatorName, LocatorValue);
                    break;
                case "selectDropdown":
                    if (Value.equals("index")){
                        base.selectDropDownByIndex(LocatorValue, Integer.parseInt(base.initializeProperty("")));
                    }else if(Value.equals("value")){
                        base.selectDropDownByValue(LocatorValue,base.initializeProperty(""));
                    } else if (Value.equals("text")) {
                        base.selectDropDownByVisibleText(LocatorValue,base.initializeProperty(""));
                    }
                    break;

                case "quit":
                    base.quit();
                    break;
                default:
                    break;

            }


        }


    }
}
