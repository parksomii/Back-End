package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;

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
