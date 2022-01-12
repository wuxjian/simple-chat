package the.wuxjian.simplechat.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * Created by wuxjian 2022/1/12
 */
@Configuration
public class ChatMessageListenerContainer {
    @Resource
    private ChatListener chatListener;

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(chatListener);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new ChannelTopic("chat"));
        return container;
    }
}
