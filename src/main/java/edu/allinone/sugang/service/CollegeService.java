package edu.allinone.sugang.service;

import edu.allinone.sugang.dto.CollegeDTO;
import edu.allinone.sugang.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // 이 클래스가 서비스 레이어의 빈으로 등록됨을 나타냄
public class CollegeService {

    @Autowired // 스프링이 CollegeRepository의 인스턴스를 자동으로 주입하도록 설정
    private CollegeRepository collegeRepository;

    // 모든 단과대학 목록을 반환하는 메서드
    public List<CollegeDTO> getAllColleges() {
        // College 엔티티 리스트를 CollegeDTO 리스트로 변환하여 반환
        return collegeRepository.findAll().stream().map(college -> {
            // College 엔티티를 CollegeDTO로 변환
            CollegeDTO dto = new CollegeDTO();
            dto.setId(college.getId()); // 단과대학 ID 설정
            dto.setCollegeName(college.getCollegeName()); // 단과대학 이름 설정
            return dto; // 변환된 DTO 반환
        }).collect(Collectors.toList()); // 변환된 DTO 리스트를 수집하여 반환
    }
}
