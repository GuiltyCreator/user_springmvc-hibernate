package online.shixun.project.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_profession")
public class ProfessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id=0L;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "creatTime",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime=new Date();

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

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
