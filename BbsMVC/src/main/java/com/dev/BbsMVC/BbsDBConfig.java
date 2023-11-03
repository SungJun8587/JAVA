package com.dev.BbsMVC;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//******************************************************************************************
// SpringBoot MyBatis 설정
//******************************************************************************************
@Configuration
@MapperScan(value="com.dev.BbsMVC.Dao", sqlSessionFactoryRef="db1SqlSessionFactory")
@EnableTransactionManagement

public class BbsDBConfig
{
    @Bean(name = "db1DataSource", destroyMethod = "close")
    @Primary // 여러 DB 연결시 첫번째 DB 정의
    @ConfigurationProperties(prefix = "spring.db1.datasource") // 환경설정 파일(application.properties)에서 값을 추출해 주입
    public DataSource db2DataSource()
    {
        return DataSourceBuilder.create().build();
    }

    // ******************************************************************************************
    // SqlSessionFactory 설정
    // - MyBatis에 관련된 SqlSessionFactory 객체를 반환하는 빈을 선언
    // ******************************************************************************************
    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("db1DataSource")DataSource dataSource) throws Exception
    {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        sessionFactory.setDataSource(dataSource); // 트랜잭션을 관리해야하는 JDBC DataSource를 설정
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml")); // MyBatis 매퍼 파일의 위치를 설정
        sessionFactory.setTypeAliasesPackage("com.dev.BbsMVC.Model"); // "com.dev.Model.Bbs" 패키지 이하의 model 클래스 이름을 짧은 별칭으로 등록

        return sessionFactory.getObject();
    }

    // *****************************************************************
    // SqlSessionTemplate 설정
    // - SqlSessionTemplate은 SqlSession을 구현하고 코드에서 SqlSession를 대체하는 역할
    // - Thead Safe하게 작성되었기 때문에 여러 DAO나 매퍼에서 공유
    // ******************************************************************************************
    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // ******************************************************************************************
    // DataSourceTransactionManager 설정
    // - 트랜잭션 매니저를 반환하는 빈을 선언
    // ******************************************************************************************
    @Bean(name = "db1DataSourceTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("db1DataSource")DataSource dataSource) throws Exception
    {
        return new DataSourceTransactionManager(dataSource);
    }   
}