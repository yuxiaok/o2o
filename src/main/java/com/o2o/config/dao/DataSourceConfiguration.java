package com.o2o.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.o2o.util.DESUtil;

/**
 * @author kai.yu
 * @date 2019/4/26 数据源配置
 **/
@Configuration
/**
 * 设置dao的扫描路径
 */
@MapperScan("com.o2o.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUserName;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 设置数据库连接池
     * 
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createComboPooledDataSource() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(jdbcDriver);
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(DESUtil.getDecryptString(jdbcUserName));
        comboPooledDataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        comboPooledDataSource.setMaxPoolSize(30);
        comboPooledDataSource.setMinPoolSize(10);
        // 设置关闭连接后不自动提交
        comboPooledDataSource.setAutoCommitOnClose(false);
        // 设置超时时间
        comboPooledDataSource.setCheckoutTimeout(10000);
        // 设置连接失败的重试次数
        comboPooledDataSource.setAcquireRetryAttempts(2);
        return comboPooledDataSource;
    }
}
