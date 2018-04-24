package com.adam.technician;

public class TechnicianApp {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("Two test types not provided in program arguments.");
        }
        String testType1 = args[0];
        String testType2 = args[1];
        String host = args.length > 2 ? args[2] : "localhost";
        Technician technician = new Technician(host, testType1, testType2);
        technician.start();
    }
}
