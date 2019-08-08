package online.shixun.project.security;

import online.shixun.project.model.UserModel;
import online.shixun.project.model.UserRole;
import online.shixun.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  security的验证过程会调用指定的UserDetailsService
 *  自定义的UserDetailsService查询数据库得到自己User后
 *  组装org.springframework.security.core.userdetails.User返回
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user=userService.getUserByName(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在:"+username);
        }

        // 用户被禁用
        if (!user.isEnabled()) {
            throw new BadCredentialsException("errors.user.is.disabled");
        }

        // 用户没有任何授权，不允许使用系统
        if (user.getUserRole() == null) {
            throw new BadCredentialsException("errors.user.has.not.anyRole");
        }

        // 用户拥有的角色信息
        Set<UserRole> userRoles = user.getUserRole();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        if (userRoles != null) {
            for (UserRole role : userRoles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().trim()));
            }
        }

        //List<GrantedAuthority> authorities=buildUserAuthority(user.getUserRole());
        user.setGrantedAuthority(grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]));

        //return buildUserForAuthentication(user,authorities);
        return user;
    }

    /*// 把自定义的UserModel转换成org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(UserModel user, List<GrantedAuthority> authorities) {

        User securityUser=new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
        return securityUser;
    }

    // Build user's authorities
    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        return new ArrayList<>(setAuths);
    }*/

}
