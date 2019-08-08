package online.shixun.project.model;

import online.shixun.project.util.DateUtils;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 用户实体类
 */
@Entity
@Table(name = "t_user")
public class UserModel implements UserDetails {

    // 实体主键（自增长）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id = 0L;

    @Column(nullable = true)
    private boolean enabled = true;

    @Column(nullable = true)
    private boolean accountNonExpired = true;

    @Column(nullable = true)
    private boolean accountNonLocked = true;

    @Column(nullable = true)
    private boolean credentialsNonExpired = true;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<>();

    @Column(name = "creatTime",nullable = true,columnDefinition="datetime")
    private String  creatTime = DateUtils.timeToString(new Date());

    // 性别(枚举值)
    public enum Gender {
        男, 女;
    }

    // 用户名称（不可以为空）
    @Column(length = 100, unique = true,nullable = false)
    private String name;

    // 用户性别（不可以为空） 枚举类型
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender = Gender.男;

    // 用户登录密码（不可以为空）
    @Column(length = 100, nullable = false)
    private String password;

    // 再次输入登录密码
    @Transient // 不作为数据库持久化字段
    private String passwordAgain;

    // 用户登录密码（不可以为空）
    @Column(length = 200, nullable = false)
    private String email;

    // 生日
    @Column(name = "birthday",nullable = true,columnDefinition="date")
    private String  birthday=DateUtils.timeToString(new Date());

    // 职业（一个用户只能从事一个职业）
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession")
    private ProfessionModel profession;

    // 爱好（一个用户可以拥有多个爱好）
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="t_USER_HOBBY",                       //指定第三张表
            joinColumns={@JoinColumn(name="id")},             //本表与中间表的外键对应
            inverseJoinColumns={@JoinColumn(name="HOBBY_ID")})
    private Set<HobbyModel> hobby;

    @Column
    private String userAvatarImage;

    // 权限
    @Transient
    private GrantedAuthority[] grantedAuthority;



    public String getUserAvatarImage() {
        return userAvatarImage;
    }

    public void setUserAvatarImage(String userAvatarImage) {
        this.userAvatarImage = userAvatarImage;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGrantedAuthority(GrantedAuthority[] grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(grantedAuthority);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ProfessionModel getProfession() {
        return profession;
    }

    public void setProfession(ProfessionModel profession) {
        this.profession = profession;
    }

    public Set<HobbyModel> getHobby() {
        return hobby;
    }

    public void setHobby(Set<HobbyModel> hobby) {
        this.hobby = hobby;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }
}