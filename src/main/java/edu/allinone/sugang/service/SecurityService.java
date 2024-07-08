package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {

    private final StudentLoginService studentLoginService;

    @Override
    // 주어진 학번 기반으로 사용자 정보 로드
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {
        Optional<Student> findOne = studentLoginService.findOne(studentNumber); // 학번 검색
        log.info("findOne : {}", findOne); // 검색된 사용자 정보 로그에 기록
        Student student = findOne.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 학번입니다."));
        // 사용자가 존재하지 않으면 UsernameNotFoundException 예외 발생
        // 위 로그 메세지는 사용자에게 표시되는 메세지가 아니고 예외 객체에 저장됨 -> 로그 파일에 기록

        return User.builder()
                .username(student.getStudentNumber())
                .password(student.getStudentPassword())
                .build();
    }

}
