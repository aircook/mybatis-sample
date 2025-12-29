package com.tistory.aircook.mybatis.config.database;

import com.tistory.aircook.mybatis.config.database.mapper.BatchMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.tistory.aircook.mybatis.repository",
        annotationClass = BatchMapper.class,
        sqlSessionTemplateRef = "batchSqlSessionTemplate"
)
public class MybatisBatchConfig {

    @Bean(name = "batchSqlSessionFactory")
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
     * SqlSessionTemplate 객체 생성(Batch 전용)
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "batchSqlSessionTemplate")
    public SqlSessionTemplate batchSqlSessionTemplate(@Qualifier("batchSqlSessionFactory")  SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

    @Bean(name = "batchTransactionManager")
    //@Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
