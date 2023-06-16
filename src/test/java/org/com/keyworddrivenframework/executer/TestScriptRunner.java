package org.com.keyworddrivenframework.executer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.com.keyworddrivenframework.keywordMapper.KeywordEngine;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.com.keyworddrivenframework.base.Base.PathSep;
import static org.com.keyworddrivenframework.base.Base.UserDir;

public class TestScriptRunner {
    KeywordEngine engine;
    String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework"+ PathSep+"testdata" + PathSep + "InputKeywords.xlsx";

    @Test(dataProvider = "testDataMethod1")
    public void testMethod1(String username) {
        engine=new KeywordEngine();
        System.out.println("Executing testMethod1 with Scenario name: " + username);
        engine.startExecution(username);

    }

    //    @Test(dataProvider = "testDataMethod2")
    public void testMethod2(String username) {
        // Test method 2 logic
        System.out.println("Executing testMethod2 with username: " + username);
    }

    @DataProvider(name = "testDataMethod1")
    public Object[][] testDataMethod1() throws IOException {
        return getTestData(filePath, "Executor", "Y");
    }

    @DataProvider(name = "testDataMethod2")
    public Object[][] testDataMethod2() throws IOException {
        return getTestData(filePath, "Executor", "N");
    }

    private Object[][] getTestData(String excelFilePath, String sheetName, String flag) throws IOException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int dataCount = 0;

        // Count the number of rows with the specified flag
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            Cell flagCell = row.getCell(1);
            String cellValue = flagCell.getStringCellValue();
            if (cellValue.equalsIgnoreCase(flag)) {
                dataCount++;
            }
        }

        Object[][] data = new Object[dataCount][1];
        int dataIndex = 0;

        // Populate the data array with usernames having the specified flag
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            Cell flagCell = row.getCell(1);
            String cellValue = flagCell.getStringCellValue();

            if (cellValue.equalsIgnoreCase(flag)) {
                Cell usernameCell = row.getCell(0);
                String username = usernameCell.getStringCellValue();
                data[dataIndex][0] = username;
                dataIndex++;
            }
        }

        workbook.close();
        inputStream.close();

        return data;
    }

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[] { TestScriptRunner.class });
        testng.run();
    }
}
