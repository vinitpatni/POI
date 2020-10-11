package com.tomtom.poi.metric;


public class POIGraphiteMetric {
    private String className;
    private String name;

    public POIGraphiteMetric(String className, String name) {
        this.className = className;
        this.name = name;
    }

    public String getClassName() {
        return this.className;
    }

    public String getName() {
        return this.name;
    }
}

