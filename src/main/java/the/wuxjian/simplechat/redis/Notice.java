package the.wuxjian.simplechat.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import the.wuxjian.simplechat.message.Message;

/**
 * Created by wuxjian 2022/1/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private String type;    // 类型
    private Object data;    // 数据


    public static Notice login(String uid) {
        return Notice.builder()
                .type(NoticeTypeEnum.LOGIN.code)
                .data(uid)
                .build();
    }

    public static Notice logout(String uid) {
        return Notice.builder()
                .type(NoticeTypeEnum.LOGOUT.code)
                .data(uid)
                .build();
    }

    public static Notice chat(Message message) {
        return Notice.builder()
                .type(NoticeTypeEnum.SINGLE.code)
                .data(message)
                .build();
    }
}
