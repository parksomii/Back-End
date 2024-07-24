package edu.allinone.sugang.handler;

import edu.allinone.sugang.dto.global.ResponseDTO;
import edu.allinone.sugang.dto.request.EnrollmentDTO;
import edu.allinone.sugang.service.EnrollmentWebFluxService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EnrollmentWebFluxHandler {

    @Autowired
    private final EnrollmentWebFluxService enrollmentService;

    /**
     * 수강신청 처리 메서드
     *
     * @param request ServerRequest containing the EnrollmentDTO
     * @return Mono<ServerResponse> with status CREATED and a ResponseDTO
     */
    public Mono<ServerResponse> enroll(ServerRequest request) {
        return request.bodyToMono(EnrollmentDTO.class)  // 요청의 body를 EnrollmentDTO로 변환
                .flatMap(enrollmentDTO -> enrollmentService.enroll(enrollmentDTO.getStudentId(), enrollmentDTO.getLectureId())  // 수강신청 처리
                        .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)  // 수강신청 성공 시 CREATED 상태로 응답
                                .bodyValue(response)));
    }

    /**
     * 대기 시간 조회 메서드
     *
     * @param request ServerRequest containing studentId and lectureId as query parameters
     * @return Mono<ServerResponse> with the waiting time
     */
    public Mono<ServerResponse> getPosition(ServerRequest request) {
        String studentId = request.queryParam("studentId").orElse("");  // query parameter로부터 studentId 가져오기
        String lectureId = request.queryParam("lectureId").orElse("");  // query parameter로부터 lectureId 가져오기
        return enrollmentService.getWaitingTime(Integer.valueOf(studentId), Integer.valueOf(lectureId))  // 대기 시간 조회
                .flatMap(waitingTime -> ServerResponse.ok()  // 대기 시간 조회 성공 시 OK 상태로 응답
                        .bodyValue(new ResponseDTO<>(HttpStatus.OK.value(), waitingTime)))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)  // 에러 발생 시 INTERNAL_SERVER_ERROR 상태로 응답
                        .bodyValue(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())));
    }
}
