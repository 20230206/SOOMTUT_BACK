package com.sparta.soomtut.dto.request;

import com.sparta.soomtut.image.entity.ImageEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageRequest {

    private Long id;
    private String title;
    private String filePath;

    public ImageEntity toEntity(){
        ImageEntity build = ImageEntity.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();
        return build;
    }
    public ImageRequest(Long id, String title, String filePath){
        this.id = id;
        this.title = title;
        this.filePath = filePath;
    }

}
