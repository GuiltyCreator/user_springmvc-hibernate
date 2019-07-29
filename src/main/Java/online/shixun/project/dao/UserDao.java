package online.shixun.project.dao;

import online.shixun.project.dto.PageData;
import online.shixun.project.model.UserModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库访问类
 */
@Repository
public class UserDao {

    // 利用 Spring 的配置文件直接生成 Hibernate 的 sessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取 Hibernate 的 Session
     * @return 返回 Session 之后就可以通过它来做各种增删改查了
     */
    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    /**
     * 新增用户
     * @param entity 用户对象
     * @return 新增的用户信息在数据表中的ID
     */
    public Long save(UserModel entity) {
        return (Long)getCurrentSession().save(entity);
    }

    /**
     * 通过用户名获取用户对象
     * @param name 用户名
     * @return 用户对象
     */
    public UserModel getUserByName(String name){
        UserModel user = (UserModel) getCurrentSession()
                .createQuery("from UserModel where name=:name")
                .setParameter("name", name)
                .uniqueResult();
        return user;
    }
    /**
     * 获取用户分页数据
     */
    @SuppressWarnings("unchecked")
    public PageData<UserModel> getUserPageData(int pageNo, int pageSize) {
        Query query = getCurrentSession().createQuery("from UserModel");
        int totalCount = query.list().size();
        query.setFirstResult((pageNo - 1) * pageSize); // 从哪里开始取
        query.setMaxResults(pageSize); // 取几条数据
        List<UserModel> result = query.list();
        PageData<UserModel> page = new PageData<UserModel>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult(result);
        page.setTotalCount(totalCount);
        return page;
    }

}
