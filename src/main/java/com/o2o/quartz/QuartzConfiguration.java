package com.o2o.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.o2o.service.ProductSellDailService;

/**
 * 引入quartz 支持定时job
 */

@Configuration
public class QuartzConfiguration {

    @Autowired
    private ProductSellDailService productSellDailService;

    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailFactoryBean;

    @Autowired
    private CronTriggerFactoryBean productSellDailyTriggerFactory;

    /**
     * 创建jobDetail并返回
     * 
     * @return
     */
    @Bean(name = "jobDetailFactory")
    public MethodInvokingJobDetailFactoryBean createJobDetail() {
        // new出JobDetailFactory对象，此工厂主要用来制作一个jobDetail,即制作一个任务
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        // 设置jobDetail的名字
        jobDetailFactoryBean.setName("product_sell_daily_job");
        // 设置jobDetail的组名
        jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
        // 不允许job并发运行
        jobDetailFactoryBean.setConcurrent(false);
        // 指定运行任务的类
        jobDetailFactoryBean.setTargetObject(productSellDailService);
        // 指定运行任务的方法
        jobDetailFactoryBean.setTargetMethod("dailyCalcuate");
        return jobDetailFactoryBean;
    }

    @Bean(name = "productSellDailyTriggerFactory")
    public CronTriggerFactoryBean createProdcuctSellDailyTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("product_sell_daily_trigger");
        cronTriggerFactoryBean.setGroup("job_product_sell_daily_group");
        // 绑定jobDetail
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        // 设置cron表达式 每天凌晨执行用一次
        cronTriggerFactoryBean.setCronExpression("0 0 0 * * ? *");
        return cronTriggerFactoryBean;
    }

    /**
     * 创建调度工厂并返回
     * 
     * @return
     */
    @Bean("schedulerFactory")
    public SchedulerFactoryBean createSchedulerFactory() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(productSellDailyTriggerFactory.getObject());
        return schedulerFactoryBean;
    }
}
