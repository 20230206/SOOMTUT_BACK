package com.sparta.soomtut.service.impl;

import com.sparta.soomtut.dto.request.ImageDto;
import com.sparta.soomtut.entity.ImageEntity;
import com.sparta.soomtut.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private S3Service s3Service;

    private ImageRepository imageRepository;

    public void saveImgPost(ImageDto imageDto){
        imageRepository.save(imageDto.toEntity());
    }

    public List<ImageDto> getList(){
        List<ImageEntity> imageEntityList = imageRepository.findAll();
        List<ImageDto> imageDtoList = new ArrayList<>();

        for(ImageEntity imageEntity : imageEntityList){
            imageDtoList.add(convertEntityToDto(imageEntity));
        }

        return imageDtoList;
    }

    private ImageDto convertEntityToDto(ImageEntity imageEntity){
        return ImageDto.builder()
                .id(imageEntity.getId())
                .title(imageEntity.getTitle())
                .filePath(imageEntity.getFilePath())
                .imgFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + imageEntity.getFilePath())
                .build();
    }
}
