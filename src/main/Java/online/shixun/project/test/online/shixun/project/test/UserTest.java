package online.shixun.project.test;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.UserModel;
import online.shixun.project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:applicationContext.xml" })
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() {
        UserModel user = new UserModel();
        user.setName("test");
        user.setPassword("123456");
        user.setGender(UserModel.Gender.男);
        user.setBirthday(new Date());
        user.setProfession("学生");
        user.setEmail("test@innovaee.com");
        Long id = userService.saveUser(user);
        System.out.println("新增用户的id为:" + id);
    }

    @Test
    public void testLogin() {
        UserModel user = new UserModel();
        user.setName("innovaee");
        user.setPassword("abcd");
        boolean result = userService.login(user);
        System.out.println("测试登录结果:" + result);
    }

    @Test
    public void testGetUserPageData() {
        PageData<UserModel> userPage = userService.getUserPageData(1, 5);
        System.out.println("获取该分页用户为：" + userPage.getResult().size());
    }

}