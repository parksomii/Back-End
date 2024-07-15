package edu.allinone.sugang.dto.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ResponseDto<T> {
    private Integer status;
    private String message;
    private T data;

    public ResponseDto(String message, T data) {
        this.status = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
    }

    public ResponseDto(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
