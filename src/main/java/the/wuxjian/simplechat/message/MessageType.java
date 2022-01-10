package the.wuxjian.simplechat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by wuxjian 2022/1/10
 */
@AllArgsConstructor
@Getter
public enum MessageType {
    LOGIN("1000", "上线"),
    LOGOUT("1001", "下线"),
    ALL_USERS("1002", "所有用户"),
    SINGLE("1003", "单聊"),
    ;
    public String code;
    public String name;
}
