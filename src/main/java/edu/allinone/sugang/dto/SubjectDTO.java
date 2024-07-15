package edu.allinone.sugang.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectDTO {
    private Integer id;
    private String subjectName;
    private String subjectDivision;
    private String targetGrade;
    private Integer hoursPerWeek;
    private Integer credit;
    private Integer departmentId;
}
