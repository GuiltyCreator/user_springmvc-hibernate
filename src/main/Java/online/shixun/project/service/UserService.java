package online.shixun.project.service;

import online.shixun.project.dao.UserDao;
import online.shixun.project.dto.PageData;
import online.shixun.project.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /*public String test(){
        return "hello";
    }*/

    public long saveUser(UserModel user){
        return userDao.save(user);
    }

    /**
     * 登录验证的业务逻辑方法
     *
     * @param userModel
     * @return
     */
    public boolean login(UserModel userModel) {
        UserModel user = userDao.getUserByName(userModel.getName());
        // 判断登录是否成功
        if (user != null && userModel.getPassword().equals(user.getPassword())) {
            // 登录成功
            return true;
        }
        // 登录失败
        return false;
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
}

