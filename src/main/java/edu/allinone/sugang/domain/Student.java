package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "student_number", length = 30, nullable = false)
    private String studentNumber;

    @Column(name = "student_password", length = 100, nullable = false)
    private String studentPassword;

    @Column(name = "student_name", length = 20, nullable = false)
    private String studentName;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "max_credits")
    private int maxCredits;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}
