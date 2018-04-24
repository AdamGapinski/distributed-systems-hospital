package com.adam.common;

public class TestOrder {
    private final Test test;
    private final String doctorName;
    private final String patientName;

    public TestOrder(Test test, String doctorName, String patientName) {
        this.test = test;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }

    public Test getTest() {
        return test;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    @Override
    public String toString() {
        return "TestOrder{" +
                "test=" + test +
                ", doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
