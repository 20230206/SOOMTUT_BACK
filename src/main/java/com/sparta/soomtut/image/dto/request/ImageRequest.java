package com.sparta.soomtut.image.dto.request;

import com.sparta.soomtut.image.entity.ImageEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageRequest {

    private Long id;
    private String filePath;

    public ImageEntity toEntity(){
        ImageEntity build = ImageEntity.builder()
                .id(id)
                .filePath(filePath)
                .build();
        return build;
    }
    public ImageRequest(Long id, String filePath){
        this.id = id;
        this.filePath = filePath;
    }

}
