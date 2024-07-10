package edu.allinone.sugang.service;

import edu.allinone.sugang.dto.DepartmentDTO;
import edu.allinone.sugang.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getDepartmentsByCollegeId(Integer collegeId) {
        return departmentRepository.findByCollegeId(collegeId).stream().map(department -> {
            DepartmentDTO dto = new DepartmentDTO();
            dto.setId(department.getId());
            dto.setDepartmentName(department.getDepartmentName());
            dto.setCollegeId(department.getCollege().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
