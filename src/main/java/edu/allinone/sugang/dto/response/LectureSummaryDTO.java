package edu.allinone.sugang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

/**
 * 강의 명으로 조회 시 강의 목록 반환 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureSummaryDTO {
    private Integer lectureId;
    private String subjectName;
    private List<LectureTimeDTO> lectureTimes;
}
