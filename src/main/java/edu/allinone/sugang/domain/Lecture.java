package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Table(name = "lecture")
public class Lecture {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "lecture_number", length = 20, nullable = false)
    private String lectureNumber;

    @Column(name = "lecture_room", length = 20)
    private String lectureRoom;

    @Column(name = "lecture_hours", length = 10, nullable = false)
    private String lectureHours;

    @Column(name = "total_capacity", nullable = false)
    private Integer totalCapacity;

    @Column(name = "lecture_description", length = 200)
    private String lectureDescription;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "lecture")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "lecture")
    private List<Enrollment> enrollments;

}
