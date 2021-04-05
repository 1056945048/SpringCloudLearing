package com.itcloud.quartz.module;

/**
 * @author yangkun
 * @date 2021-03-24
 */
public class JobConstant {
    /**
     * JobDataMap 的参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
         normal(0),
         parse(1);
         private int value;
         ScheduleStatus(int value) {
             this.value = value;
         }
         public int getValue() {
             return value;
         }
    }
}
