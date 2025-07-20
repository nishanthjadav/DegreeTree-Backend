package com.villanova.courseplanner.Service;

import com.villanova.courseplanner.Entity.*;
import com.villanova.courseplanner.Repository.CourseLeafRepository;
import com.villanova.courseplanner.Repository.CourseRepository;
import com.villanova.courseplanner.Repository.OperatorNodeRepository;
import com.villanova.courseplanner.dto.CoursePrerequisiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private CourseLeafRepository leafRepo;
    @Autowired
    private OperatorNodeRepository opRepo;

    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Optional<Course> getCourse(Long code) {
        return courseRepo.findById(code);
    }

    public Course getCourseByCode(String courseCode) {
        return courseRepo.findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
    }

    public CoursePrerequisiteDTO getCoursePrerequisites(String courseCode) {
        return courseRepo.findCoursePrerequisites(courseCode)
                .orElseThrow(() -> new RuntimeException("Course prerequisites not found for " + courseCode));
    }

    public Map<String, Object> getPrerequisiteTree(String courseCode) {
        try {
            List<Map<String, Object>> treeData = courseRepo.findPrerequisiteTreeStructure(courseCode);
            return buildPrerequisiteTree(treeData);
        } catch (Exception e) {
            // Fallback to simple structure
            CoursePrerequisiteDTO dto = getCoursePrerequisites(courseCode);
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("type", "AND");
            List<Map<String, Object>> children = new ArrayList<>();
            for (Course prereq : dto.getPrerequisites()) {
                Map<String, Object> child = new HashMap<>();
                child.put("courseCode", prereq.getCourseCode());
                child.put("courseName", prereq.getCourseName());
                children.add(child);
            }
            fallback.put("children", children);
            return fallback;
        }
    }

    private Map<String, Object> buildPrerequisiteTree(List<Map<String, Object>> treeData) {
        Map<Long, Map<String, Object>> nodeMap = new HashMap<>();
        Map<String, Object> root = null;

        // Build node map
        for (Map<String, Object> nodeData : treeData) {
            Long nodeId = (Long) nodeData.get("nodeId");
            Map<String, Object> node = new HashMap<>();
            
            String nodeLabel = (String) nodeData.get("nodeLabel");
            if ("OperatorNode".equals(nodeLabel)) {
                node.put("type", nodeData.get("nodeType"));
                node.put("children", new ArrayList<>());
            } else if ("CourseLeaf".equals(nodeLabel)) {
                node.put("courseCode", nodeData.get("courseCode"));
            }
            
            nodeMap.put(nodeId, node);
            
            // Assume first node is root (can be improved)
            if (root == null) {
                root = node;
            }
        }

        // Build relationships
        for (Map<String, Object> nodeData : treeData) {
            Long nodeId = (Long) nodeData.get("nodeId");
            List<Long> childIds = (List<Long>) nodeData.get("childIds");
            
            Map<String, Object> node = nodeMap.get(nodeId);
            if (node.containsKey("children")) {
                List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
                for (Long childId : childIds) {
                    if (nodeMap.containsKey(childId)) {
                        children.add(nodeMap.get(childId));
                    }
                }
            }
        }

        return root;
    }
}

