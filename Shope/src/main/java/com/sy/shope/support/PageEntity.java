package com.sy.shope.support;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wang xiao
 * @description:
 * @date: Created in 14:38 2020/6/3
 */
@Data
@AllArgsConstructor
public class PageEntity<T> implements Serializable {
    private int currentPage;
    private int pageSize;
    private int total;
    private List<T> entityList;
}

