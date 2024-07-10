package edu.allinone.sugang.service;

import edu.allinone.sugang.dto.LectureDTO;
import edu.allinone.sugang.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;

    public List<LectureDTO> getLecturesBySubjectIdAndTargetGrade(Integer subjectId, String targetGrade) {
        return lectureRepository.findBySubjectIdAndTargetGrade(subjectId, targetGrade).stream().map(lecture -> {
            LectureDTO dto = new LectureDTO();
            dto.setId(lecture.getId());
            dto.setLectureNumber(lecture.getLectureNumber());
            dto.setLectureRoom(lecture.getLectureRoom());
            dto.setLectureHours(lecture.getLectureHours());
            dto.setTotalCapacity(lecture.getTotalCapacity());
            dto.setLectureDescription(lecture.getLectureDescription());
            dto.setSubjectId(lecture.getSubject().getId());
            dto.setProfessorId(lecture.getProfessor().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}