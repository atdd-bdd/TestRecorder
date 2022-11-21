package com.kenpugh.testrecorder.entities;

public class TestFilter {
    public Boolean includeActive = false;
    public Boolean includeInactive = false;
    public Boolean includeRetired = false;
    @SuppressWarnings("unused")
    public  TestFilterDTO toDTO(){
        TestFilterDTO testFilterDTO = new TestFilterDTO();
        testFilterDTO.includeActive = this.includeActive.toString();
        testFilterDTO.includeInactive = this.includeInactive.toString();
        testFilterDTO.includeRetired = this.includeInactive.toString();
        return testFilterDTO;
    }
    public static TestFilter fromTestFilterDTO(TestFilterDTO testFilterDTO){
        TestFilter testFilter = new TestFilter();
        testFilter.includeActive = Boolean.parseBoolean(testFilterDTO.includeActive);
        testFilter.includeRetired = Boolean.parseBoolean((testFilterDTO.includeRetired));
        testFilter.includeInactive = Boolean.parseBoolean(testFilterDTO.includeInactive);
        return testFilter;
    }

    @Override
    public String toString() {
        return "TestFilter{" +
                "includeActive=" + includeActive +
                ", includeInActive=" + includeInactive +
                ", includeRetired=" + includeRetired +
                '}';
    }
}
