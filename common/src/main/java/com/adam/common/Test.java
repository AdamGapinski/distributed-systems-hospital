package com.adam.common;

public class Test {
    private final TestType type;
    private final Boolean done;

    public Test(TestType type) {
        this.type = type;
        this.done = false;
    }

    public Test(TestType type, Boolean done) {
        this.type = type;
        this.done = done;
    }

    public TestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Test{" +
                "type=" + type +
                ", done=" + done +
                '}';
    }
}
