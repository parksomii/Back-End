package edu.allinone.sugang.service;

import edu.allinone.sugang.domain.Subject;
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

    // 특정 학부 ID에 해당하는 과목 목록을 반환하는 메서드
    public List<SubjectDTO> getSubjectsByDepartmentId(Integer departmentId) {
        // Department 엔티티 리스트를 SubjectDTO 리스트로 변환하여 반환
        List<Subject> subjects = subjectRepository.findByDepartmentId(departmentId);
        return subjects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 강의명으로 과목을 조회하는 메서드
    public List<SubjectDTO> getSubjectsByLectureName(String lectureName) {
        // Subject 엔티티 리스트를 SubjectDTO 리스트로 변환하여 반환
        List<Subject> subjects = subjectRepository.findBySubjectNameContaining(lectureName);
        return subjects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Subject 엔티티를 SubjectDTO로 변환하는 메서드
    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO(); // 새로운 SubjectDTO 객체 생성
        dto.setId(subject.getId()); // 과목 ID 설정
        dto.setSubjectName(subject.getSubjectName()); // 과목 이름 설정
        dto.setSubjectDivision(subject.getSubjectDivision()); // 과목 구분 설정
        dto.setTargetGrade(subject.getTargetGrade()); // 대상 학년 설정
        dto.setHoursPerWeek(subject.getHoursPerWeek()); // 주당 강의 시간 설정
        dto.setCredit(subject.getCredit()); // 학점 설정
        dto.setDepartmentId(subject.getDepartment().getId()); // 과목이 속한 학부 ID 설정
        return dto; // 변환된 DTO 반환
    }
}
