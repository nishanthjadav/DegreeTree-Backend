package com.villanova.courseplanner.Entity;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node
public abstract class PrerequisiteLogic {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
