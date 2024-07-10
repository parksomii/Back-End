package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.SubjectDTO;
import edu.allinone.sugang.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{departmentId}")
    public List<SubjectDTO> getSubjectsByDepartmentId(@PathVariable Integer departmentId) {
        return subjectService.getSubjectsByDepartmentId(departmentId);
    }
}
