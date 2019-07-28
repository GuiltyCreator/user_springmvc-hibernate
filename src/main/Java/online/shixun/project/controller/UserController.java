package online.shixun.project.controller;

import online.shixun.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

@RequestMapping(value = "hello")
    public String hello(){
    return userService.test();
}

}
