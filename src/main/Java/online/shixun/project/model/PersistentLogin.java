package online.shixun.project.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_persistent_logins")
public class PersistentLogin {
    @Column(name = "series",length = 64)
    private String series;

    @Id
    @Column(name = "username",nullable = false ,unique = true,length = 64)
    private String username;

    @Column(name = "token",nullable = false , length = 64)
    private String token;

    @Column(name = "last_used",nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
