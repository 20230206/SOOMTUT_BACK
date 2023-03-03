package com.sparta.soomtut.lectureRequest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import com.sparta.soomtut.util.response.ErrorCode;
import com.sparta.soomtut.util.exception.CustomException;

@Getter
@RequiredArgsConstructor
public enum LectureState {
    ALL(0),
    NONE(1),
    IN_PROGRESS(2),
    DONE(3);

    private final int value;

    public static LectureState valueOf(int value) {
        return Arrays.asList(values()).stream()
                    .filter(item -> item.getValue() == value)
                    .findFirst().orElseThrow( () -> new CustomException(ErrorCode.CATEGORY_OUTOFINDEX));
    }
}
