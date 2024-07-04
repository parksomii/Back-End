package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
