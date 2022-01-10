package the.wuxjian.simplechat.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by wuxjian 2022/1/10
 */
@Data
@Builder
public class User {
    private String uid;
    private String name;
}
