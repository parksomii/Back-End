package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // 학생 아이디와 과목 아이디로 수강 신청 정보 가져오기
    Optional<Enrollment> findByStudentIdAndLectureId(Long studentId, Long lectureId);
}
