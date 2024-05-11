package com.demo.service;

import com.demo.model.Video;
import com.demo.service.fallback.VideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 这里需要填写的就是相应的服务名, 同时需要指定fallback的类, fallback 就是用来兜底使用的
@FeignClient(name = "demo-video", fallback = VideoServiceFallback.class)
public interface VideoService {

    @GetMapping(value = "/api/v1/video/find_by_id")
    Video findById(@RequestParam("videoId") int videoId);


    // 这里使用 @RequestMapping 与 @PostMapping 注解都是可以的
    @RequestMapping(value = "/api/v1/video/saveByFeign")
    int saveByFeign(@RequestBody() Video video);
}
