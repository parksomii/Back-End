package edu.allinone.sugang.controller;

import edu.allinone.sugang.dto.CollegeDTO;
import edu.allinone.sugang.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @GetMapping
    public List<CollegeDTO> getAllColleges() {
        return collegeService.getAllColleges();
    }
}