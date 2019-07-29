package online.shixun.project.controller;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.UserModel;
import online.shixun.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 负责将客户端请求转发到login.jsp，供客户端看登录页面
     */
    @RequestMapping(value = "")
    public String toLogin(){
        return "login";
    }

    /**
     * 负责处理客户端的登录请求
     */
    @RequestMapping(value = "login")
    public String doLogin(UserModel user, Model model){
        // 调用service方法实现登录验证
        boolean result = userService.login(user);
        // 判断登录是否
        if(result){
            return "index";
        }else{
            model.addAttribute("loginErrorMessage", "用户名或密码错误，登录失败！");
            return "login";
        }
    }

    /**
     * 查询并分页显示所有的用户信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "user/list")
    public String listUser(Model model, HttpServletRequest request) {
        //获取指定页数（第几页）
        String page = request.getParameter("page");
        //page为空，默认查询第1页数据，import是org.springframework.util.StringUtils
        int pageNo = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        //获取用户分页数据
        PageData<UserModel> userPageData = userService.getUserPageData(pageNo, 5);
        model.addAttribute("pageInfo", userPageData);
        return "/user/list";
    }

}
