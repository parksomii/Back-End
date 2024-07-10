package edu.allinone.sugang.controller;

import edu.allinone.sugang.controller.LectureController;
import edu.allinone.sugang.dto.LectureDTO;
import edu.allinone.sugang.service.LectureService;
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

// LectureController에 대한 단위 테스트를 수행하기 위해 WebMvcTest를 사용
@WebMvcTest(LectureController.class)
public class LectureControllerTest {

    // MockMvc를 사용하여 HTTP 요청을 모의
    @Autowired
    private MockMvc mockMvc;

    // LectureService를 모의(Mock)하여 테스트에서 사용할 수 있도록 설정
    @MockBean
    private LectureService lectureService;

    // 테스트에서 사용할 강의(Lecture) 목록을 저장할 변수
    private List<LectureDTO> lectureList;

    // 각 테스트가 실행되기 전에 실행되는 설정 메서드
    @BeforeEach
    public void setUp() {
        // Mockito의 애노테이션을 초기화
        MockitoAnnotations.openMocks(this);

        // 테스트에서 사용할 강의 DTO 객체 생성
        LectureDTO lecture1 = new LectureDTO();
        lecture1.setId(1);
        lecture1.setLectureNumber("CS101");
        lecture1.setLectureRoom("101호");
        lecture1.setLectureHours("3시간");
        lecture1.setTotalCapacity(50);
        lecture1.setLectureDescription("자료구조 강의");
        lecture1.setSubjectId(1);
        lecture1.setProfessorId(1);

        LectureDTO lecture2 = new LectureDTO();
        lecture2.setId(2);
        lecture2.setLectureNumber("CS102");
        lecture2.setLectureRoom("102호");
        lecture2.setLectureHours("3시간");
        lecture2.setTotalCapacity(50);
        lecture2.setLectureDescription("운영체제 강의");
        lecture2.setSubjectId(1);
        lecture2.setProfessorId(2);

        // 강의 목록을 리스트에 추가
        lectureList = Arrays.asList(lecture1, lecture2);
    }

    // 특정 과목 ID와 학년에 해당하는 강의 목록을 반환하는 메서드를 테스트
    @Test
    public void getLecturesBySubjectIdAndTargetGrade_ShouldReturnLectures() throws Exception {
        Integer subjectId = 1;
        String targetGrade = "2학년";

        // 모의 서비스 메서드가 호출될 때 반환할 값을 설정
        when(lectureService.getLecturesBySubjectIdAndTargetGrade(subjectId, targetGrade)).thenReturn(lectureList);

        // GET 요청을 보내고 응답이 예상대로 나오는지 검증
        mockMvc.perform(get("/api/lectures/{subjectId}/{targetGrade}", subjectId, targetGrade)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 상태가 200 OK인지 확인
                .andExpect(content().json("[{'id': 1, 'lectureNumber': 'CS101', 'lectureRoom': '101호', 'lectureHours': '3시간', 'totalCapacity': 50, 'lectureDescription': '자료구조 강의', 'subjectId': 1, 'professorId': 1}, {'id': 2, 'lectureNumber': 'CS102', 'lectureRoom': '102호', 'lectureHours': '3시간', 'totalCapacity': 50, 'lectureDescription': '운영체제 강의', 'subjectId': 1, 'professorId': 2}]")); // 응답 JSON이 예상과 일치하는지 확인
    }
}
