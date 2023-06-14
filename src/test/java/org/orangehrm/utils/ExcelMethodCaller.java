package org.orangehrm.utils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelMethodCaller {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("src/test/java/org/orangehrm/testdata/InputKeywords.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet("Sheet1"); // Change "Sheet1" to the actual sheet name in your Excel file

            // Call a method from the Excel sheet
            Row methodRow = sheet.getRow(1); // Assuming the method details are in the second row of the sheet
            String methodName = methodRow.getCell(0).getStringCellValue();
            String methodArg1 = methodRow.getCell(1).getStringCellValue();
            int methodArg2 = (int) methodRow.getCell(2).getNumericCellValue();

            // Call the method with the provided arguments
            Object result = callMethod(methodName, methodArg1, methodArg2);
            System.out.println("Method Result: " + result);

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    // Define your own methods here
    public static Object callMethod(String methodName, String arg1, int arg2) {
        Object result = null;
        // Implement your logic here to call the appropriate method based on the method name
        // You can use a switch statement or if-else conditions to handle different methods

        // Example: Calling a method named "exampleMethod" with two arguments
        if (methodName.equals("exampleMethod")) {
            result = exampleMethod(arg1, arg2);
        }else if(methodName.equals("addMethod")){
            result=addMethod(arg1,arg2);
        }

        return result;
    }

    public static int exampleMethod(String arg1, int arg2) {
        int a=9;
        int c=a+arg2;
        System.out.println("Sum = "+c);
        System.out.println(arg1);
        // Implement your logic for the method here
        return arg1.length() * arg2;
    }
    public static int addMethod(String arg1, int arg2) {
        int a=19;
        int c=a+arg2;
        System.out.println("Sum = "+c);
        System.out.println(arg1);
        // Implement your logic for the method here
        return arg1.length() * arg2;
    }

}
