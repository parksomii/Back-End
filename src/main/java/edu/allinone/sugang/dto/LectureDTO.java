package edu.allinone.sugang.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LectureDTO {
    private Integer id;
    private String lectureNumber;
    private String lectureRoom;
    private String lectureHours;
    private Integer totalCapacity;
    private String lectureDescription;
    private Integer subjectId;
    private Integer professorId;
}
