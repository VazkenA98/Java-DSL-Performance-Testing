package com.picsart.tests;

import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class LogUtils {
    public static final String SEPARATOR_LOG = "-----------------------------------------------------------------------";
    private static final ThreadLocal<List<String>> logs = new ThreadLocal<>();

    private LogUtils() {
    }

    public static void initLogObject() {
        logs.set(new ArrayList<>());
    }

    public static List<String> getLogs() {
        return logs.get();
    }

    public static void setLogs(String message) {
        message = getLogMessage(message);
        if (logs.get() == null)
            initLogObject();
        logs.get().add(message);
    }

    public static void setLogs(Object message) {
        setLogs(String.valueOf(message));
    }

    public static String getLogMessage(String message) {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        List<StackTraceElement> collect = Arrays.stream(stackTrace).filter(a -> a.getClassName().contains("com.picsart")).collect(Collectors.toList());
        StackTraceElement stackTraceElement = collect.get(collect.size() - 2);
        return String.format("%s:%s:%s : %s", sdf.format(currentTime), stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), message);
    }

    public static void removeLogs() {
        printMessages(getLogs());
        if (logs != null)
            logs.remove();
    }

    private static void printMessages(List<String> messages) {
        messages.forEach(log::info);
    }

    public static String getInfoLog(String info) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = 70 - info.toCharArray().length;
        stringBuilder.append("-".repeat(Math.max(0, length / 2)));
        stringBuilder.append(" ");
        stringBuilder.append(info);
        stringBuilder.append(" ");
        stringBuilder.append("-".repeat(Math.max(0, length / 2)));
        return stringBuilder.toString();
    }
}