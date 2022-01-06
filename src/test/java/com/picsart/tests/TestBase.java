package com.picsart.tests;

import org.apache.jmeter.protocol.http.sampler.HTTPSampleResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import us.abstracta.jmeter.javadsl.core.postprocessors.DslJsr223PostProcessor;

public class TestBase {

    @AfterMethod
    public void afterMethod(){
        LogUtils.removeLogs();
    }

    @BeforeSuite
    public void beforeSuit(){
        LogUtils.initLogObject();
    }

    protected void logRequestResponse(DslJsr223PostProcessor.PostProcessorVars s) {
        String logString = "Request : "+((HTTPSampleResult) s.prev).getHTTPMethod() +" "+ s.ctx.getCurrentSampler().toString()+" "
                +"Response : " + s.prevMap().get("responseCode") +" "+ s.prevMap().get("responseMessage");
        LogUtils.setLogs(logString);
    }

}
