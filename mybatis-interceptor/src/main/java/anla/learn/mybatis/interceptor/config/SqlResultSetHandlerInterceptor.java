package anla.learn.mybatis.interceptor.config;

import anla.learn.mybatis.interceptor.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/5 17:24
 **/
@Slf4j
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class SqlResultSetHandlerInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement stmt = (Statement) invocation.getArgs()[0];

        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    break;
                }
            }
        }
        User user = new User();
        user.setUid(11);
        user.setDescription("autoDescription");
        return Arrays.asList(user);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
