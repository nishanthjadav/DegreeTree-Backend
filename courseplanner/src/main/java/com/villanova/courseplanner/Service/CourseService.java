package com.villanova.courseplanner.Service;

import com.villanova.courseplanner.Entity.*;
import com.villanova.courseplanner.Repository.CourseLeafRepository;
import com.villanova.courseplanner.Repository.CourseRepository;
import com.villanova.courseplanner.Repository.OperatorNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final OperatorNodeRepository operatorNodeRepository;
    private final CourseLeafRepository courseLeafRepository;

    public CourseService(CourseRepository courseRepository,
                         OperatorNodeRepository operatorNodeRepository,
                         CourseLeafRepository courseLeafRepository) {
        this.courseRepository = courseRepository;
        this.operatorNodeRepository = operatorNodeRepository;
        this.courseLeafRepository = courseLeafRepository;
    }

    public Optional<Course> getCourseById(String code) {
        return courseRepository.findById(code);
    }

    public List<Course> findEligibleCourses(List<String> completedCourses) {
        Set<String> completedSet = new HashSet<>(completedCourses);
        List<Course> allCourses = courseRepository.findAll();
        List<Course> eligibleCourses = new ArrayList<>();

        for (Course course : allCourses) {
            PrerequisiteLogic root = course.getPrerequisiteLogic();
            if (root == null || satisfies(root, completedSet)) {
                eligibleCourses.add(course);
            }
        }
        return eligibleCourses;
    }

    private boolean satisfies(PrerequisiteLogic node, Set<String> completed) {
        if (node instanceof CourseLeaf) {
            return completed.contains(((CourseLeaf) node).getCourse().getCourseCode());
        } else if (node instanceof OperatorNode) {
            OperatorNode opNode = (OperatorNode) node;
            List<PrerequisiteLogic> children = opNode.getChildren();
            if (opNode.getType() == OperatorNode.OperatorType.AND) {
                return children.stream().allMatch(child -> satisfies(child, completed));
            } else if (opNode.getType() == OperatorNode.OperatorType.OR) {
                return children.stream().anyMatch(child -> satisfies(child, completed));
            }
        }
        return false;
    }


}
