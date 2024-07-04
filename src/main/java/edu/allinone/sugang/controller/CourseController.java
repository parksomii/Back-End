package edu.allinone.sugang.controller;

import edu.allinone.sugang.domain.*;
import edu.allinone.sugang.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/departments")
    public String getAllDepartments(Model model) {
        List<Department> departments = courseService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departments";
    }
    @GetMapping("/lectures")
    public String getLecturesByDepartment(@RequestParam Long departmentId, Model model) {
        List<Lecture> lectures = courseService.getLecturesByDepartment(departmentId);
        model.addAttribute("lectures", lectures);
        return "lectures";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        List<Student> students = courseService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        List<Subject> subjects = courseService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects";
    }

    @GetMapping("/professors")
    public String getAllProfessors(Model model) {
        List<Professor> professors = courseService.getAllProfessors();
        model.addAttribute("professors", professors);
        return "professors";
    }
}
