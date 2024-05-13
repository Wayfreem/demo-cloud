package com.demo.service.fallback;

import com.demo.model.Video;
import com.demo.service.VideoService;
import org.springframework.stereotype.Service;

@Service("videoServiceFallback")
public class VideoServiceFallback implements VideoService {

    @Override
    public Video findById(int videoId) {
        Video video = new Video();
        video.setTitle("熔断降级数据");
        return video;
    }

    @Override
    public int saveByFeign(Video video) {
        return 0;
    }

}
