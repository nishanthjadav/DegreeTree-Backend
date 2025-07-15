package com.villanova.courseplanner.Entity;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Course")
public class Course
{
    @Id
    private String courseCode;
    private String courseName;

    private String courseDescription;
    private int credits;

    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private PrerequisiteLogic prerequisiteLogic;  // one root node (AND/OR)

    public Course() {}

    public Course(String courseCode, String courseName, String courseDescription, int credits, PrerequisiteLogic prerequisiteLogic) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.credits = credits;
        this.prerequisiteLogic = prerequisiteLogic;
    }
    public Course(String courseCode, String courseName, String courseDescription, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.credits = credits;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public PrerequisiteLogic getPrerequisiteLogic() {
        return prerequisiteLogic;
    }

    public void setPrerequisiteLogic(PrerequisiteLogic prerequisiteLogic) {
        this.prerequisiteLogic = prerequisiteLogic;
    }
}
