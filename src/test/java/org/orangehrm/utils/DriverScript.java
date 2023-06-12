package org.orangehrm.utils;

import org.orangehrm.reusable.ReusableMethods;
import org.testng.annotations.Test;

public class DriverScript {
    ReadExcelData readExcelData=new ReadExcelData();
    ReusableMethods reuse =new ReusableMethods();
    String path="src/test/java/org/orangehrm/testdata/InputKeywords.xlsx";
    String sheet="Sheet1";
    int rowNum;

    @Test
    public void driveTestMethods(){
        rowNum=readExcelData.setExcelData(path,sheet);
        for(int row=1;row<=rowNum;row++){
            String cellValue=readExcelData.getCellData(row,0);
            if(cellValue.equals("setUpDriver")){
                String ar1 = readExcelData.getCellData(row,row);
                reuse.setUpDriver(ar1);
            }
            else if(cellValue.equals("logInToApp")){
                String ag1 = readExcelData.getCellData(row,1);
                String arg2 =readExcelData.getCellData(row,row);
                reuse.logInToApp(ag1,arg2);
            }
//            else if(cellValue.equals("clickAdmin")){
//                reuse.clickAdmin();
//            }
//            else if(cellValue.equals("searchUser")){
//                reuse.searchUser();
//            }
            else if(cellValue.equals("tearDown")){
                reuse.tearDown();
            }
        }
    }
}
