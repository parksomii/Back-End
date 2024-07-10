package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_division", length = 20, nullable = false)
    private String subjectDivision;

    @Column(name = "target_grade", length = 20, nullable = false)
    private String targetGrade;

    @Column(name = "subject_name", length = 30, nullable = false)
    private String subjectName;

    @Column(name = "hours_per_week", nullable = false)
    private Integer hoursPerWeek;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @OneToMany(mappedBy = "subject")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
