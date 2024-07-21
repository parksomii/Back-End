package edu.allinone.sugang.controller;

import edu.allinone.sugang.repository.EnrollmentRepository;
import edu.allinone.sugang.repository.LectureRepository;
import edu.allinone.sugang.repository.StudentRepository;
import edu.allinone.sugang.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@WithMockUser
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @MockBean
    private LectureRepository lectureRepository; // Mock Bean으로 변경

    @MockBean
    private StudentRepository studentRepository; // Mock Bean으로 변경

    @MockBean
    private EnrollmentRepository enrollmentRepository; // Mock Bean으로 변경

    /* ================================================================= */
    //                              수강 신청                            //
    /* ================================================================= */

    /**
     * 학생id, 과목id를 입력 받고 수강 신청 하기 테스트
     */
    @Test
    public void enrollTest() throws Exception {
        doNothing().when(enrollmentService).enroll(any(), any());

        mockMvc.perform(post("/enrollment")
                        .with(csrf()) // CSRF 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lectureId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.studentId").value(1))
                .andExpect(jsonPath("$.message").value("신청 완료"));

        verify(enrollmentService).enroll(1, 1);
    }

    /**
     * 학생id, 과목코드 입력 받고 수강 신청 하기 테스트
     */
//    @Test
//    public void enrollByCodeTest() throws Exception {
//        // enrollmentService.enroll 이 호출될 때 아무 작업도 하지 않도록 설정 (doNothing())
//        doNothing().when(enrollmentService).enroll(any(), any());
//
//        mockMvc.perform(post("/enrollment/by-code")
//                        .with(csrf()) // CSRF 토큰 추가
//                        .param("studentId", "1") // request parameter로 studentId 1 전달
//                        .param("lectureNumber", "21032-001")) // request parameter로 lectureNumber 21032-001 전달
//                .andExpect(status().isCreated()) // 응답 상태 코드가 201 Created인지 확인
//                .andExpect(jsonPath("$.data.studentId").value(1)) // 응답 JSON이 studentId 1 확인
//                .andExpect(jsonPath("$.data.lectureId").value(1)) // 응답 JSON이 lectureId 1 확인
//                .andExpect(jsonPath("$.message").value("신청 완료")); // 응답 message가 "신청 완료"를 포함하는지 확인.
//
//        verify(enrollmentService).enroll(1, 1); // enroll 메서드가 1번 호출되었는지 확인
//    }

    /**
     * 수강 신청 취소 테스트
     */
    @Test
    public void cancelTest() throws Exception {
        doNothing().when(enrollmentService).cancel(1, 1);

        mockMvc.perform(delete("/enrollment/1/1")
                        .with(csrf()) // CSRF 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)) // 본체 제거
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studentId").value(1))
                .andExpect(jsonPath("$.message").value("취소 완료"));

        verify(enrollmentService).cancel(1, 1); // cancel 메서드가 1번 호출되었는지 확인
    }


    /* ================================================================= */
    //                              강의 조회                            //
    /* ================================================================= */

    /**
     * 과목명으로 강의 조회 테스트
     */

    /* ================================================================= */
    //                              정보 갱신                            //
    /* ================================================================= */

    /**
     * 시간표 갱신 테스트
     */

    /**
     * 학점 갱신 테스트
     */
}
