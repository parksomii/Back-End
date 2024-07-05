package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Student;
import edu.allinone.sugang.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentLoginService {

    private final StudentRepository studentRepository;

    public Optional<Student> findOne(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber);
    }
}
