package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.CollegeDTO;
import edu.allinone.sugang.service.CollegeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// CollegeController에 대한 단위 테스트를 수행하기 위해 WebMvcTest를 사용
@WebMvcTest(CollegeController.class)
public class CollegeControllerTest {

    // MockMvc를 사용하여 HTTP 요청을 모의
    @Autowired
    private MockMvc mockMvc;

    // CollegeService를 모의(Mock)하여 테스트에서 사용할 수 있도록 설정
    @MockBean
    private CollegeService collegeService;

    // 테스트에서 사용할 단과대학(College) 목록을 저장할 변수
    private List<CollegeDTO> collegeList;

    // 각 테스트가 실행되기 전에 실행되는 설정 메서드
    @BeforeEach
    public void setUp() {
        // Mockito의 애노테이션을 초기화
        MockitoAnnotations.openMocks(this);

        // 테스트에서 사용할 단과대학 DTO 객체 생성
        CollegeDTO college1 = new CollegeDTO();
        college1.setId(1);
        college1.setCollegeName("ICT융합과학대학");

        CollegeDTO college2 = new CollegeDTO();
        college2.setId(2);
        college2.setCollegeName("공과대학");

        // 단과대학 목록을 리스트에 추가
        collegeList = Arrays.asList(college1, college2);
    }

    // 모든 단과대학 목록을 반환하는 메서드를 테스트
    @Test
    public void getAllColleges_ShouldReturnColleges() throws Exception {
        // 모의 서비스 메서드가 호출될 때 반환할 값을 설정
        when(collegeService.getAllColleges()).thenReturn(collegeList);

        // GET 요청을 보내고 응답이 예상대로 나오는지 검증
        mockMvc.perform(get("/api/college")
                        .contentType(MediaType.APPLICATION_JSON)) // 요청의 Content-Type 설정
                .andExpect(status().isOk()) // HTTP 상태가 200 OK인지 확인
                .andExpect(content().json("[{'id': 1, 'collegeName': 'ICT융합과학대학'}, {'id': 2, 'collegeName': '공과대학'}]")); // 응답 JSON이 예상과 일치하는지 확인
    }
}
