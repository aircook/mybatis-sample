package com.tistory.aircook.mybatis.config.database;

import com.tistory.aircook.mybatis.config.database.interceptor.MybatisInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.tistory.aircook.mybatis.repository",
        annotationClass = Mapper.class,
        sqlSessionTemplateRef = "sqlSessionTemplate"
)
public class MybatisSimpleConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        //sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:com/tistory/aircook/mybatis/repository/**/*.xml"));
        //sqlSessionFactory.setTypeAliasesPackage("com.tistory.aircook.mybatis.model.**");

        /*
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <configuration>
        <settings>
            <setting name="mapUnderscoreToCamelCase" value="true"/>
            <setting name="callSettersOnNulls" value="true"/>
            <setting name="jdbcTypeForNull" value="NULL" />
        </settings>
        </configuration>
        */
        //Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        //sqlSessionFactory.setConfigLocation(myBatisConfig);
        // to java config
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);

        sqlSessionFactory.setConfiguration(configuration);

        ////Mybatis Plug-In 설정
        sqlSessionFactory.setPlugins(new MybatisInterceptor());

        return sqlSessionFactory.getObject();
    }

    /**
     * SqlSessionTemplate 객체 생성
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    //@Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
