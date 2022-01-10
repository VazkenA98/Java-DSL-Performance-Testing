package com.picsart.tests;

import org.apache.jmeter.threads.JMeterVariables;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MimeTypes;
import org.testng.Assert;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static us.abstracta.jmeter.javadsl.dashboard.DashboardVisualizer.dashboardVisualizer;

public class PicsartApi {


    @Test
    public void simplePhotoDiscover() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup(2, 10, //uses 2 threads (concurrent users) which send 10 HTTP GET requests each
                        httpSampler("https://api.picsart.com/preproduction/photos/discover/cards?market=apple&include_premium=false")
                )
                //this is just to log details of each request stats
                //jtlWriter("test" + Instant.now().toString().replace(":", "-") + ".jtl")
                // htmlReporter("googleTest-" + Instant.now().toString().replace(":", "-")),
                //responseFileSaver(Instant.now().toString().replace(":", "-") + "-response")
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    //threadGroup(10, 20) // 10 threads for 20 iterations each
    //threadGroup(10, Duration.ofSeconds(20)) // 10 threads for 20 seconds each

    //Avoid starting all threads at once affecting performance metrics and generation

    //threadGroup().rampTo(10, Duration.ofSeconds(5)).holdIterating(20) // ramp to 10 threads for 5 seconds (1 thread every half second) and iterating each thread 20 times
    //threadGroup().rampToAndHold(10, Duration.ofSeconds(5), Duration.ofSeconds(20)) //similar as above but after ramping up holding execution for 20 seconds

    @Test
    public void complexThreadGroupPhotoDiscover() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup()
                        .rampToAndHold(10, Duration.ofSeconds(5), Duration.ofSeconds(20))
                        .rampToAndHold(100, Duration.ofSeconds(10), Duration.ofSeconds(30))
                        .rampTo(200, Duration.ofSeconds(10))
                        .rampToAndHold(100, Duration.ofSeconds(10), Duration.ofSeconds(30))
                        .rampTo(0, Duration.ofSeconds(5))
                        .children(
                                httpSampler("https://google.com")

                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void rampPhotoDiscover() throws IOException {
        TestPlanStats stats = testPlan(
                rpsThreadGroup()
                        .maxThreads(500)
                        .rampTo(20, Duration.ofSeconds(10))
                        .rampTo(10, Duration.ofSeconds(10))
                        .rampToAndHold(1000, Duration.ofSeconds(5), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://google.com")

                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void multiGroup() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("User Data")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://api.picsart.com/preproduction/user/data")
                                        .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                        ),
                threadGroup("Show me")
                        .rampToAndHold(10, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://api.picsart.com/preproduction/users/show/me.json")
                                        .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void tearDown() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup(2, 1,
                        httpSampler("https://api.picsart.com/preproduction/user/data")
                                .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                ),
                teardownThreadGroup(
                        httpSampler("https://api.picsart.com/preproduction/users/remove/378863189033101")
                                .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                                .method(HttpMethod.DELETE)
                ),
                responseFileSaver(Instant.now().toString().replace(":", "-") + "-response")
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }

    @Test
    public void updateUsernameWithExistingIUsername() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Update Username")
                        .rampToAndHold(400, Duration.ofSeconds(10), Duration.ofSeconds(10))
                        .children(
                                httpSampler("https://api.picsart.com/users/update.json")
                                        .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                                        .body("{ \"password\": \"\", \"name\": \"\", \"email\": \"\", \"username\": \"dtygsujkd888\" }")
                                        .contentType(MimeTypes.Type.APPLICATION_JSON_UTF_8)
                                        .method(HttpMethod.POST)
                                        .children(
                                                jsr223PostProcessor(s -> System.out.println(s.prevMap()))

                                        )
                        ),
                dashboardVisualizer()
        ).run();
        System.out.println(stats.overall().sampleTimePercentile99().toSeconds());
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }


    @Test
    public void updateUsername() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup("Update Username")
                        .rampToAndHold(1, Duration.ofSeconds(1), Duration.ofSeconds(1))
                        .children(
                                httpSampler("https://api.picsart.com/preproduction/users/update.json")
                                        .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                                        .body("${REQUEST_BODY}")
                                        .contentType(MimeTypes.Type.APPLICATION_JSON_UTF_8)
                                        .method(HttpMethod.POST)
                                        .children(
                                                jsr223PreProcessor("vars.put('REQUEST_BODY', " + getClass().getName()
                                                        + ".buildRequestBody(vars))"),
                                                jsr223PostProcessor(s -> System.out.println(s.prevMap()))

                                        )
                        ),
                //dashboardVisualizer()
                responseFileSaver(Instant.now().toString().replace(":", "-") + "-response")
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }


    public static String buildRequestBody(JMeterVariables vars) {
        String countVarName = "REQUEST_COUNT";
        Integer countVar = (Integer) vars.getObject(countVarName);
        int count = countVar != null ? countVar + 1 : 1;
        vars.putObject(countVarName, count);
        return "{ \"password\": \"\", \"name\": \"\", \"email\": \"\", \"username\": \"" + TestUtils.randomUsername() + "\" }";
    }

/*
    @Test
    public void complexThreadGroupPhotoDiscover() throws IOException {
        TestPlanStats stats = testPlan(
                threadGroup()
                        .rampToAndHold(10, Duration.ofSeconds(5), Duration.ofSeconds(20))
                        .rampToAndHold(100, Duration.ofSeconds(10), Duration.ofSeconds(30))
                        .rampTo(200, Duration.ofSeconds(10))
                        .rampToAndHold(100, Duration.ofSeconds(10), Duration.ofSeconds(30))
                        .rampTo(0, Duration.ofSeconds(5))
                        .children(
                                httpSampler("https://api.picsart.com/preproduction/users/reports/add/378863189033101.json")
                                        .method(HttpMethod.POST)
                                        .contentType(MimeTypes.Type.APPLICATION_JSON_UTF_8)
                                        .header("x-api-key", "8780bad6-57e5-4918-9ae9-392e61c4dc7c")
                                        .body("{\"password\":\"1\"")
                                        .children(
                                                jsr223PostProcessor(s -> System.out.println(s.prevMap()))

                                        )
                        ),
                dashboardVisualizer()
        ).run();
        Assert.assertTrue(stats.overall().sampleTimePercentile99().toSeconds() <= Duration.ofSeconds(5).toSeconds());
    }
*/





}
