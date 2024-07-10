package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findByDepartmentIdAndTargetGrade(Integer departmentId, String targetGrade);
    //학부와 학년에 맞는 과목을 조회하는 데 필요한 findByDepartmentIdAndTargetGrade 메서드
}
