package online.shixun.project.service;

import online.shixun.project.dao.HobbyDao;
import online.shixun.project.dao.ProfessionDao;
import online.shixun.project.dao.UserDao;
import online.shixun.project.dto.PageData;
import online.shixun.project.model.HobbyModel;
import online.shixun.project.model.ResponseData;
import online.shixun.project.model.UserModel;
import online.shixun.project.model.UserRole;
import online.shixun.project.util.ImageUtils;
import online.shixun.project.util.Poi4Excel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProfessionDao professionDao;
    @Autowired
    private HobbyDao hobbyDao;

    /**
     * 保存或者编辑用户信息
     * @param request
     * @param user
     */
    public void saveOrUpdateUser(HttpServletRequest request,UserModel user,String rootPath, MultipartRequest file) throws Exception {

        String[] hobbys= request.getParameterValues("hobby");
        Set<HobbyModel> hobby=new HashSet<>();
        if(user.getId()==0L) {
            String name=request.getParameter("name");
            if(userDao.getUserByName(name)!=null){
                throw new IllegalArgumentException("已存在相同的用户名");
            }
            user.setName(name);
            user.setPassword(request.getParameter("password"));
        }

        if(file!=null){
            System.out.println("第一步");
            MultipartFile imageFile = file.getFile("avatar");
            if (null != imageFile) {
                System.out.println("第二步");
                Map<String, Object> images = ImageUtils.saveImage(rootPath, imageFile, false);
                user.setUserAvatarImage(images.get("imageName").toString());
            }
        }

        user.setEmail(request.getParameter("email"));
        user.setBirthday( request.getParameter("birthday"));
        user.setGender(UserModel.Gender.valueOf(request.getParameter("gender")));
        user.setProfession(professionDao.getProfessionById(Long.parseLong(request.getParameter("profession"))));
        for(String item:hobbys){
            hobby.add(hobbyDao.getHobbyById(Long.valueOf(item)));
        }
        user.setHobby(hobby);


         userDao.save(user);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Long id){
        userDao.deleteUser(id);
    }

    /**
     * 批量删除用户
     * @param request
     */
    public void deleteUsers(HttpServletRequest request){
        String [] idsString=request.getParameterValues("ids");
        Long[] ids=new Long[idsString.length];
        for (int i=0;i<idsString.length;i++){
            ids[i]=Long.parseLong(idsString[i]);
        }
        userDao.deleteUsers(ids);
    }

    /**
     * 根据用户名查找用户的方法
     * @param name 用户名
     * @return 用户实例
     */
    public UserModel getUserByName(String name){
        UserModel user=userDao.getUserByName(name);
        return user;
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public UserModel getUserById(Long id){
        return userDao.getUserById(id);
    }


    /**
     * 查询所有用户形成的分页数据
     * @param pageNo 当前是第几页
     * @param pageSize 每页显示多少人
     * @return
     */
    public PageData<UserModel> getUserPageData(int pageNo, int pageSize) {
        PageData<UserModel> userPage = userDao.getUserPageData(pageNo, pageSize);
        return userPage;
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<UserModel> getAllUsers(){
        return userDao.getAllUsers();
    }

    /**
     * 更改密码
     * @param params
     */
    public void updatePassword( Map<String,String> params){

        UserModel userModel = userDao.getUserById(Long.parseLong(params.get("id")));
        String newPassword = params.get("password");
        userModel.setPassword(newPassword);
        userDao.save(userModel);

    }

    /**
     * excel导出客户信息
     *
     * @return Workbook 工作文档对象
     */
    public Workbook export(String fileName, List<UserModel> users) throws IOException {
        // excel格式是.xlsx
        String fileType = "xlsx";
        // 导出excel需要的数据格式
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        // 导出excel第一行标题数据
        ArrayList<String> listTitle = new ArrayList<>();
        // 存放标题顺序
        listTitle.add("用户名");
        listTitle.add("密码");
        listTitle.add("生日");
        listTitle.add("邮箱");
        listTitle.add("爱好");
        listTitle.add("职业");
        listTitle.add("创建时间");
        // 将标题数据放在excel数据
        list.add(listTitle);
        for (int i = 0; i < users.size(); i++) {
            // 存放excel内容数据
            ArrayList<String> listBody = new ArrayList<>();
            // 存放数据顺序和存放标题顺序对应
            listBody.add(users.get(i).getName());
            listBody.add(users.get(i).getPassword());
            listBody.add(users.get(i).getBirthday());
            listBody.add(users.get(i).getEmail());
            Set<HobbyModel> hobbies=users.get(i).getHobby();
            String hobby="";
            for(HobbyModel hobbyModel:hobbies){
                hobby+="["+hobbyModel.getName()+"]";
            }
            listBody.add(hobby);
            listBody.add(users.get(i).getProfession().getName());
            listBody.add(users.get(i).getCreatTime());
            list.add(listBody);
        }
        // 调用公共类的导出方法
        return Poi4Excel.writer(fileName, fileType, list);
    }



}

