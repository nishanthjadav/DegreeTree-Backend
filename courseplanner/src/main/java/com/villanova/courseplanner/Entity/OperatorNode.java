package com.villanova.courseplanner.Entity;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("Operator")
public class OperatorNode extends PrerequisiteLogic {

    public enum OperatorType { AND, OR }

    private OperatorType type;

    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private List<PrerequisiteLogic> children = new ArrayList<>();

    public OperatorNode() {}

    public OperatorNode(OperatorType type) {
        this.type = type;
    }

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }

    public List<PrerequisiteLogic> getChildren() {
        return children;
    }

    public void setChildren(List<PrerequisiteLogic> children) {
        this.children = children;
    }

    public void addChild(PrerequisiteLogic child) {
        this.children.add(child);
    }
}
