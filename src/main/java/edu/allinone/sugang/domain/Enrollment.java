package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    private boolean cancel;

    @Builder
    public Enrollment(Student student, Lecture lecture, boolean cancel) {
        this.student = student;
        this.lecture = lecture;
        this.cancel = cancel;
    }
}
