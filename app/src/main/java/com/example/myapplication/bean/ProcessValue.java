package com.example.myapplication.bean;

public class ProcessValue {
    public final String process;

    public static ProcessValue getInstance(String process) {
        return new ProcessValue(process);
    }

    private ProcessValue(String process) {
        this.process = process;
    }
}
