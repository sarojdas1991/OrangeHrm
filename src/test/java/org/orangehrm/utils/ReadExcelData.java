package org.orangehrm.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ReadExcelData {
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    public int setExcelData(String fileName,String sheetName) {
        File file = new File(fileName);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sheet= workbook.getSheet(sheetName);
        int rowNo=sheet.getLastRowNum();
        return rowNo;

    }
    public String getCellData(int row,int column){
        Cell cell=sheet.getRow(row).getCell(column);
        String cellData=cell.getStringCellValue();
        return cellData;
    }

}
