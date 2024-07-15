package edu.allinone.sugang.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@WithMockUser
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    public void enrollTest() throws Exception {
        doNothing().when(enrollmentService).enroll(any(), any());

        mockMvc.perform(post("/enrollment/enroll")
                        .with(csrf()) // CSRF 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lectureId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.studentId").value(1))
                .andExpect(jsonPath("$.message").value("신청 완료"));

        verify(enrollmentService).enroll(1, 1);
    }

    @Test
    public void cancelTest() throws Exception {
        doNothing().when(enrollmentService).cancel(1, 1); // 특정 학생과 강의 ID로 cancel 메서드 호출

        mockMvc.perform(delete("/enrollment/1/1")
                        .with(csrf()) // CSRF 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)) // 본체 제거
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studentId").value(1))
                .andExpect(jsonPath("$.message").value("취소 완료"));

        verify(enrollmentService).cancel(1, 1); // cancel 메서드가 1번 호출되었는지 확인
    }
}
