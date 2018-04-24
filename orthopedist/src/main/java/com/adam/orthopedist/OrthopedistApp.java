package com.adam.orthopedist;

public class OrthopedistApp {
    public static void main(String[] args) throws Exception {
        String host = args.length < 2 ? "localhost" : args[1];
        String name = args.length < 1 ? "Jan Kowalski" : args[0];
        Orthopedist orthopedist = new Orthopedist(host, name);
        orthopedist.start();
    }
}
