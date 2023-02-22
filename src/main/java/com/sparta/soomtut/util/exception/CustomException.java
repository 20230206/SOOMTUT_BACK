package com.sparta.soomtut.util.exception;

import com.sparta.soomtut.util.response.ErrorCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomException extends IllegalArgumentException  {
    
    private ErrorCode errorCode;

    public CustomException(ErrorCode error) {
        super(error.getMessage());
        this.errorCode = error;
    }
}
