package com.example.test.database;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(
        basePackages = "com.example.test",
        sqlSessionFactoryRef = "MyBatis_DataSource",
        sqlSessionTemplateRef = "MyBatis_SqlSessionFactory"
)
@ComponentScan(basePackages = {"com.example.test"})
@RequiredArgsConstructor
public class DatabaseConfig {
    private final DBInfoProps dbInfoProps;
    private final ApplicationContext applicationContext;

    @Bean(name = "MyBatis_DataSource")
    public DataSource getRouterDatasource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targetDataSources = createTargetDataSources();
        routingDataSource.setTargetDataSources(targetDataSources);

        Map.Entry<Object, Object> entry = targetDataSources.entrySet().stream().findFirst().get();
        String defDBUser = entry.getKey().toString();

        routingDataSource.setDefaultTargetDataSource(targetDataSources.get(defDBUser));

        return routingDataSource;
    }

    private Map<Object, Object> createTargetDataSources() {

        Map<Object, Object> targetDataSources = new HashMap<>();

        dbInfoProps.getInfo().forEach(info -> {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(info.getDriverClassname());
            dataSource.setUrl(info.getUrl());
            dataSource.setUsername(info.getUsername());
            dataSource.setPassword(info.getPassword());

            targetDataSources.put(info.getUsername(), dataSource);
        });

        return targetDataSources;
    }

    @Bean(name = "MyBatis_SqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getRouterDatasource());

        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/sql/**/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getRouterDatasource());
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.afterPropertiesSet();

        return transactionManager;
    }

    @Bean(name = "MyBatis_SqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(getSqlSessionFactory());
    }

}
