package com.bbs.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据返回结果集
 *
 * @param <T>
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 第几页
     */
    private int page;

    /**
     * 返回数据集合
     */
    private List<T> list;

    /**
     * 总数据条数
     */
    private int total;

    /**
     * 总页数
     */
    private int pageTotal;
}
