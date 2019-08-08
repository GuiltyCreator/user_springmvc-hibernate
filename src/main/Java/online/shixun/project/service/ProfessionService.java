package online.shixun.project.service;

import online.shixun.project.dao.ProfessionDao;
import online.shixun.project.dto.PageData;
import online.shixun.project.model.ProfessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionDao dao;


    /**
     * 获取全部数据
     * @return 数据集合
     */
    public List<ProfessionModel> getAllProfession(){
        return dao.getAllProfessions();
    }

    /**
     * 查询所有职业形成的分页数据
     * @param pageNo 当前是第几页
     * @param pageSize 每页显示多少人
     * @return
     */
    public PageData<ProfessionModel> getUserPageData(int pageNo, int pageSize) {
        PageData<ProfessionModel> userPage = dao.getUserPageData(pageNo, pageSize);
        return userPage;
    }

    /**
     * 根据id找profession
     * @param id 职业id
     * @return 职业对象
     */
    public ProfessionModel getProfessionById(Long id){
        return dao.getProfessionById(id);
    }


    /**
     * 增加或者更新数据的方法
     * @param params
     */
    public void saveOrUpdate(Map<String ,String > params){
        Long id=Long.parseLong(params.get("id"));
        ProfessionModel professionModel;
        if(id==0L){
            professionModel=new ProfessionModel();
        }
        else {
            professionModel=getProfessionById(id);
        }
        professionModel.setName(params.get("name"));
        dao.saveOrUpdate(professionModel);
    }

    /**
     * 删除爱好
     * @param professionId
     */
    public void deleteProfession(Long professionId){
        dao.delete(getProfessionById(professionId));
    }

}
