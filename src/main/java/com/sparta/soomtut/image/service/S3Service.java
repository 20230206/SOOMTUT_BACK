package com.sparta.soomtut.image.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.soomtut.image.dto.request.ImageRequest;

import com.sparta.soomtut.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public static final String CLOUD_FRONT_DOMAIN_NAME = "doetinf9mat8b.cloudfront.net";
    public static final String S3_URI = "https://soomtut.s3.ap-northeast-2.amazonaws.com/";

    private final MemberService memberService;

    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }
// 구현안된 코드
//    public AwsS3
//
//    private String putS3(MultipartFile file, String fileName) throws IOException {
//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(),null)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return getS3(bucket, fileName);
//    }
//
//    private String getS3(String bucket, String fileName){
//        return s3Client.getUrl(bucket, fileName).toString();
//    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    public String uploadProfile(Long memberId, MultipartFile file) throws IOException{
        var member = memberService.getMemberById(memberId);
        if(memberId != null){
            // boolean isExistObject = s3Client.doesObjectExist(bucket, String.valueOf(memberId));

            // if (isExistObject) {
            //     s3Client.deleteObject(bucket, String.valueOf(memberId));
            // }
        }
        

        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = profiledir + "/"  + date.format(new Date())+ "-" + file.getOriginalFilename();

        member.updateProfileImage(S3_URI + fileName);

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(),null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

    public String uploadPostImage(String currentFilePath, MultipartFile file) throws IOException{
        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = postdir + "/" + file.getOriginalFilename() + "-" + date.format(new Date());

        if(!"".equals(currentFilePath) && currentFilePath != null){
            boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);

            if (isExistObject) {
                s3Client.deleteObject(bucket, currentFilePath);
            }
        }

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(),null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return fileName;
    }
}

