package the.wuxjian.simplechat.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wuxjian 2022/1/12
 */
@RestController
public class TestController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/sendMsg")
    public String send() {
        redisTemplate.convertAndSend("chat", "hello message");
        return "success";
    }
}
