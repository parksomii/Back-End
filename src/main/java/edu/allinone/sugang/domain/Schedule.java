package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayOfWeek;
    private String firstTime;
    private String lastTime;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
