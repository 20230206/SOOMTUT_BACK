package com.sparta.soomtut.util.response;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;


public class ToResponse {
    
    public static <T> ResponseEntity<Map<String, Object>> of(T data, ErrorCode code) {
        Map<String, Object> datas = res(data, code.getMessage());
        return ResponseEntity.status(code.getStatus()).body(datas);
    }
    
    public static <T> ResponseEntity<Map<String, Object>> of(T data, SuccessCode code) {
        Map<String, Object> datas = res(data, code.getMessage());
        return ResponseEntity.status(code.getStatus()).body(datas);
    }

    public static <T> ResponseEntity<Map<String, Object>> of(T data, ResponseCookie cookie, ErrorCode code) {
        Map<String, Object> datas = res(data, code.getMessage());
        return ResponseEntity.status(code.getStatus()).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(datas);
    }
    
    public static <T> ResponseEntity<Map<String, Object>> of(T data, ResponseCookie cookie, SuccessCode code) {
        Map<String, Object> datas = res(data, code.getMessage());
        return ResponseEntity.status(code.getStatus()).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(datas);
    }

    public static <T> ResponseEntity<Map<String, Object>> of(T data, HttpHeaders headers, SuccessCode code) {
        Map<String, Object> datas = res(data, code.getMessage());
        return ResponseEntity.status(code.getStatus()).headers(headers).body(datas);
    }

    private static <T> Map<String, Object> res(T data, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("message", message);
        return res;
    }
}
