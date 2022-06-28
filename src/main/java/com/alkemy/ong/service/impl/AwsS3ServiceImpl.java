package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.AwsS3Service;
import com.alkemy.ong.utils.FileUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {

    @Value("${spring.aws.s3.bucket}")
    private String bucketName;
    @Value("${spring.aws.s3.endpoint}")
    private String endPoint;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = FileUtils.convertMultiPartToFile(multipartFile);
        String fileName = FileUtils.generateFileName(multipartFile);
        uploadFileTos3bucket(fileName, file);
        file.delete();
        return FileUtils.getFileUrl(endPoint,bucketName,fileName);
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}