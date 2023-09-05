package com.rkdevblog.fileservice.controller;

import com.rkdevblog.fileservice.service.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
public class S3Controller {

    private final S3FileService fileService;

    @Autowired
    public S3Controller(S3FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Upload a file to s3 bucket
     *
     * @param file     file
     * @param fileName fileName
     * @return fileName
     */
    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam String fileName) {
        try {
            fileService.uploadFile(file, fileName);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Download the file
     *
     * @param fileName fileName
     * @return ByteArrayResource
     */
    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") final String fileName) {
        try {
            final byte[] data = fileService.downloadFile(fileName);
            final ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                    .header("Cache-Control", "no-cache")
                    .body(resource);
        } catch (NoSuchFileException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }
}
