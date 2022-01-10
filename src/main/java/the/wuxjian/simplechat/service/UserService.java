package the.wuxjian.simplechat.service;

import org.springframework.stereotype.Service;
import the.wuxjian.simplechat.dto.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuxjian 2022/1/10
 */
@Service
public class UserService {
    private static final Map<String, User> users = new ConcurrentHashMap<>();


    public User login(String name) {
        String uid = UUID.randomUUID().toString();
        User user = User.builder().name(name).uid(uid).build();
        users.put(uid, user);
        return user;
    }

    public void logout(String uid) {
        users.remove(uid);
    }

    public Collection<User> allUser() {
        return users.values();
    }

}
