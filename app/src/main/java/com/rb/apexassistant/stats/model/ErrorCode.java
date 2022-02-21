package com.rb.apexassistant.stats.model;

public class ErrorCode {
    public String Error;

    public ErrorCode(String error) {
        Error = error;
    }

    public ErrorCode() {

    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
