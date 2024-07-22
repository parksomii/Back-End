package edu.allinone.sugang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수강신청 시 학점 반환 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDTO {
    private Integer studentId;
    private Integer maxCredit;
    private Integer EnrolledCredit;
}
