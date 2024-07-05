package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private String subjectDivision;
    private String targetGrade;
    private int hoursPerWeek;
    private int credits;

    @OneToMany(mappedBy = "subject")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
