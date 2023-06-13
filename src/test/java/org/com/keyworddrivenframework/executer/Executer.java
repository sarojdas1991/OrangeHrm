package org.com.keyworddrivenframework.executer;

import org.com.keyworddrivenframework.keywordMapper.KeywordEngine;
import org.testng.annotations.Test;

public class Executer {
    public KeywordEngine engine;
    @Test
    public void loginTest(){
        engine=new KeywordEngine();
        engine.startExecution("Sheet1");

    }
}
