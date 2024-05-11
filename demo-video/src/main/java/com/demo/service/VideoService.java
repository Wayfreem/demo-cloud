package com.demo.service;

import com.demo.mapper.VideoMapper;
import com.demo.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuq
 * @Time 2022-8-4 16:56
 * @Description
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    public Video findById(int videoId) {
        return videoMapper.findById(videoId);
    }
}
