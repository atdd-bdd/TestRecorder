package com.kenpugh.testrecorder.entities;

public class TestFilterDTO {
    public String includeActive = "false";
    public String includeInactive = "false";
    public String includeRetired = "false";

    @Override
    public String toString() {
        return "TestFilterDTO{" +
                "includeActive='" + includeActive + '\'' +
                ", includeInActive='" + includeInactive + '\'' +
                ", includeRetired='" + includeRetired + '\'' +
                '}';
    }
}

