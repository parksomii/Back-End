package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findByCollegeId(Integer collegeId); //학부 데이터를 조회하는 데 필요한 findByCollegeId 메서드
}