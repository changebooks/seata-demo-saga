package com.github.changebooks.seata.demo.saga.biz.shop.main;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Saga 配置
 *
 * @author changebooks
 */
@Configuration
public class SagaConfigurer {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.cloud.alibaba.seata.tx-service-group}")
    private String groupName;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DbStateMachineConfig stateMachineConfig(DruidDataSource dataSource) throws IOException {
        ThreadPoolExecutorFactoryBean threadExecutor = new ThreadPoolExecutorFactoryBean();
        threadExecutor.setThreadNamePrefix("SAGA_THREAD");

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:saga/*.json");

        DbStateMachineConfig stateMachineConfig = new DbStateMachineConfig();
        stateMachineConfig.setThreadPoolExecutor((ThreadPoolExecutor) threadExecutor.getObject());
        stateMachineConfig.setDataSource(dataSource);
        stateMachineConfig.setResources(resources);
        stateMachineConfig.setEnableAsync(true);
        stateMachineConfig.setApplicationId(appName);
        stateMachineConfig.setTxServiceGroup(groupName);
        return stateMachineConfig;
    }

    @Bean
    public ProcessCtrlStateMachineEngine stateMachineEngine(DbStateMachineConfig stateMachineConfig) {
        ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
        stateMachineEngine.setStateMachineConfig(stateMachineConfig);
        return stateMachineEngine;
    }

    @Bean
    public StateMachineEngineHolder stateMachineEngineHolder(ProcessCtrlStateMachineEngine stateMachineEngine) {
        StateMachineEngineHolder stateMachineEngineHolder = new StateMachineEngineHolder();
        stateMachineEngineHolder.setStateMachineEngine(stateMachineEngine);
        return stateMachineEngineHolder;
    }

}
