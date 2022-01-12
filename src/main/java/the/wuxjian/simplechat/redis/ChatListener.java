package the.wuxjian.simplechat.redis;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import the.wuxjian.simplechat.config.WsHandler;

import javax.annotation.Resource;

/**
 * Created by wuxjian 2022/1/12
 */
@Service
@Slf4j
public class ChatListener implements MessageListener {
    @Resource
    private WsHandler wsHandler;


    @Override
    public void onMessage(Message message, byte[] pattern) {
//        System.out.println("接收数据:" + message.toString());
        try {
            String s = JSON.parse(message.toString()).toString();
            Notice notice = JSON.parseObject(s, Notice.class);
            String type = notice.getType();
            if (type.equals(NoticeTypeEnum.LOGIN.code)) {
                String uid = (String) notice.getData();
                wsHandler.broadLoginMessage(uid);
                wsHandler.broadAllUserMessage();
            } else if (type.equals(NoticeTypeEnum.LOGOUT.code)) {
                String uid = (String) notice.getData();
                wsHandler.close(uid);
            } else if (type.equals(NoticeTypeEnum.SINGLE.code)) {
                JSONObject jsonObject = (JSONObject) notice.getData();
                wsHandler.sendMessage(JSON.parseObject(jsonObject.toJSONString(), the.wuxjian.simplechat.message.Message.class));
            }
        } catch (Exception e) {
            log.error("处理消息异常", e);
        }
    }
}
