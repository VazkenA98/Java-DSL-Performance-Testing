package com.picsart.tests;

import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.blazemeter.BlazeMeterEngine;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.time.Duration;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class PicsartWebBlazemeter {

    @Test
    public void testPerformance() throws Exception {
        TestPlanStats stats = testPlan(
                // number of threads and iterations are in the end overwritten by BlazeMeter engine settings
                threadGroup(2, 10,
                        httpSampler("https://picsart.com")
                )
        ).runIn(new BlazeMeterEngine("a4b14372ed5009462439a139:dbe8712bd2ad7f3923173239517aca0fa3705aa22c4b21f093ace2ad7ca415c41761ca43")
                .testName("Picsart Main Page")
                .totalUsers(10)
                .holdFor(Duration.ofSeconds(1))
                .threadsPerEngine(20)
                .testTimeout(Duration.ofMinutes(2)));
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }


}
