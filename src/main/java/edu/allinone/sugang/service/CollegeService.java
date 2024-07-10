package edu.allinone.sugang.service;

import edu.allinone.sugang.dto.CollegeDTO;
import edu.allinone.sugang.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegeService {
    @Autowired
    private CollegeRepository collegeRepository;

    public List<CollegeDTO> getAllColleges() {
        return collegeRepository.findAll().stream().map(college -> {
            CollegeDTO dto = new CollegeDTO();
            dto.setId(college.getId());
            dto.setCollegeName(college.getCollegeName());
            return dto;
        }).collect(Collectors.toList());
    }
}
