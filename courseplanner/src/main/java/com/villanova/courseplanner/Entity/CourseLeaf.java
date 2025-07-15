package com.villanova.courseplanner.Entity;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("CourseLeaf")
public class CourseLeaf extends PrerequisiteLogic {

    @Relationship(type = "LINKS_TO", direction = Relationship.Direction.OUTGOING)
    private Course course;

    public CourseLeaf() {}

    public CourseLeaf(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

