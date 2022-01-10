package the.wuxjian.simplechat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by wuxjian 2022/1/10
 */
@AllArgsConstructor
@Getter
public enum MessageType {
    TEXT("1000", "文本"),
    PICTURE("1001", "图像"),

    ;
    public String code;
    public String name;
}
