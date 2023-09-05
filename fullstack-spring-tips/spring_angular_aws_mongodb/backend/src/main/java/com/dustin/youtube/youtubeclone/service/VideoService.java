package com.dustin.youtube.youtubeclone.service;


import com.dustin.youtube.youtubeclone.dto.UploadVideoResponse;
import com.dustin.youtube.youtubeclone.dto.VideoDto;
import com.dustin.youtube.youtubeclone.model.Video;
import com.dustin.youtube.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String url = s3Service.upload(multipartFile);
        var video = new Video();
        video.setUrl(url);
//        Objects.requireNonNull(userId);
//        video.setUserId(userId);
        videoRepository.save(video);
        return new UploadVideoResponse(video.getId(), url);
    }

    public VideoDto editVideoMetadata(VideoDto videoMetaDataDto) {
        var video = getVideoById(videoMetaDataDto.getVideoId());
        video.setTitle(videoMetaDataDto.getVideoName());
        video.setDescription(videoMetaDataDto.getDescription());
        video.setUrl(videoMetaDataDto.getUrl());
        // Ignore Channel ID as it should not be possible to change the Channel of a Video
        video.setTags(videoMetaDataDto.getTags());
        video.setVideoStatus(videoMetaDataDto.getVideoStatus());
        // View Count is also ignored as its calculated independently
        videoRepository.save(video);
        return videoMetaDataDto;
//        return videoMapper.mapToDto(video);
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var video = getVideoById(videoId);
        String url = s3Service.upload(file);
        video.setThumbnailUrl(url);
        videoRepository.save(video);
        return url;
    }


    private Video getVideoById(String id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Video with ID - " + id));
    }

}
