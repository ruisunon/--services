package com.dustin.youtube.youtubeclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    private static final String BUCKET_NAME = "youtube-clone-dustin";
    private final AmazonS3Client amazonS3Client;

    @Override
    public String upload(MultipartFile file) {
        //Update file to AWS S3

        //prepare a key
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = String.format("%s.%s", UUID.randomUUID(), extension);
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (Exception e) {
//            throw new Exception("An Exception Occurred while uploading file");
        }
        amazonS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);
        return amazonS3Client.getResourceUrl(BUCKET_NAME, key);
    }

    @Override
    public void deleteFile(String fileName) {

    }
}
