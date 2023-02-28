package com.sparta.soomtut.image.dto.response;

import com.sparta.soomtut.image.entity.ImageEntity;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageResponse {

    private Long id;
    private String filePath;
    private String imgFullPath;

//    public ImageEntity toEntity(){
//        ImageEntity build = ImageEntity.builder()
//                .id(id)
//                .filePath(filePath)
//                .build();
//        return build;
//    }

    @Builder
    public ImageResponse(Long id, String filePath, String imgFullPath){
        this.id = id;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }
}
