package org.com.keyworddrivenframework.executer;

import org.testng.annotations.Test;

public class Executor {

GenerateSheet generate =new GenerateSheet();
    @Test
    public void startExecution(){
        generate.getSheetName("Executor");
    }



}
