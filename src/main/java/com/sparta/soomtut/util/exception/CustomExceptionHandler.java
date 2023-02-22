package com.sparta.soomtut.util.exception;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.soomtut.util.response.ToResponse;

import java.util.Map;
import java.util.HashMap;


@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(CustomException e) {
        // httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", e.getErrorCode().toString());
        data.put("codeNumber", e.getErrorCode().getStatus());

        return ToResponse.of(data, e.getErrorCode());
    }
}
