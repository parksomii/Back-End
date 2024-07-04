package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}