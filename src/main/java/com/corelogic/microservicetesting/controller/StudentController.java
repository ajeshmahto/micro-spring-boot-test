package com.corelogic.microservicetesting.controller;

import com.corelogic.microservicetesting.model.Course;
import com.corelogic.microservicetesting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by ajesh on 11/15/17.
 */

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/students/{studentId}/courses")
    public List<Course> getCoursesFromStudent(@PathVariable String studentId){

        return studentService.retrieveCourses(studentId);

    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public Course getDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId){
        return studentService.retrieveCourse(studentId,courseId);
    }

    @PostMapping("/students/{studentId}/courses")
    public ResponseEntity<Void> registerStudentCourse(@PathVariable String studentId, @RequestBody Course newCourses){

        Course course = studentService.addCourse(studentId,newCourses);
        if(course==null){
            return ResponseEntity.noContent().build();
        }

        URI location  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
