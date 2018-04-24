package com.adam.common;

public class TestResult {
    private final Test test;
    private final String patientName;

    public TestResult(Test test, String patientName) {
        this.test = test;
        this.patientName = patientName;
    }

    public Test getTest() {
        return test;
    }

    public String getPatientName() {
        return patientName;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "test=" + test +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
