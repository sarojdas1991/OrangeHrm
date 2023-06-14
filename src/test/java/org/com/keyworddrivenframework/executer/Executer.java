package org.com.keyworddrivenframework.executer;

import org.com.keyworddrivenframework.keywordMapper.KeywordEngine;
import org.testng.annotations.Test;

public class Executer {
    public KeywordEngine engine;
    @Test(priority = 1)
    public void loginTest(){
        engine=new KeywordEngine();
        engine.startExecution("addUser");

    }
//    @Test(priority = 2)
    public void addUser(){
        engine=new KeywordEngine();
        engine.startExecution("Sheet2");

    }
}
