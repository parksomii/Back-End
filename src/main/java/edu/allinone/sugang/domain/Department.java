package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "department")
public class Department {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "department_name", length = 20, nullable = false)
    private String departmentName;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "department")
    private List<Student> students;

    @OneToMany(mappedBy = "department")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @OneToMany(mappedBy = "department")
    private List<Subject> subjects;
}
