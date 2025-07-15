package com.villanova.courseplanner.Repository;

import com.villanova.courseplanner.Entity.CourseLeaf;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CourseLeafRepository extends Neo4jRepository<CourseLeaf, Long> {
}
