package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, name = "student_number")
    private String studentNumber;

    @Column(name = "student_password")
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
