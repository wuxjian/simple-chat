package the.wuxjian.simplechat.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import the.wuxjian.simplechat.dto.User;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by wuxjian 2022/1/10
 */
@Service
public class UserService {

    private static final String USER_KEY = "users";

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    public User login(String name) {
        String uid = UUID.randomUUID().toString();
        User user = User.builder().name(name).uid(uid).build();
        redisTemplate.opsForHash().put(USER_KEY, uid, JSONUtil.toJsonStr(user));

        return user;
    }

    public void logout(String uid) {
        redisTemplate.opsForHash().delete(USER_KEY, uid);
    }

    public Collection<User> allUser() {
        Collection<Object> users = redisTemplate.opsForHash().entries(USER_KEY).values();
        if (CollectionUtil.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(i -> JSONUtil.toBean(i.toString(), User.class))
                .collect(Collectors.toList());
    }

}
