package com.villanova.courseplanner.Repository;

import com.villanova.courseplanner.Entity.Course;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CourseRepository extends Neo4jRepository<Course, String> {
}
