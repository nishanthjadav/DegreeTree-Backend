package com.villanova.courseplanner.dto;

import java.util.List;

public class EligibilityRequest {
    private List<String> completedCourses;

    public List<String> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(List<String> completedCourses) {
        this.completedCourses = completedCourses;
    }
}
