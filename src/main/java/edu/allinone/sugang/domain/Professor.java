package edu.allinone.sugang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "professor_name", length = 20, nullable = false)
    private String professorName;

    @Column(name = "email", length = 30)
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<Lecture> lectures;

}
