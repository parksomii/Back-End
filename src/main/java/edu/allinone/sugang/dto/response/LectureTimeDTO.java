package edu.allinone.sugang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 강의 요일, 시간 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureTimeDTO {
    private String dayOfWeek;
    private LocalTime firstTime;
    private LocalTime lastTime;
}
