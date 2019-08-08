package online.shixun.project.security;

import online.shixun.project.dao.UserDetailsDao;
import online.shixun.project.model.UserAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义验证程序
 * 实现自定义的AuthenticationProvider，当每次登录失败以后更新用户尝试次数表
 */
@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Autowired
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{//调用上层验证逻辑
            Authentication auth=super.authenticate(authentication);
            //如果验证通过登录成功则重置尝试次数， 否则抛出异常
            userDetailsDao.resetFailAttempts(authentication.getName());
            return auth;
        }
        catch (BadCredentialsException e){//如果验证不通过，则更新尝试次数，当超过次数以后抛出账号锁定异常
            String error;
            userDetailsDao.updateFailAttempts(authentication.getName());
            error="用户名或密码不正确，您还有："+(5-userDetailsDao.getUserAttempts(authentication.getName()).getAttempts())+"次登录机会";
            throw new BadCredentialsException(error);
        }
        catch (LockedException e){//该用户已经被锁定，则进入这个异常
            String error;
            UserAttempts userAttempts=userDetailsDao.getUserAttempts(authentication.getName());
            if(userAttempts!=null){
                Date lastAttempts = userAttempts.getLastModified();
                error="用户已被锁定，用户名："+authentication.getName()+"  最后尝试时间："+lastAttempts;
            } else {
                error = e.getMessage();
            }
            throw new LockedException(error);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
