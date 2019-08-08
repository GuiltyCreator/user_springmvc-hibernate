package online.shixun.project.dao;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.HobbyModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class HobbyDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取 Hibernate 的 Session
     * @return 返回 Session 之后就可以通过它来做各种增删改查了
     */
    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * 增加或者更新数据的方法
     * @param entity 实体类对象
     */
    public void saveOrUpdate(HobbyModel entity){
        getCurrentSession().saveOrUpdate(entity);
        getCurrentSession().flush();
    }

    /**
     * 删除数据的方法
     * @param entity 实体类对象
     */
    public void delete(HobbyModel entity){
        getCurrentSession().delete(entity);
        getCurrentSession().flush();
    }

    /**
     * 根据爱好名称查找爱好的方法
     * @param hobbyId 爱好id
     * @return 实体类对象
     */
    @Transactional(readOnly = true)
    public HobbyModel getHobbyById(Long hobbyId){
        /*HobbyModel hobby=(HobbyModel) getCurrentSession()
                .createQuery("form HobbyModel where id=:id")
                .setParameter("id",Integer.valueOf(hobbyId))
                .uniqueResult();*/
        HobbyModel hobby=(HobbyModel)getCurrentSession().get(HobbyModel.class,hobbyId);
        return hobby;
    }

    /**
     * 获取全部数据
     * @return 数据集合
     */
    @SuppressWarnings("unchecked")
    public List<HobbyModel> getAllHobbies(){
        List<HobbyModel> hobbies = getCurrentSession().createQuery("from HobbyModel").list();
        return hobbies;
    }

    /**
     * 获取用户分页数据
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageData<HobbyModel> getUserPageData(int pageNo, int pageSize) {
        Query query = getCurrentSession().createQuery("from HobbyModel");//还未查询，只是维持住状态
        int totalCount = query.list().size();//调用query.list()后，开始执行,获得总数

        //设置查询条件
        query.setFirstResult((pageNo - 1) * pageSize); // 从哪里开始取
        query.setMaxResults(pageSize); // 取几条数据

        //再次查询，这次要结果集
        List<HobbyModel> result = query.list();
        PageData<HobbyModel> page = new PageData<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult(result);
        page.setTotalCount(totalCount);
        return page;
    }


}
