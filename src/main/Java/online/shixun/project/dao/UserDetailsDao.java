package online.shixun.project.dao;

import online.shixun.project.model.UserAttempts;
import online.shixun.project.model.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;


@Repository
@Transactional
public class UserDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final int MAX_ATTEMPTS=5;//最大尝试次数

    /**
     * 获取 Hibernate 的 Session
     * @return 返回 Session 之后就可以通过它来做各种增删改查了
     */
    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return
     */
    private boolean isUserExists(String username) {
        boolean result = false;
        Long count = (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(*) from UserModel u where u.name =:name")
                .setParameter("name", username)
                .iterate().next();
        if (count > 0) {
            result = true;
        }
        return result;
    }


    @Transactional(noRollbackFor = LockedException.class)
    public void updateFailAttempts(String username){
        UserAttempts userAttempts = getUserAttempts(username);
        System.out.println("Attempts:"+userAttempts.getAttempts());
        if (userAttempts == null) {//如果没有记录
            if (isUserExists(username)) {//如果存在这个用户
                userAttempts = new UserAttempts();
                userAttempts.setUsername(username);
                userAttempts.setAttempts(1);
                userAttempts.setLastModified(new Date());
                getCurrentSession().save(userAttempts);
            }
        }
        else {//如果有记录
            if (isUserExists(username)) {//如果存在这个用户
                userAttempts.setAttempts(userAttempts.getAttempts()+1);
                userAttempts.setLastModified(new Date());
                getCurrentSession().update(userAttempts);
                getCurrentSession().flush();
            }

            if(userAttempts.getAttempts()>=MAX_ATTEMPTS){//如果尝试的次数大于了规定的次数
                getCurrentSession().createQuery("update UserModel u set u.accountNonLocked =:accountNonLocked where u.name =:name")
                        .setParameter("accountNonLocked", false)
                        .setParameter("name", username)
                        .executeUpdate();
                    throw new LockedException("用户账号已被锁定，请联系管理员解锁");
            }
        }
    }

    /**
     * 重置登陆次数
     * @param username 用户名
     * @return
     */
    public void resetFailAttempts(String username) {
        getCurrentSession()
                .createQuery("update UserAttempts ua set ua.attempts = 0, ua.lastModified = null where ua.username =:username")
                .setParameter("username", username)
                .executeUpdate();
    }


    /**
     * 通过用户名获取用户尝试登陆次数对象
     * @param username 用户名
     * @return 用户尝试登陆次数对象
     */
    @SuppressWarnings("uncheck")//@suppresswarnings就是告诉编译器忽略警告。不用在编译完成后出现警告。 @SuppressWarnings(“”)
    @Transactional(readOnly = true)
    public UserAttempts getUserAttempts(String username){
        UserAttempts userAttempts=(UserAttempts) getCurrentSession()
                .createQuery("from UserAttempts where username=:username")
                .setParameter("username", username)
                .uniqueResult();
        return userAttempts;
    }
}
