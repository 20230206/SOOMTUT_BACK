package com.sparta.soomtut.util.dto.request;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }

}
