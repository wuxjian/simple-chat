package the.wuxjian.simplechat.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by wuxjian 2022/1/12
 */
@AllArgsConstructor
@Getter
public enum NoticeTypeEnum {
    LOGIN("10", "上线"),
    LOGOUT("20", "下线"),
    SINGLE("30", "单聊"),
    ;
    public String code;
    public String name;
}
