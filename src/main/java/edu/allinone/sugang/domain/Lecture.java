package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_number")
    private int lectureNumber;

    @Column(name = "lecture_room")
    private String lectureRoom;

    @Column(name = "lecture_hours")
    private String lectureHours;

    @Column(name = "total_capacity")
    private int totalCapacity;

    @Column(name = "lecture_description")
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

    @OneToMany(mappedBy = "lecture")
    private List<Schedule> schedules;

}
