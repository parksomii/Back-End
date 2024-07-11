package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.SubjectDTO;
import edu.allinone.sugang.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    // 특정 학부 ID에 해당하는 과목 목록을 반환
    @GetMapping("/api/subjects/{departmentId}")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByDepartmentId(@PathVariable Integer departmentId) {
        List<SubjectDTO> subjects = subjectService.getSubjectsByDepartmentId(departmentId);
        return ResponseEntity.ok(subjects);
    }

    // 강의명으로 과목을 조회
    @GetMapping("/api/subjects/search")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByLectureName(@RequestParam String lectureName) {
        List<SubjectDTO> subjects = subjectService.getSubjectsByLectureName(lectureName);
        return ResponseEntity.ok(subjects);
    }
}
