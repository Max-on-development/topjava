package ru.javawebinar.topjava.service;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//https://stackoverflow.com/questions/17552779/record-time-it-takes-junit-tests-to-run
public class MyJUnitStopWatch extends Stopwatch {

    private static Logger log = Logger.getLogger(MyJUnitStopWatch.class.getName());

    public static List<String> resultList = new ArrayList<>();

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String s = testName + " " +TimeUnit.NANOSECONDS.toMillis(nanos) + " milliseconds";
        log.log(Level.INFO, s);
        resultList.add(s);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }
}
