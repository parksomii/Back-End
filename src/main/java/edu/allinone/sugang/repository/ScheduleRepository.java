package edu.allinone.sugang.repository;

import edu.allinone.sugang.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
    // lectureId로 Schedule 조회
    List<Schedule> findByLectureId(Integer lectureId);
}
