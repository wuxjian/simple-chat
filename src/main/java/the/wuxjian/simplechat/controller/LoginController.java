package the.wuxjian.simplechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import the.wuxjian.simplechat.dto.User;
import the.wuxjian.simplechat.service.UserService;

/**
 * Created by wuxjian 2022/1/10
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public User login(String name) {
        return userService.login(name);
    }

}
