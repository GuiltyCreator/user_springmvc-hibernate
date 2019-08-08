package online.shixun.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    @Qualifier("tokenRepositoryDao")
    PersistentTokenRepository tokenRepository;

    //自定义的验证类CustomAuthenticationProvider
    @Resource
    private CustomAuthenticationProvider authenticationProvider;

    /**
     * 使用remember-me必须指定UserDetailsService
     * @return
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/").successForwardUrl("/")//设置登录完成后的跳转页面
                    .usernameParameter("name")//与前端账号的name一致
                    .passwordParameter("password")//与前端密码的name一致
                .and()
                    .logout().deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll()//登出后回到登录界面，并且删除掉Cookies
                .and()
                    .rememberMe()
                .rememberMeParameter("remember-me").tokenRepository(tokenRepository)//由于我们提供记住我的功能，跟踪令牌数据在数据库中，所以我们配置PersistentTokenRepository实现。
                .tokenValiditySeconds(1209600)
                //.userDetailsService(userDetailsService)//RememberMe要使用指定的userDetailsService这个和前面重写的那个userDetailsService()方法效果一样
                .key("xcp")
                .and()
                    .csrf().disable();
    }



        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }
}
