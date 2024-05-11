package com.demo.controller;

import com.demo.model.Video;
import com.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuq
 * @Time 2022-8-4 16:56
 * @Description
 */
@RestController
@RequestMapping("api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    // http://localhost:9000/api/v1/video/find_by_id?videoId=30
    @RequestMapping("find_by_id")
    public Object findById(int videoId, HttpServletRequest httpRequest){
        Video video = videoService.findById(videoId);
        String serverInfo = httpRequest.getServerName() + ":"+ httpRequest.getServerPort();
        video.setServerInfo(serverInfo);
        return video;
    }

    /**
     * 这里是接收传入过来的参数，使用的注解可以使用 @RequestMapping("saveByFeign") 或者 @PostMapping("saveByFeign")
     * @param video Video
     * @return 返回值
     */
    @PostMapping("saveByFeign")
    public int saveByFeign(@RequestBody Video video){
        System.out.println("========>"+video.getTitle());
        return 1;
    }
}

