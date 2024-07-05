package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {

    private final StudentLoginService studentLoginService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String insertedStudentNumber) throws UsernameNotFoundException {
        Optional<Student> findOne = studentLoginService.findOne(insertedStudentNumber);
        log.info("findOne : {}", findOne);
        Student student = findOne.orElseThrow( () -> new UsernameNotFoundException("존재하지 않는 학번입니다."));

        return User.builder()
                .username(student.getStudentNumber())
                .password(student.getStudentPassword())
                .build();
    }

    public Student createStudent(String studentNumber, String studentPassword, PasswordEncoder passwordEncoder) {
        Student student = new Student();
        student.setId(null);
        student.setStudentNumber(studentNumber);
        student.setStudentPassword(passwordEncoder.encode(studentPassword));
        return student;
    }
}
