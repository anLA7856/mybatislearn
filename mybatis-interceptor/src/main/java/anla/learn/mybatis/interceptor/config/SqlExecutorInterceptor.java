package anla.learn.mybatis.interceptor.config;

import anla.learn.mybatis.interceptor.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * update, query, flushStatements, commit, rollback, getTransaction, close, isClosed
 * @author anLA7856
 * @date 19-11-4 下午10:24
 * @description
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class SqlExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 根据签名指定的args顺序获取具体的实现类
        // 获取MappedStatement实例, 并获取当前SQL命令类型
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType commandType = ms.getSqlCommandType();

        // 获取当前正在被操作的类, 有可能是Java Bean, 也可能是普通的操作对象, 比如普通的参数传递
        Object parameter = invocation.getArgs()[1];
        // 获取拦截器指定的方法类型, 通常需要拦截 update
        String methodName = invocation.getMethod().getName();
        log.info("NormalPlugin, methodName; {}, commandType: {}", methodName, commandType);

        // todo 为什么这个parameter 是MapperMethod$ParamMap?
        if (parameter instanceof User) {
            // 获取参数
            User userParam = (User) parameter;
            if ("update".equals(methodName)) {
                if (commandType.equals(SqlCommandType.INSERT)) {
                    userParam.setDescription("SqlExecutorInterceptor 动态增加");
                } else if (commandType.equals(SqlCommandType.UPDATE)) {
                    userParam.setDescription("SqlExecutorInterceptor 动态更新");
                }
            }
        }
        // 均不是需要被拦截的类型, 不做操作
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
