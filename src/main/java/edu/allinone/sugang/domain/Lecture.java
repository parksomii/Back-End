package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lectureNumber;
    private String lectureRoom;
    private String lectureHours;
    private int totalCapacity;
    private String lectureDescription; // 강의 설명 추가

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "lecture")
    private List<Enrollment> enrollments;

    /*
    @OneToMany(mappedBy = "lecture")
    private List<Schedule> schedules;
    */
}
