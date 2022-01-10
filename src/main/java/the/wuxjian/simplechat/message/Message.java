package the.wuxjian.simplechat.message;

import lombok.Builder;
import lombok.Data;

/**
 * Created by wuxjian 2022/1/10
 */
@Data
@Builder
public class Message {
    private String messageId;
    private String messageType;
    private String from;
    private String to;
    private String content;
    private boolean sys;  // 系统消息
}
