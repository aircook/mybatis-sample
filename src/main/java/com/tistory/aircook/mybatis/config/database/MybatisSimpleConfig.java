package com.tistory.aircook.mybatis.config.database;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
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

        //Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        //sqlSessionFactory.setConfigLocation(myBatisConfig);

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
