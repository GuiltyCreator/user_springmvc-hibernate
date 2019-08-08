package online.shixun.project.model;

import javax.persistence.*;

/**
 * 用户角色实体类
 */
@Entity
@Table(name="t_user_roles",uniqueConstraints = @UniqueConstraint(columnNames = {"role","user"}))
public class UserRole {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "user_role_id")
private Long userRoleId = 0L;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user",nullable = false)
private UserModel user;

@Column(nullable = false,length = 45)
private String role;


    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
