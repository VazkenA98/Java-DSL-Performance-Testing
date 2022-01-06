package com.picsart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;


public class PicsartWebDemo2 extends TestBase{

    @Test
    public void mainPageS() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Main Page", 10, 10,
                        httpSampler("https://picsart.com")

                ),
                htmlReporter("results/googleTest-")
        ).run();
        System.out.println(stats.overall().sampleTimePercentile99().toSeconds());
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }



}