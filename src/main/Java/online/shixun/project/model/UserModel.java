package online.shixun.project.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * 用户实体类
 */
@Entity
@Table(name = "t_user")
public class UserModel {

    // 实体主键（自增长）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id = 0L;

    // 性别(枚举值)
    public enum Gender {
        男, 女;
    }

    // 用户名称（不可以为空）
    @Column(length = 100, nullable = false)
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
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    // 职业（一个用户只能从事一个职业）
    @Column
    private String profession;

    // 爱好（一个用户可以拥有多个爱好）
    private String[] hobby;

    /**
     * hobby 转换为,分隔的字符串
     * @return 字符串
     */
    public String getHobbyString() {
        if (hobby != null && hobby.length > 0) {
            String hobbyStr = Arrays.toString(hobby);
            return hobbyStr.substring(1, hobbyStr.length() - 1);
        }
        return "";
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

    public String getPassword() {
        return password;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }
}