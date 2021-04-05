package com.itcloud.delay.queue.constants;

/**
 * @author yangkun
 * @date 2021-03-30
 */
public class DelayConfig {
    /**
     * 睡眠时间
     */
    public static Long SLEEP_TIME = 1000L;

    /**
     * 重试次数
     */
    public static Integer RETRY_COUNT = 5;

    /**
     * 默认超时时间
     */
    public static Long PROCESS_TIME = 5000L;
}
