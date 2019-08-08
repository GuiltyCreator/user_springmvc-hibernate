package online.shixun.project.service;

import online.shixun.project.dao.HobbyDao;
import online.shixun.project.dto.PageData;
import online.shixun.project.model.HobbyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HobbyService {

    @Autowired
    private HobbyDao dao;


    /**
     * 获取全部数据
     * @return 数据集合
     */
    public List<HobbyModel> getAllHobbies(){
        return dao.getAllHobbies();
    }



    /**
     * 查询所有爱好形成的分页数据
     * @param pageNo 当前是第几页
     * @param pageSize 每页显示多少人
     * @return
     */
    public PageData<HobbyModel> getUserPageData(int pageNo, int pageSize) {
        PageData<HobbyModel> userPage = dao.getUserPageData(pageNo, pageSize);
        return userPage;
    }

    /**
     * 根据爱好id找爱好
     * @param id 爱好id
     * @return 爱好对象
     */
    public HobbyModel getHobbyById(Long id){
        return dao.getHobbyById(id);
    }

    /**
     * 增加或者更新数据的方法
     * @param params
     */
    public void saveOrUpdate(Map<String ,String > params){
        Long id=Long.parseLong(params.get("id"));
        HobbyModel hobby;
        if(id==0L){
            hobby=new HobbyModel();
        }
        else {
            hobby=getHobbyById(id);
        }
        hobby.setName(params.get("name"));
        dao.saveOrUpdate(hobby);
    }

    /**
     * 删除爱好
     * @param hobbyId
     */
    public void deleteHobby(Long hobbyId){
        dao.delete(getHobbyById(hobbyId));
    }

}
