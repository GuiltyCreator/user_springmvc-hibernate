package online.shixun.project.controller;

import online.shixun.project.model.ResponseData;
import online.shixun.project.model.UserModel;
import online.shixun.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 负责将客户端请求转发到index.jsp，供客户端看主要页面
     */
    @RequestMapping(value = "/")
    public String toLogin(Model model){
            model.addAttribute("user",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index";
    }

/*
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public ResponseData getDetailUser(){
        ResponseData responseData=new ResponseData();
        try {
            userService.getUserByName();
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }
*/


    /**
     * 负责将客户端请求转发到login.jsp，供客户端看登录页面
     */
    @RequestMapping(value = "login")
    public String doLogin(Model model,
                          @RequestParam(value = "error", required = false) String error,
                          HttpServletRequest request) {
        if (error!=null) {
            model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        return "/login";
    }

    /**
     * 自定义错误类型
     * @param request
     * @param key
     * @return
     */
    private String getErrorMessage(HttpServletRequest request, String key){
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error=exception.getMessage();
        return error;
    }

    /**
     * 判断用户是不是通过remember me方式登录，参考
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
  /*
        private boolean isRememberMeAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }
*/
}
