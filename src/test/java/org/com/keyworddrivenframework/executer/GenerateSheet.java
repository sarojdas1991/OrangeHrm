package org.com.keyworddrivenframework.executer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.com.keyworddrivenframework.keywordMapper.KeywordEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.com.keyworddrivenframework.base.Base.PathSep;
import static org.com.keyworddrivenframework.base.Base.UserDir;

public class GenerateSheet {
    KeywordEngine engine;

    Workbook book;
    Sheet sheet;
    public  void getSheetName(String sheetName) {
        String filePath = UserDir + PathSep + "src" + PathSep + "test" + PathSep + "java" + PathSep + "org" + PathSep + "com" + PathSep + "keyworddrivenframework"+ PathSep+"testdata" + PathSep + "InputKeywords2.xlsx";

        try {
            File file=new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            book= WorkbookFactory.create(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        sheet=book.getSheet(sheetName);
        for(int i=0;i<sheet.getLastRowNum();i++){
            String cellValue=sheet.getRow(i+1).getCell(1).getStringCellValue();
            if(cellValue.equalsIgnoreCase("Y")){
                String cellData=sheet.getRow(i+1).getCell(i).getStringCellValue();
                System.out.println("Scenario Name "+cellData);
                engine=new KeywordEngine();
                engine.startExecution(cellData);
            }

        }

    }
}
