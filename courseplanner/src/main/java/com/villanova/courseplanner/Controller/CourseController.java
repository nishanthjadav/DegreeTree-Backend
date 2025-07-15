package com.villanova.courseplanner.Controller;

import com.villanova.courseplanner.Entity.Course;
import com.villanova.courseplanner.Repository.CourseRepository;
import com.villanova.courseplanner.Service.CourseService;
import com.villanova.courseplanner.dto.EligibilityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:4173"}) // Allow frontend ports
public class CourseController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository){
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourse(@PathVariable String code) {
        return courseService.getCourseById(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all-courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        return ResponseEntity.ok(allCourses);
    }
    @PostMapping("/eligible")
    public ResponseEntity<List<Course>> getEligibleCourses(@RequestBody EligibilityRequest request) {
        List<String> completed = request.getCompletedCourses();
        List<Course> eligible = courseService.findEligibleCourses(completed);
        return ResponseEntity.ok(eligible);
    }


}

