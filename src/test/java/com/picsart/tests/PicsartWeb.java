package com.picsart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static us.abstracta.jmeter.javadsl.dashboard.DashboardVisualizer.dashboardVisualizer;
import static us.abstracta.jmeter.javadsl.parallel.ParallelController.parallelController;

public class PicsartWeb {

    @Test
    public void mainPageS() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Main Page", 20, 30,
                        httpSampler("https://picsart.com")
                ),
                dashboardVisualizer()
                //htmlReporter("googleTest-" + Instant.now().toString().replace(":", "-"))
        ).run();
        System.out.println(stats.overall().sampleTimePercentile99().toSeconds());
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void mainPageSF() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Main Page", 100, 5,
                        httpSampler("https://picsart.com")
                ),
                dashboardVisualizer()
                //htmlReporter("googleTest-" + Instant.now().toString().replace(":", "-"))
        ).run();
        System.out.println(stats.overall().sampleTimePercentile99().toSeconds());
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void mainPageF() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Main Page", 800, 1,
                        httpSampler("https://picsart.com")
                ),
                dashboardVisualizer()
                //htmlReporter("googleTest-" + Instant.now().toString().replace(":", "-"))
        ).run();
        System.out.println(stats.overall().sampleTimePercentile99().toSeconds());
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void headerPagesS() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Create Page")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/create")
                        ),
                threadGroup("Batch Page")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/create/batch")
                        ),
                threadGroup("Blog Page")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/blog")
                        ),
                threadGroup("Pricing Page")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/gold")
                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void headerPagesSF() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Create Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/create")
                        ),
                threadGroup("Batch Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/create/batch")
                        ),
                threadGroup("Blog Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/blog")
                        ),
                threadGroup("Pricing Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/gold")
                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void groupHeaders() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Create Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/create")
                        ),
                threadGroup("Batch Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/create/batch")
                        ),
                threadGroup("Blog Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/blog")
                        ),
                threadGroup("Pricing Page")
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(20))
                        .children(
                                httpSampler("https://picsart.com/gold")
                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }


    @Test
    public void challengesPageF() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Challenges Page")
                        .rampToAndHold(10, Duration.ofSeconds(5), Duration.ofSeconds(10))
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(5))
                        .rampTo(0, Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/challenges")

                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(10).toSeconds());
    }

    @Test
    public void challengesPageFCondition() throws IOException {
        AtomicInteger ERROR_COUNT = new AtomicInteger();
        TestPlanStats stats = testPlan(
                threadGroup("Challenges Page")
                        .rampToAndHold(10, Duration.ofSeconds(5), Duration.ofSeconds(10))
                        .rampToAndHold(50, Duration.ofSeconds(10), Duration.ofSeconds(5))
                        .rampTo(0, Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://picsart.com/challenges")
                                        .children(
                                                jsr223PostProcessor(s -> {
                                                    if ("503".equals(s.prev.getResponseCode())) {
                                                        ERROR_COUNT.addAndGet(s.prev.getErrorCount());
                                                    }
                                                })
                                        )

                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertEquals(ERROR_COUNT.get(), 0, "Challenges Page Failed With " + ERROR_COUNT + " ERRORS");
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(10).toSeconds());
    }




    //####################Controllers####################
    @Test
    public void parallel() throws Exception {
        TestPlanStats stats = testPlan(
                threadGroup(2, 10,
                        parallelController(
                                httpSampler("Main Url", "https://picsart.com"),
                                httpSampler("Blog Url", "https://picsart.com/blog"))
                ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }


    @Test
    public void testTransactions() throws IOException {
        testPlan(
                threadGroup(2, 10,
                        transaction("Main Page",
                                httpSampler("Main Url", "https://picsart.com")
                                        .children(uniformRandomTimer(1000, 3000))
                        ),
                        transaction("Create Page",
                                httpSampler("Create Url", "https://picsart.com/create")
                        )
                ),
                dashboardVisualizer()
        ).run();
    }

    @Test
    public void percentageTests() throws Exception {
        TestPlanStats stats = testPlan(
                threadGroup(5, 10,
                        percentController(20, // run this 20% of the times
                                httpSampler("Blog Url", "https://picsart.com/blog")),
                        percentController(80, // run this 80% of the times
                                httpSampler("Main Url", "https://picsart.com"))
                ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(10).toSeconds());
    }


}