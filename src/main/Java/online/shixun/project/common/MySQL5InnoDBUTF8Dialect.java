package online.shixun.project.common;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class MySQL5InnoDBUTF8Dialect extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString(){
        return "ENGINE=InnoDB DEFAULT CHARSET=UTF8";
    }

}
