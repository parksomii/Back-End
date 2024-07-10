package edu.allinone.sugang.dto.global;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Controller에서 최종적으로 내보내는 Dto에 대한 형태를 통일한다.
 * 응답 시 처리 결과 뿐만이 아닌 status, message, timestamp에 대한 값을 추가로 내보낸다
 */
@NoArgsConstructor
@Getter
public class ResponseDto<T> {
    private Integer status;
    private String message;
    private T data;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder
    public ResponseDto(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        try{
            return HttpStatus.valueOf(status);
        }catch (IllegalArgumentException e){
            return HttpStatus.OK;
        }
    }
}
