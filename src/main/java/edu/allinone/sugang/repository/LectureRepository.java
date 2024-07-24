package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    List<Lecture> findBySubjectIdAndTargetGrade(Integer subjectId, String targetGrade);
    List<Lecture> findBySubject_SubjectNameContaining(String subjectName);
    Optional<Lecture> findByLectureNumber(String lectureNumber); // lectureNumber로 강의 조회
}
