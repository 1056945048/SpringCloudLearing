package com.itcloud.quartz.module;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Map;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Data
public class Query<T>  {
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;

    private long curPage = 1;

    private long limit = 10;

    public Query(Map<String, Object> params) {
         if (params.get("page") != null) {
            curPage =Long.parseLong((String)params.get("page"));
         }
         if(params.get("limit") != null) {
             limit = Long.parseLong((String)params.get("limit"));
         }
         //分页对象
         this.page = new Page<>(curPage, limit);

         params.put("page", page);
    }



}
