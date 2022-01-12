package the.wuxjian.simplechat.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import the.wuxjian.simplechat.dto.User;
import the.wuxjian.simplechat.message.Message;
import the.wuxjian.simplechat.redis.Notice;
import the.wuxjian.simplechat.service.UserService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class WsHandler implements WebSocketHandler {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //在线用户列表
    private static final Map<String, WebSocketSession> uidSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uid = (String) session.getAttributes().get("uid");
        uidSessionMap.put(uid, session);

        redisTemplate.convertAndSend("chat", JSON.toJSONString(Notice.login(uid)));
    }

    //接收socket信息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Message message = JSONUtil.toBean((String) webSocketMessage.getPayload(), Message.class);
        redisTemplate.convertAndSend("chat", JSON.toJSONString(Notice.chat(message)));
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String uid = (String) session.getAttributes().get("uid");
        log.info("连接出错:{}", uid);
        if (session.isOpen()) {
            session.close();
        }
        redisTemplate.convertAndSend("chat", JSON.toJSONString(Notice.logout(uid)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String uid = (String) session.getAttributes().get("uid");
        log.info("连接关闭:{}", uid);
        if (session.isOpen()) {
            session.close();
        }
        redisTemplate.convertAndSend("chat", JSON.toJSONString(Notice.logout(uid)));
    }


    /**
     * 发送信息给指定用户
     */
    public void sendMessage(Message message) throws IOException {
        String to = message.getTo();
        WebSocketSession session = uidSessionMap.get(to);
        if (Objects.isNull(session)) {
            log.info("不存在用户{}", to);
            return;
        }
        if (session.isOpen()) {
            TextMessage textMessage = new TextMessage(JSONUtil.toJsonStr(message));
            session.sendMessage(textMessage);
            log.info("发送给{}成功", to);
        }
    }

    // 广播用户上线
    public void broadLoginMessage(String uid) throws IOException {
        Message loginMessage = Message.loginMessage(uid);
        broadcast(loginMessage); // 广播登录消息;
    }

    // 广播用户下线
    public void broadLogoutMessage(String uid) throws IOException {
        Message loginMessage = Message.logoutMessage(uid);
        broadcast(loginMessage);
    }

    // 广播所有用户信息
    public void broadAllUserMessage() throws IOException {
        Collection<User> users = userService.allUser();
        Message allUserMessage = Message.allUserMessage(users);
        broadcast(allUserMessage);
    }


    /**
     * 广播信息
     */
    public void broadcast(Message message) throws IOException {
        Set<String> uidSet = uidSessionMap.keySet();
        for (String uid : uidSet) {
            WebSocketSession session = uidSessionMap.get(uid);
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(JSONUtil.toJsonStr(message)));
            }
        }
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void close(String uid) throws IOException {
        uidSessionMap.remove(uid);
        userService.logout(uid);

        broadLogoutMessage(uid);
        broadAllUserMessage();
    }
}
