package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Schedule {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "day_of_week", length = 20, nullable = false)
    private String dayOfWeek;

    @Column(name = "first_time", nullable = false)
    private String firstTime;

    @Column(name = "last_time", nullable = false)
    private String lastTime;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;
}
