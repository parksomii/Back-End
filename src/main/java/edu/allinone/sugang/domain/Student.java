package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String studentNumber;

    private String studentPassword;
    private String studentName;
    private String grade;
    private int maxCredits;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

}
