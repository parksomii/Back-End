package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.CollegeDTO;
import edu.allinone.sugang.service.CollegeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

@WebMvcTest(CollegeController.class)
public class CollegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollegeService collegeService;

    private List<CollegeDTO> collegeList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        CollegeDTO college1 = new CollegeDTO();
        college1.setId(1);
        college1.setCollegeName("ICT융합과학대학");

        CollegeDTO college2 = new CollegeDTO();
        college2.setId(2);
        college2.setCollegeName("공과대학");

        collegeList = Arrays.asList(college1, college2);
    }

    @Test
    public void getAllColleges_ShouldReturnColleges() throws Exception {
        when(collegeService.getAllColleges()).thenReturn(collegeList);

        mockMvc.perform(get("/api/college")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1, 'collegeName': 'ICT융합과학대학'}, {'id': 2, 'collegeName': '공과대학'}]"));
    }
}