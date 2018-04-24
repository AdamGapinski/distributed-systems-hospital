package com.adam.administrator;

public class AdministratorApp {

    public static void main(String[] args) throws Exception {
        String host = args.length < 1 ? "localhost" : args[0];
        Administrator administrator = new Administrator(host);
        administrator.start();
    }
}
