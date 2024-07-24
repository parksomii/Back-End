package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Enrollment;
import edu.allinone.sugang.domain.Lecture;
import edu.allinone.sugang.domain.Student;
import edu.allinone.sugang.dto.response.CreditDTO;
import edu.allinone.sugang.dto.response.LectureSummaryDTO;
import edu.allinone.sugang.dto.response.LectureTimeDTO;
import edu.allinone.sugang.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;

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

        // 2. 강의 정원이 찼는지 확인
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
        enrollmentRepository.delete(enrollment);

        // 4. 신청 인원 감소
        lecture.decrementEnrolledCount();

        // 5. 신청 가능 학점 증가
        student.increaseMaxCredits(lecture.getSubject().getCredit());
    }

    /**
     * 수강 신청 내역 조회
     */
    @Transactional
    public List<Enrollment> getEnrollments(Integer studentId) {
        // 1. 학생 정보 가져오기
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        // 2. 수강 신청 내역 조회
        return enrollmentRepository.findByStudentId(studentId);
    }

    /* ================================================================= */
    //                              강의 조회                            //
    /* ================================================================= */

    /**
     * 과목명으로 강의 조회
     */
    @Transactional
    public List<LectureSummaryDTO> getLecturesBySubjectName(String subjectName) {
        List<Lecture> lectures = lectureRepository.findBySubject_SubjectNameContaining(subjectName); // 과목명으로 강의 조회
        return lectures.stream()
                .map(lecture -> LectureSummaryDTO.builder()
                        .lectureId(lecture.getId()) // 강의id 추가
                        .subjectName(lecture.getSubject().getSubjectName()) // 강의 이름 추가
                        .lectureTimes(lecture.getSchedules().stream() // 강의 시간 DTO 추가
                                .map(schedule -> LectureTimeDTO.builder()
                                        .dayOfWeek(schedule.getDayOfWeek()) // 요일 추가
                                        .firstTime(schedule.getFirstTime()) // 시작 시간 추가
                                        .lastTime(schedule.getLastTime()) // 종료 시간 추가
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 특정 강의 정보 조회
     */

    /* ================================================================= */
    //                              정보 갱신                            //
    /* ================================================================= */

    /**
     * 시간표 갱신
     */
    @Transactional
    public List<LectureTimeDTO> updateTimetable(Integer studentId) {
        // 1. 수강 신청 내역 가져오기
        List<Enrollment> enrollments = getEnrollments(studentId);

        // 2. 수강신청 내역에서 lectureId만 추출
        List<Integer> lectureIds = enrollments.stream()
                .map(enrollment -> enrollment.getLecture().getId())
                .toList();

        // 3. LectureTimeDTO 리스트 생성 후 반환
        return lectureIds.stream()
                .flatMap(lectureId -> scheduleRepository.findByLectureId(lectureId).stream())
                .map(schedule -> LectureTimeDTO.builder()
                        .dayOfWeek(schedule.getDayOfWeek())
                        .firstTime(schedule.getFirstTime())
                        .lastTime(schedule.getLastTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 학점 갱신
     */
    @Transactional
    public List<CreditDTO> updateCredits(Integer studentId) {
        // 1. 수강 신청 내역 가져오기
        List<Enrollment> enrollments = getEnrollments(studentId);

        // 2. 수강 신청 내역에서 신청 학점 리스트 생성
        List<Integer> enrolledCredits = enrollments.stream()
                .map(enrollment -> enrollment.getLecture().getSubject().getCredit())
                .toList();

        // 3. 수강 신청 학점 합 계산
        Integer totalCredit = enrolledCredits.stream()
                .reduce(0, Integer::sum);

        // 4. 학점 정보 반환
        return enrollments.stream()
                .map(enrollment -> CreditDTO.builder()
                        .studentId(studentId) // 학생 id 추가
                        .maxCredit(enrollment.getStudent().getMaxCredits()) // 최대 학점 추가
                        .EnrolledCredit(totalCredit) // 신청 학점 추가
                        .build())
                .collect(Collectors.toList());
    }
}