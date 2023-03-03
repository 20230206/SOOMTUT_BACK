package com.sparta.soomtut.image.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageResponse {
    private Long id;
    private String filePath;
    private String imgFullPath;

    @Builder
    public ImageResponse(Long id, String filePath, String imgFullPath){
        this.id = id;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }

}
