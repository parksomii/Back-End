package edu.allinone.sugang.dto.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ResponseDto
 * 상태, 메세지, 데이터 반환 DTO
 */
@NoArgsConstructor
@Getter
public class ResponseDto<T> {
    private Integer status;
    private String message;
    private T data;

    // 모두 반환
    public ResponseDto(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 메세지 없이 반환
    public ResponseDto(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    // 데이터 없이 반환
    public ResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
