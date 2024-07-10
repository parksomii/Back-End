package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    // 학생id와 강의id로 수강 신청 정보 가져오기
    Optional<Enrollment> findByStudentIdAndLectureId(Integer studentId, Integer lectureId);
}
