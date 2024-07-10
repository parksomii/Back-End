package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.global.ResponseDto;
import edu.allinone.sugang.dto.request.EnrollmentRequestDto;
import edu.allinone.sugang.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * 수강 신청 하기
     */
    @PostMapping("/enroll")
    public ResponseDto<?> enroll(
            @RequestBody EnrollmentRequestDto requestDto
    ) {
        ResponseDto<?> responseDto = enrollmentService.enroll(en);
    }

}
