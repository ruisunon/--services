package com.rkdevblog.fileservice.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

@Service
@Slf4j
public class S3FileService {

    private static final String FILE_DOWNLOAD_ERROR = "Error while downloading file";

    private final AmazonS3 s3Client;
    private final String bucketName;

    @Autowired
    public S3FileService(AmazonS3 s3Client, @Value("${aws.bucket.name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    /**
     * Upload file to s3 bucket
     *
     * @param file     file
     * @param fileName fileName
     */
    public void uploadFile(MultipartFile file, String fileName) {
        try {
            s3Client.putObject(bucketName, fileName, file.getInputStream(), getMetaData(file));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AmazonClientException("Error occurred while uploading to s3");
        }
    }

    /**
     * Download a file from s3 bucket
     *
     * @param keyName keyName
     * @return byte[]
     */
    public byte[] downloadFile(final String keyName) throws NoSuchFileException {
        try {
            byte[] content;
            final S3Object s3Object = s3Client.getObject(bucketName, keyName);
            final S3ObjectInputStream stream = s3Object.getObjectContent();
            content = IOUtils.toByteArray(stream);
            s3Object.close();
            return content;
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                throw new NoSuchFileException("File Not Found");
            }
            throw new AmazonClientException(FILE_DOWNLOAD_ERROR, e);
        } catch (IOException | AmazonClientException ex) {
            throw new AmazonClientException(FILE_DOWNLOAD_ERROR, ex);
        }
    }

    /**
     * Set file metadata
     *
     * @param file file
     * @return objectMetaData
     */
    private ObjectMetadata getMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }
}
