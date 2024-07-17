package edu.allinone.sugang.controller;

import edu.allinone.sugang.domain.Enrollment;
import edu.allinone.sugang.dto.global.ResponseDto;
import edu.allinone.sugang.dto.request.EnrollmentRequestDto;
import edu.allinone.sugang.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * 수강 신청 하기
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 상태를 201 Created로 설정
    public ResponseDto<EnrollmentRequestDto> enroll(@RequestBody EnrollmentRequestDto requestDto) {
       try {
            enrollmentService.enroll(requestDto.getStudentId(), requestDto.getLectureId());
            return new ResponseDto<>(HttpStatus.CREATED.value(), "신청 완료", requestDto);
        } catch (IllegalArgumentException e) {
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestDto);
        } catch (Exception e) {
           return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), requestDto);
       }
    }

    /**
     * 수강 신청 취소 하기
     */
    @DeleteMapping("/{studentId}/{lectureId}")
    public ResponseDto<EnrollmentRequestDto> cancel(@PathVariable Integer studentId, @PathVariable Integer lectureId) {
        try {
            enrollmentService.cancel(studentId, lectureId);
            EnrollmentRequestDto requestDto = new EnrollmentRequestDto(studentId, lectureId);
            return new ResponseDto<>(HttpStatus.OK.value(), "취소 완료", requestDto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 수강 신청 내역 조회
     */
    @GetMapping("/{studentId}")
    public ResponseDto<?> getEnrollments(@PathVariable Integer studentId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getEnrollments(studentId);
            if (enrollments.isEmpty()) {
                return new ResponseDto<>(HttpStatus.OK.value(), "수강신청 내역이 없습니다.");
            } else {
                return new ResponseDto<>(HttpStatus.OK.value(), enrollments);
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
