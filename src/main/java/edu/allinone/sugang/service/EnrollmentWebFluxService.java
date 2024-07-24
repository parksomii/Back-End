package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Enrollment;
import edu.allinone.sugang.domain.Lecture;
import edu.allinone.sugang.domain.Student;
import edu.allinone.sugang.dto.global.ResponseDTO;
import edu.allinone.sugang.repository.EnrollmentRepository;
import edu.allinone.sugang.repository.LectureRepository;
import edu.allinone.sugang.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class EnrollmentWebFluxService {

    // Repository 및 Redis 템플릿 주입
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;
    private final ReactiveStringRedisTemplate redisTemplate;

    // 대기열 키 접두사와 최대 동시 수강신청 처리 수 상수 정의
    private static final String WAITING_LIST_PREFIX = "waiting:list:";
    private static final int MAX_CONCURRENT_ENROLLMENTS = 1000;  // 최대 동시 수강신청 처리 수

    // 현재 동시 수강신청 처리 수를 관리하는 원자 변수
    private final AtomicInteger currentConcurrentEnrollments = new AtomicInteger(0);

    /**
     * 수강신청 처리 메서드
     *
     * @param studentId 학생 ID
     * @param lectureId 강의 ID
     * @return Mono<ResponseDTO<?>> 응답 DTO를 포함한 Mono
     */
    public Mono<ResponseDTO<?>> enroll(Integer studentId, Integer lectureId) {
        String waitingListKey = WAITING_LIST_PREFIX + lectureId;  // 대기열 키 생성
        String userLectureKey = studentId + ":" + lectureId;  // 학생 ID와 강의 ID를 결합하여 고유 키 생성
        long currentTime = System.currentTimeMillis();  // 현재 시간

        return redisTemplate.opsForZSet().size(waitingListKey)  // 대기열 크기 확인
                .flatMap(size -> {
                    if (size >= MAX_CONCURRENT_ENROLLMENTS) {  // 최대 동시 수강신청 처리 수 초과 시
                        return redisTemplate.opsForZSet().add(waitingListKey, userLectureKey, currentTime)  // 대기열에 추가
                                .then(Mono.just(new ResponseDTO<>(HttpStatus.OK.value(), "대기열에 추가되었습니다.")));
                    } else {  // 바로 수강신청 가능 시
                        currentConcurrentEnrollments.incrementAndGet();
                        return processEnrollment(studentId, lectureId)  // 수강신청 처리
                                .then(Mono.just(new ResponseDTO<>(HttpStatus.CREATED.value(), "신청 완료")))
                                .doFinally(signalType -> {
                                    currentConcurrentEnrollments.decrementAndGet();  // 완료 후 동시 수강신청 처리 수 감소
                                    handleNextInQueue(lectureId);  // 다음 대기열 처리
                                });
                    }
                });
    }

    /**
     * 수강신청 실제 처리 메서드
     *
     * @param studentId 학생 ID
     * @param lectureId 강의 ID
     * @return Mono<Void>
     */
    private Mono<Void> processEnrollment(Integer studentId, Integer lectureId) {
        return Mono.fromRunnable(() -> {
            // 강의 및 학생 정보 조회
            Lecture lecture = lectureRepository.findById(lectureId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

            // 신청 가능 학점 초과 확인
            if (student.getMaxCredits() - lecture.getSubject().getCredit() < 0) {
                throw new IllegalArgumentException("신청 가능 학점을 초과했습니다.");
            }

            // 수강신청 저장
            enrollmentRepository.save(Enrollment.builder()
                    .student(student)
                    .lecture(lecture)
                    .build());

            // 신청 인원 및 학점 업데이트
            lecture.incrementEnrolledCount();
            student.decreaseMaxCredits(lecture.getSubject().getCredit());
        }).then();
    }

    /**
     * 다음 대기열 처리 메서드
     *
     * @param lectureId 강의 ID
     */
    private void handleNextInQueue(Integer lectureId) {
        String waitingListKey = WAITING_LIST_PREFIX + lectureId;  // 대기열 키 생성
        redisTemplate.opsForZSet().popMin(waitingListKey)  // 대기열에서 다음 학생 가져오기
                .flatMap(waitingStudentId -> {
                    if (waitingStudentId != null) {
                        // 학생 ID와 강의 ID 추출
                        String[] ids = waitingStudentId.getValue().split(":");
                        Integer nextStudentId = Integer.valueOf(ids[0]);
                        Integer nextLectureId = Integer.valueOf(ids[1]);
                        return enroll(nextStudentId, nextLectureId).then();  // 다음 학생 수강신청 처리
                    } else {
                        return Mono.empty();
                    }
                }).subscribe();
    }

    /**
     * 대기 시간 조회 메서드
     *
     * @param studentId 학생 ID
     * @param lectureId 강의 ID
     * @return Mono<Long> 대기 시간 (초 단위)
     */
    public Mono<Long> getWaitingTime(Integer studentId, Integer lectureId) {
        String waitingListKey = WAITING_LIST_PREFIX + lectureId;  // 대기열 키 생성
        String userLectureKey = studentId + ":" + lectureId;  // 학생 ID와 강의 ID를 결합하여 고유 키 생성
        return redisTemplate.opsForZSet().rank(waitingListKey, userLectureKey)  // 대기열에서의 순위 조회
                .flatMap(rank -> Mono.just(rank * 10));  // 대기시간을 10초씩 계산
    }
}
