package com.example.zmj;

public class srt {
    private String body;
    private long startTime;
    private long endTime;

    public srt(String body, long startTime, long endTime) {
        this.body = body;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
