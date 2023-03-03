package com.sparta.soomtut.image.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.sparta.soomtut.lecture.service.LectureService;
import com.sparta.soomtut.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.profiledir}")
    private String profiledir;
    @Value("${cloud.aws.s3.postdir}")
    private String postdir;

    public static final String CLOUD_FRONT_DOMAIN_NAME = "https://d14tc44lwo36do.cloudfront.net/";
    private final MemberService memberService;
    private final LectureService lectureService;

    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    @Transactional
    public String uploadProfile(Long memberId, MultipartFile file) throws IOException{
        var member = memberService.getMemberById(memberId);
        String key = member.getImage().substring(38);

        if(key.length() > 0){
             boolean isExistObject = s3Client.doesObjectExist(bucket, key);
             if (isExistObject) {
                 s3Client.deleteObject(bucket, key);
             }
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = profiledir + "/"  + date.format(new Date())+ "-" + file.getOriginalFilename();
        member.updateProfileImage(CLOUD_FRONT_DOMAIN_NAME + fileName);
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(),null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

    @Transactional
    public String uploadLectureImage(Long lectureId, MultipartFile file) throws IOException{
        var lecture = lectureService.getLectureById(lectureId);
        String key = lecture.getImage().substring(38);

        if(key.length() > 0){
            boolean isExistObject = s3Client.doesObjectExist(bucket, key);

            if (isExistObject) {
                s3Client.deleteObject(bucket, key);
            }
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = postdir + "/" + date.format(new Date()) + "-" + file.getOriginalFilename();

        lecture.updateLectureImage(CLOUD_FRONT_DOMAIN_NAME + fileName);

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(),null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

}

