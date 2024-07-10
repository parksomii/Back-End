package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "subject_division")
    private String subjectDivision;

    @Column(name = "target_grade")
    private String targetGrade;

    @Column(name = "hours_per_week")
    private int hoursPerWeek;

    @Column(name = "credit")
    private int credit;

    @OneToMany(mappedBy = "subject")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
