package online.shixun.project.dao;

import online.shixun.project.model.PersistentLogin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Repository("tokenRepositoryDao")
@Transactional
public class TokenRepositoryDao implements PersistentTokenRepository {

    // 利用 Spring 的配置文件直接生成 Hibernate 的 sessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        getCurrentSession().saveOrUpdate(persistentLogin);

        persistentLogin=getTokenBy("username",token.getUsername());
        System.out.println("token的name是："+persistentLogin.getUsername()+"   token是："+persistentLogin.getToken());
    }

    /**
     * 通过给定参数获取一个PersistentLogin对象
     * @param key 给定参数
     * @param value 参数的值
     * @return PersistentLogin对象
     */
    public PersistentLogin getTokenBy(String key,Object value){
        String hql="from PersistentLogin where "+key+"=:"+key;
        System.out.println("hql:"+hql);
        PersistentLogin persistentLogin=(PersistentLogin)getCurrentSession()
                .createQuery(hql)
                .setParameter(key, value)
                .uniqueResult();
        return persistentLogin;
    }


    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        System.out.println("更新token");
        PersistentLogin persistentLogin =getTokenBy("series",series);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        getCurrentSession().update(persistentLogin);
        getCurrentSession().flush();

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            PersistentLogin persistentLogin = getTokenBy("series",seriesId);

            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void removeUserTokens(String username) {
        PersistentLogin persistentLogin=getTokenBy("username",username);
        if (persistentLogin != null) {
            getCurrentSession().delete(persistentLogin);
        }
    }
}
