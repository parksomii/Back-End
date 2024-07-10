package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "first_time")
    private String firstTime;

    @Column(name = "last_time")
    private String lastTime;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
