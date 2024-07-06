package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "student_password")
    private String studentPassword;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "max_credits")
    private int maxCredits;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}
