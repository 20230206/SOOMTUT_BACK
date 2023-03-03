package com.sparta.soomtut.image.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageRequest {
    private Long id;
    private String filePath;

    public ImageRequest(Long id, String filePath){
        this.id = id;
        this.filePath = filePath;
    }

}
