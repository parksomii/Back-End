package edu.allinone.sugang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {
    private Integer studentId;
    private Integer lectureId;
}
