package edu.allinone.sugang.service;

import edu.allinone.sugang.dto.SubjectDTO;
import edu.allinone.sugang.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectDTO> getSubjectsByDepartmentId(Integer departmentId) {
        return subjectRepository.findByDepartmentId(departmentId).stream().map(subject -> {
            SubjectDTO dto = new SubjectDTO();
            dto.setId(subject.getId());
            dto.setSubjectName(subject.getSubjectName());
            dto.setSubjectDivision(subject.getSubjectDivision());
            dto.setTargetGrade(subject.getTargetGrade());
            dto.setHoursPerWeek(subject.getHoursPerWeek());
            dto.setCredit(subject.getCredit());
            dto.setDepartmentId(subject.getDepartment().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
