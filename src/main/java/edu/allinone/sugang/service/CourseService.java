package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.*;
import edu.allinone.sugang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Lecture> getLecturesByDepartment(Long departmentId) {
        return lectureRepository.findByDepartmentId(departmentId);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }
}
