package online.shixun.project.controller;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.ResponseData;
import online.shixun.project.model.UserModel;
import online.shixun.project.service.HobbyService;
import online.shixun.project.service.ProfessionService;
import online.shixun.project.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import sun.management.counter.LongArrayCounter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HobbyService hobbyService;

    @Autowired
    private ProfessionService professionService;



    /**
     * 将请求转发到user/add.jsp,供客户端看增加用户界面
     * @param model
     * @return
     */
  @RequestMapping(value = "/add")
  public String addUser( Model model){

      UserModel user=new UserModel();
      model.addAttribute("user",user);
      model.addAttribute("allHobbies",hobbyService.getAllHobbies());
      model.addAttribute("allProfessions",professionService.getAllProfession());
      return "/user/form";
  }

  @RequestMapping(value = "/{itemId}/edit")
    public String editUser(Model model, @PathVariable Long itemId){

      UserModel user=userService.getUserById(itemId);
      model.addAttribute("user",user);
      model.addAttribute("allHobbies",hobbyService.getAllHobbies());
      model.addAttribute("allProfessions",professionService.getAllProfession());

      return "/user/form";
  }
    /**
     * 保存用户
     * @param request
     * @return
     * @throws ParseException
     */
  @RequestMapping(value = "/save",method = RequestMethod.POST)
  @ResponseBody
  public ResponseData saveUser(HttpServletRequest request, MultipartRequest avatar) {
      ResponseData responseData=new ResponseData();
      UserModel userModel;
      Long id=Long.parseLong(request.getParameter("id"));
      if(id==0L){
          userModel=new UserModel();
      }
      else {
        userModel=userService.getUserById(id);
      }
      try {
          userService.saveOrUpdateUser(request,userModel,request.getServletContext().getRealPath("/"),avatar);
      }catch (Exception e){
        responseData.setError(e.getMessage());
      }
        return responseData;
  }

    /**
     * 删除用户
     * @param params
     * @return
     */
  @RequestMapping(value = "/delete")
  @ResponseBody
  public ResponseData deleteUser(@RequestParam Map<String ,String > params){
      ResponseData responseData=new ResponseData();
      try{
        userService.deleteUser(Long.parseLong(params.get("id")));
      }catch (Exception e){
          responseData.setError(e.getMessage());
      }
      return responseData;

  }

    /**
     * 批量删除
     * @param request
     * @return
     */
  @RequestMapping("/delete/batch")
  @ResponseBody
  public ResponseData deleteUsers(HttpServletRequest request){
        ResponseData responseData=new ResponseData();
        try{
            userService.deleteUsers(request);
        }catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }



    /**
     * 查询并分页显示所有的用户信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
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

    /**
     * 将请求转发到password.jsp中
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}/password/edit")
    public String toPassWordPage(Model model,@PathVariable Long userId){
        UserModel user=userService.getUserById(userId);
        model.addAttribute("user",user);
       return "/user/password";
    }

    @RequestMapping(value = "/password/update")
    @ResponseBody
    public ResponseData updatePassword(@RequestParam Map<String,String> params){
        ResponseData responseData=new ResponseData();

        Set<Map.Entry<String,String>> s1 = params.entrySet();
        System.out.println("开始遍历params");
        for(Iterator<Map.Entry<String,String>> it = s1.iterator();it.hasNext();) {
            Map.Entry<String, String> me = it.next();
            String key = me.getKey();
            String value = me.getValue();
            System.out.println(key+":"+value);
        }

        try {
            userService.updatePassword(params);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    /**
     * 导出用户
     * @return
     */
    @RequestMapping(value = "/export")
    public void exportUser(HttpServletResponse response){

        try {
            String value = "UsersExcel";

            // 获取所有需要导出的客户信息
            List<UserModel> users = userService.getAllUsers();

            System.out.println("输出user:");
            for(UserModel user:users){
                System.out.println("姓名:"+user.getName());
            }

            // 获取工作文档对象
            Workbook wb = userService.export(value, users);
            // 设置发送到客户端的响应的内容类型
            response.setContentType("application/vnd.ms-excel");
            // 设置下载文件名称
            response.setHeader("Content-disposition", "attachment;filename=" + value + ".xlsx");
            // 获取输出流
            OutputStream ouputStream = new BufferedOutputStream(response.getOutputStream());
            // 下载文件(写输出流)
            wb.write(ouputStream);
            // 刷新流
            ouputStream.flush();
            // 关闭流
            ouputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
