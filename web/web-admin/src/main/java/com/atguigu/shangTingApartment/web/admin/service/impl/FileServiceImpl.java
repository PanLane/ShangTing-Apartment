package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.shangTingApartment.common.minio.MinioProperties;
import com.atguigu.shangTingApartment.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    MinioClient minioClient;
    @Autowired
    MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //判断桶是否存在
        boolean is_exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
        if(!is_exist){
            //创建桶
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            //设置桶权限
            //设置该桶的访问权限
            String policy = """
                        {
                            "statement" : [{
                                "Action" : "s3:GetObject",
                                "Effect" : "Allow",
                                "Principal" : "*",
                                "Resource" : "arn:aws:s3:::%s/*"
                            }],
                            "Version" : "2012-10-17"
                        }
                        """.formatted(minioProperties.getBucket());
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucket()).config(policy).build());
        }
        //创建对象名
        String objectName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" +
                "uuid"+"-" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        //上传文件
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(objectName)
                .stream(file.getInputStream(),file.getSize(),-1)
                .contentType(file.getContentType())
                .build());
        //拼接路径
        return String.join("/",minioProperties.getEndpoint(),minioProperties.getBucket(),objectName);
    }
}
