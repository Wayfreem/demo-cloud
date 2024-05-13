package com.demo.controller;

import com.demo.model.Video;
import com.demo.model.VideoOrder;
import com.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/video_order")
@RefreshScope
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("com.demo.service.VideoService")
    @Autowired
    private VideoService videoService;

    @Value("${video.title}")
    private String videoTitle;

    @RequestMapping("/configTest")
    private Map configTest(HttpServletRequest httpRequest) {
        String serverInfo = httpRequest.getServerName() + ":" + httpRequest.getServerPort();
        Map m = new HashMap();
        m.put("videoTitle", videoTitle);
        m.put("serverInfo", serverInfo);
        return m;
    }

    @RequestMapping("/save")
    public Object save(int videoId) {

        // 使用 负载均衡策略
        // 这里通过服务名去调用，这里的服务名就是在 nacos 上面的服务名称
        Video video = restTemplate.getForObject("http://demo-video/api/v1/video/find_by_id?videoId=" + videoId, Video.class);

        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setServerInfo(video.getServerInfo());

        return videoOrder;
    }

    // 通过 FeignClient 访问
    // http://localhost:9000/api/v1/video_order/findById?videoId=30
    @RequestMapping("/findById")
    public Object findById(int videoId) {

        Video video = videoService.findById(videoId);

        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setServerInfo(video.getServerInfo());
        return videoOrder;
    }


    int temp = 0;

    /**
     * 用于测试熔断、降级
     *
     * @return map
     */
    @RequestMapping("list")
    private Map list(HttpServletRequest httpRequest) {

        temp++;

        if (temp % 3 == 0) {
            throw new RuntimeException("服务异常");
        }

        String serverInfo = httpRequest.getServerName() + ":" + httpRequest.getServerPort();
        return Map.of("title", "测试返回数据", "name", "返回名称", "serverInfo", serverInfo);
    }


    @RequestMapping("gateway")
    private Map gateway(HttpServletRequest httpRequest) {
        String serverInfo = httpRequest.getServerName() + ":" + httpRequest.getServerPort();
        return Map.of("title", "测试返回数据", "name", "返回名称", "serverInfo", serverInfo);
    }

}
