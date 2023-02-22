package com.sparta.soomtut.dto.request;

import com.sparta.soomtut.entity.ImageEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {

    private Long id;

    private String title;

    private String filePath;

    private String imgFullPath;

    public ImageEntity toEntity(){
        ImageEntity build = ImageEntity.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Long id, String title, String filePath, String imgFullPath){
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }
}
