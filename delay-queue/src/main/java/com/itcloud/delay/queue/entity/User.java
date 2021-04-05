package com.itcloud.delay.queue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangkun
 * @date 2021-03-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
        private String id;
        private String name;
        private String age;
    /**
     * 重试次数
      */
    private int retry;
}
