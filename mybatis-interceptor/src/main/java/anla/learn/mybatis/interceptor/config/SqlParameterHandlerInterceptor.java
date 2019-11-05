package anla.learn.mybatis.interceptor.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 以下会判断参数是否有id，
 * 有就会把id=1放入
 *
 * @author anLA7856
 * @date 19-11-4 下午11:27
 * @description
 */
@Slf4j
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class SqlParameterHandlerInterceptor implements Interceptor {
    private static final String PARAM_KEY = "id";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof ParameterHandler) {
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();

            // 反射获取 BoundSql 对象，此对象包含生成的sql和sql的参数map映射
            Field boundSqlField = parameterHandler.getClass().getDeclaredField("boundSql");
            boundSqlField.setAccessible(true);
            BoundSql boundSql = (BoundSql) boundSqlField.get(parameterHandler);

            List<String> paramNames = new ArrayList<>();
            // 若参数映射没有包含的key直接返回
            boolean hasKey = hasParamKey(paramNames, boundSql.getParameterMappings());
            if (!hasKey) {
                return invocation.proceed();
            }

            // 反射获取 参数对像
            Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
            parameterField.setAccessible(true);
            Object parameterObject = parameterField.get(parameterHandler);

            // 改写参数
            parameterObject = processSingle(parameterObject, paramNames);

            // 改写的参数设置到原parameterHandler对象
            parameterField.set(parameterHandler, parameterObject);
        }
        return invocation.proceed();

    }
    private Object processSingle(Object paramObj, List<String> paramNames) throws Exception {

        Map<String, Object> paramMap = new MapperMethod.ParamMap<>();
        if (paramObj == null) {
            paramMap.put(PARAM_KEY, 1);
            paramObj = paramMap;
            // 单参数 将 参数转为 map
        } else if (ClassUtils.isPrimitiveOrWrapper(paramObj.getClass())
                || String.class.isAssignableFrom(paramObj.getClass())
                || Number.class.isAssignableFrom(paramObj.getClass())) {
            if (paramNames.size() == 1) {
                paramMap.put(paramNames.iterator().next(), paramObj);
                paramMap.put(PARAM_KEY, 1);
                paramObj = paramMap;
            }
        } else {
            processParam(paramObj);
        }

        return paramObj;
    }

    private void processParam(Object parameterObject) throws IllegalAccessException, InvocationTargetException {
        // 处理参数对象  如果是 map 且map的key 中没有 tenantId，添加到参数map中
        // 如果参数是bean，反射设置值
        if (parameterObject instanceof Map) {
            ((Map) parameterObject).put(PARAM_KEY, 1);
        } else {
            PropertyDescriptor ps = BeanUtils.getPropertyDescriptor(parameterObject.getClass(), PARAM_KEY);
            if (ps != null && ps.getReadMethod() != null && ps.getWriteMethod() != null) {
                Object value = ps.getReadMethod().invoke(parameterObject);
                if (value == null) {
                    ps.getWriteMethod().invoke(parameterObject, 1);
                }
            }
        }
    }

    private boolean hasParamKey(List<String> paramNames, List<ParameterMapping> parameterMappings) {
        boolean hasKey = false;
        for (ParameterMapping parameterMapping : parameterMappings) {
            if (StringUtils.equals(parameterMapping.getProperty(), PARAM_KEY)) {
                hasKey = true;
                paramNames.add(parameterMapping.getProperty());
            }
        }
        return hasKey;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
