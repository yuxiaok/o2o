package com.o2o.config.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author kai.yu
 * @date 2019/4/26 mybatis工厂配置
 **/
@Configuration
public class SqlSessionFactoryConfiguration {
    @Autowired
    private DataSource dataSource;

    /**
     * mybatis核心配置文件
     */
    private static String mybatisConfigFile;

    @Value("${mybatis.config}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        SqlSessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
    }

    /**
     * mapper文件所在位置
     */
    private static String mapperPath;

    @Value("${mapper.path}")
    public void setMapperPath(String mapperPath) {
        SqlSessionFactoryConfiguration.mapperPath = mapperPath;
    }

    /**
     * 实体类的位置
     */
    @Value("${type.aliase.package}")
    private String typeAliasPackage;

    /**
     * 创建sqlSessionFactoryBean实例
     * 
     * @return
     * @throws IOException
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置mybatis-config扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
        // 设置mapper扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
            new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置实体类扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean;
    }
}
