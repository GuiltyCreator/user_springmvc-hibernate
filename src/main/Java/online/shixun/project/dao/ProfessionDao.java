package online.shixun.project.dao;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.HobbyModel;
import online.shixun.project.model.ProfessionModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProfessionDao {

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
    public void saveOrUpdate(ProfessionModel entity){
        getCurrentSession().saveOrUpdate(entity);
        getCurrentSession().flush();
    }

    /**
     * 删除数据的方法
     * @param entity 实体类对象
     */
    public void delete(ProfessionModel entity){
        getCurrentSession().delete(entity);
        getCurrentSession().flush();
    }

    /**
     * 获取全部数据
     * @return 数据集合
     */
    @SuppressWarnings("unchecked")
    public List<ProfessionModel> getAllProfessions(){
        List<ProfessionModel> professions = getCurrentSession().createQuery("from ProfessionModel").list();
        return professions;
    }


    /**
     * 根据职业名称查找职业的方法
     * @param professionId 职业id
     * @return 实体类对象
     */
    @Transactional(readOnly = true)
    public ProfessionModel getProfessionById(Long professionId){
       /* ProfessionModel profession=(ProfessionModel) getCurrentSession()
                .createQuery("form ProfessionModel where id=:id")
                .setParameter("id",Integer.valueOf(professionId))
                .uniqueResult();*/
        //System.out.println("ProfessionId:"+Integer.valueOf(professionId));
       ProfessionModel profession=(ProfessionModel) getCurrentSession().get(ProfessionModel.class,professionId);
        //System.out.println("ProfessionModel"+profession.getName());

        return profession;
    }

    /**
     * 获取用户分页数据
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageData<ProfessionModel> getUserPageData(int pageNo, int pageSize) {
        Query query = getCurrentSession().createQuery("from ProfessionModel");//还未查询，只是维持住状态
        int totalCount = query.list().size();//调用query.list()后，开始执行,获得总数

        //设置查询条件
        query.setFirstResult((pageNo - 1) * pageSize); // 从哪里开始取
        query.setMaxResults(pageSize); // 取几条数据

        //再次查询，这次要结果集
        List<ProfessionModel> result = query.list();
        PageData<ProfessionModel> page = new PageData<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult(result);
        page.setTotalCount(totalCount);
        return page;
    }


}
