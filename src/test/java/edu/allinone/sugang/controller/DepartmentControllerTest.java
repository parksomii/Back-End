package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.DepartmentDTO;
import edu.allinone.sugang.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// DepartmentController에 대한 단위 테스트를 수행하기 위해 WebMvcTest를 사용
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    // MockMvc를 사용하여 HTTP 요청을 모의
    @Autowired
    private MockMvc mockMvc;

    // DepartmentService를 모의(Mock)하여 테스트에서 사용할 수 있도록 설정
    @MockBean
    private DepartmentService departmentService;

    // 테스트에서 사용할 학과(Department) 목록을 저장할 변수
    private List<DepartmentDTO> departmentList;

    // 각 테스트가 실행되기 전에 실행되는 설정 메서드
    @BeforeEach
    public void setUp() {
        // Mockito의 애노테이션을 초기화
        MockitoAnnotations.openMocks(this);

        // 테스트에서 사용할 학과 DTO 객체 생성
        DepartmentDTO department1 = new DepartmentDTO();
        department1.setId(1); // 학과 객체의 ID 설정
        department1.setDepartmentName("컴퓨터공학과"); // 학과 객체의 이름을 "컴퓨터공학과"로 설정
        department1.setCollegeId(1); // 학과 객체가 속한 단과대학의 ID를 설정 (예: 1번 단과대학)

        DepartmentDTO department2 = new DepartmentDTO();
        department2.setId(2);
        department2.setDepartmentName("기계공학과");
        department2.setCollegeId(1);

        // 학과 목록을 리스트에 추가
        departmentList = Arrays.asList(department1, department2);
    }

    // 특정 단과대학 ID에 해당하는 학과 목록을 반환하는 메서드를 테스트
    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // 인증된 사용자 모의
    public void getDepartmentsByCollegeId_ShouldReturnDepartments() throws Exception {
        Integer collegeId = 1; // 테스트할 단과대학 ID를 설정

        // 모의 서비스 메서드가 호출될 때 반환할 값을 설정
        when(departmentService.getDepartmentsByCollegeId(collegeId)).thenReturn(departmentList);

        // GET 요청을 보내고 응답이 예상대로 나오는지 검증
        mockMvc.perform(get("/api/departments/{collegeId}", collegeId)
                        .contentType(MediaType.APPLICATION_JSON)) // 요청의 Content-Type을 JSON으로 설정
                .andExpect(status().isOk()) // HTTP 상태가 200 OK인지 확인
                .andExpect(content().json("[{'id': 1, 'departmentName': '컴퓨터공학과', 'collegeId': 1}, {'id': 2, 'departmentName': '기계공학과', 'collegeId': 1}]")); // 응답 JSON이 예상과 일치하는지 확인
    }
}
