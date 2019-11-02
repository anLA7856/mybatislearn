package anla.learn.mybatis.hello.withoutxml;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author anLA7856
 * @date 19-11-2 下午12:34
 * @description
 */
public class MyDataSourceFactory implements DataSourceFactory {
    private Properties prop;


    @Override
    public void setProperties(Properties properties) {
        prop = properties;
    }

    @Override
    public DataSource getDataSource() {
        PooledDataSource ds = new PooledDataSource();

        ds.setDriver(prop.getProperty("driver"));
        ds.setUrl(prop.getProperty("url"));
        ds.setUsername(prop.getProperty("user"));
        ds.setPassword(prop.getProperty("password"));

        return ds;
    }
}
