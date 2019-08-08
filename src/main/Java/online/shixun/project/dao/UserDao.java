package online.shixun.project.dao;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.UserModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库访问类
 */
@Repository
@Transactional
public class UserDao {

    // 利用 Spring 的配置文件直接生成 Hibernate 的 sessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取 Hibernate 的 Session
     * @return 返回 Session 之后就可以通过它来做各种增删改查了
     */
    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * 新增/编辑用户
     * @param entity 用户对象
     * @return
     */
    public void save(UserModel entity) {
        getCurrentSession().saveOrUpdate(entity);
        getCurrentSession().flush();
    }

    /**
     * 通过用户名获取用户对象
     * @param name 用户名
     * @return 用户对象
     */
    @Transactional(readOnly = true)
    public UserModel getUserByName(String name){
        UserModel user = (UserModel) getCurrentSession()
                .createQuery("from UserModel where name=:name")
                .setParameter("name", name)
                .uniqueResult();
        return user;
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public UserModel getUserById(Long id){
        return (UserModel) getCurrentSession().get(UserModel.class,id);
    }

    /**
     * 查询所有用户
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserModel> getAllUsers(){
        Criteria criteria=getCurrentSession().createCriteria(UserModel.class);
        criteria.add(Restrictions.like("name","%%"));
        return criteria.list();
    }
    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Long id){
        getCurrentSession().delete(getUserById(id));
        getCurrentSession().flush();
    }

    /**
     * 批量删除用户
     * @param ids
     */
    public void deleteUsers(Long[] ids){
        if(ids.length>0) {
            for (Long id : ids) {
            deleteUser(id);
            }
        }
    }

    /**
     * 获取用户分页数据
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public PageData<UserModel> getUserPageData(int pageNo, int pageSize) {
        Query query = getCurrentSession().createQuery("from UserModel");//还未查询，只是维持住状态
        int totalCount = query.list().size();//调用query.list()后，开始执行,获得总数

        //设置查询条件
        query.setFirstResult((pageNo - 1) * pageSize); // 从哪里开始取
        query.setMaxResults(pageSize); // 取几条数据

        //再次查询，这次要结果集
        List<UserModel> result = query.list();
        PageData<UserModel> page = new PageData<UserModel>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult(result);
        page.setTotalCount(totalCount);
        return page;
    }

}
