package com.dhj.eac_api.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @description: 分页模型 VO
 * @author: dhj
 * @version: v1.0
 */
@Data
@ToString
public class PageVO<T> {
    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer count;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 当前页所有记录
     */
    private List<T> records;

    /**
     * 页码集合
     */
    private List<Integer> pageCodes;
}
