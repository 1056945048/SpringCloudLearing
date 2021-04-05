package com.itcloud.quartz.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Configuration
public class QuartzConfig {
    /**
     * quartz属性
     */
    private Properties quartzProperties() {
        Properties prop = new Properties();
        //拉取trigger加锁, 多实例部署一定要加上，防止任务重复执行，出现ABA问题！！！
        prop.put("org.quartz.jobStore.acquireTriggersWithinLock", "true");
        // 可以是任意字符串，这个值对于scheduler自己来说没有任何意义，但是当多个实例存在同一个程序中时，
        // 它可以用于区分不同的scheduler。如果你使用集群特性，那么在集群中逻辑术语同一个scheduler的实例
        // 必须使用相同的名字
        prop.put("org.quartz.scheduler.instanceName", "quartzScheduler");
        // 设置为AUTO，它会自动为你生成Id
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // Quartz内置了一个“更新检查”特性，因此Quartz项目每次启动后都会检查官网，Quartz是否存在新版本。
        // 这个检查是异步的，不影响Quartz项目本身的启动和初始化。
        // 可以在Quartz配置文件中，设置org.quartz.scheduler.skipUpdateCheck的属性为true来跳过更新检查。
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        // 是否打开Quartz的JMX支持
        prop.put("org.quartz.scheduler.jmx.export", "true");

        // JobStore配置相关--------------
        // #JobDataMaps是否都为String类型
        // prop.put("org.quartz.jobStore.useProperties", "true");//不要用，报错
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // PG专用
        // prop.put("org.quartz.jobStore.driverDelegateClass",
        //       "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        // job注入datasource
        // prop.put("org.quartz.jobStore.dataSource","druid");//#使用JNDI数据源的时候，数据源的名字

        // 集群相关---------------
        // 开启集群 #ture则此实例需要参加到集群中
        prop.put("org.quartz.jobStore.isClustered", "true");
        // #调度实例失效的检查时间间隔
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        // prop.put("org.quartz.jobStore.dataSource", "myDS");
        // 这是 JobStore 能处理的错过触发的 Trigger 的最大数量
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        // #容许的最大作业延长时间
        prop.put("org.quartz.jobStore.misfireThreshold", "60000");
        // 表前缀
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        // 值为 true 时告知 Quartz(当使用 JobStoreTX 或 CMT)
        // 调用 JDBC 连接的 setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE) 方法
        // 这有助于阻止某些数据库在高负载和长时间事物时锁的超时。
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
        prop.put("org.quartz.jobStore.selectWithLockSQL",
                "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

        // 线程池相关 ThreadPool---------------
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 可以是任意的正整数。实际上只有1到100会用到。这是并行执行job可用的线程数。如果只有几个job，
        // 一天也只有几次触发，那么一个线程就足够了！如果你有成百上千的job，且每一个每一分钟都会触发。
        // 那么你可能想要让线程的数量达到50或者100了（这取决于你的job执行的工作以及系统资源了）。
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // 可以为true或者false，默认为false
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread",
                "true");
        // 插件相关----------------
        prop.put("org.quartz.plugin.triggHistory.class",
                "org.quartz.plugins.history.LoggingJobHistoryPlugin");
        // ShutdownHookPlugin捕获JVN终止的事件，并调用scheduler的shutdown方法。
        prop.put("org.quartz.plugin.shutdownhook.class",
                "org.quartz.plugins.management.ShutdownHookPlugin");
        prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true");
        return prop;
    }


    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }

}
