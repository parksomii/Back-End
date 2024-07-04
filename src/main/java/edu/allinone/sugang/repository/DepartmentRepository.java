package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}