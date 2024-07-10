package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Enrollment;
import edu.allinone.sugang.domain.Lecture;
import edu.allinone.sugang.domain.Student;
import edu.allinone.sugang.repository.EnrollmentRepository;
import edu.allinone.sugang.repository.LectureRepository;
import edu.allinone.sugang.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    /* ================================================================= */
    //                              수강 신청                            //
    /* ================================================================= */

    /**
     * 수강 신청
     */
    @Transactional
    public void enroll(Integer studentId, Integer lectureId) {
        // 1. 강의 정보, 학생 정보 가져오기
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        // 2. 정원이 찼는지 확인
        if (lecture.getTotalCapacity() - lecture.getEnrolledCount() <= 0) {
            throw new IllegalArgumentException("정원이 찼습니다.");
        }

        // 3. 신청 가능 학점 확인
        if (student.getMaxCredits() - lecture.getSubject().getCredit() < 0) {
            throw new IllegalArgumentException("신청 가능 학점을 초과했습니다.");
        }

        // 4. 수강 신청
        enrollmentRepository.save(Enrollment.builder()
                .student(student)
                .lecture(lecture)
                .cancel(false)
                .build()
        );

        // 5. 신청 인원 증가
        lecture.incrementEnrolledCount();

        // 6. 신청 가능 학점 감소
        student.decreaseMaxCredits(lecture.getSubject().getCredit());
    }

    /**
     * 수강 신청 취소
     */
    @Transactional
    public void cancel(Integer studentId, Integer lectureId) {
        // 1. 강의 정보, 학생 정보 가져오기
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        // 2. 수강 신청 정보 가져오기
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndLectureId(studentId, lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수강 신청 정보가 존재하지 않습니다."));

        // 3. 수강 신청 취소
        enrollment.cancel();

        // 4. 신청 인원 감소
        lecture.decrementEnrolledCount();

        // 5. 신청 가능 학점 증가
        student.increaseMaxCredits(lecture.getSubject().getCredit());
    }
}
