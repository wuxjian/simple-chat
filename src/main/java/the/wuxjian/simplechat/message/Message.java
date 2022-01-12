package the.wuxjian.simplechat.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import the.wuxjian.simplechat.dto.User;

import java.util.Collection;

/**
 * Created by wuxjian 2022/1/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String messageId;
    private String messageType;
    private String from;
    private String to;
    private Object payload;


    public static Message loginMessage(String uid) {
        return Message.builder()
                .from("-1")
                .messageType(MessageType.LOGIN.code)
                .payload(uid)
                .build();
    }

    public static Message logoutMessage(String uid) {
        return Message.builder()
                .from("-1")
                .messageType(MessageType.LOGOUT.code)
                .payload(uid)
                .build();
    }

    public static Message allUserMessage(Collection<User> userList) {
        return Message.builder()
                .from("-1")
                .messageType(MessageType.ALL_USERS.code)
                .payload(userList)
                .build();
    }

}
