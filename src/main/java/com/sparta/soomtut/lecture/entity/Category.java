package com.sparta.soomtut.lecture.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import com.sparta.soomtut.util.exception.CustomException;
import com.sparta.soomtut.util.response.ErrorCode;

// lombok
@Getter
@RequiredArgsConstructor
public enum Category {
    ALL("전체", 0),
    SPORTS("스포츠", 1),
    DANCE("댄스", 2),
    STUDY("공부", 3),
    FOREIGN("외국어", 4),
    MUSIC("음악", 5),
    IT("IT", 6),
    DEGIGN("디자인", 7),
    COOKING("요리", 8),
    ART("미술", 9),
    EXERCISE("운동", 10);

    private final String name;
    private final int value;

    public static Category valueOf(int value) {
        return Arrays.asList(values()).stream()
                        .filter(item -> item.getValue() == value)
                        .findFirst().orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_OUTOFINDEX));
    }
}
