package com.tistory.aircook.mybatis.config.database.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
    @Signature(type = StatementHandler.class,
            method = "prepare",
            args = {Connection.class, Integer.class})
})
@Slf4j
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        //String sqlI = mappedStatement.getId();

        //Interceptor한 SQL
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

        //파라미터
        Object paramObj = statementHandler.getParameterHandler().getParameterObject();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT *\n");
        sql.append("FROM (\n");
        sql.append(originalSql);
        sql.append("\t)\n");

        log.debug("sql is [{}]", sql);

        //변경된 SQL 설정
        metaStatementHandler.setValue("delegate.boundSql.sql", sql.toString());
        return invocation.proceed();
    }

    //파라미터로 전달받는 target객체는 실제 타겟이 되는 대상으로 현재 플러그인과 연결해 준다.
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
