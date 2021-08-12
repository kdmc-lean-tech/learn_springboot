package com.demo.demo.Shared.Helpers;

import com.demo.demo.Shared.Enums.Sorting;

public class Paginate<T> {
    private Integer page;
    private Integer pageSize;
    private Integer offset;
    private T sort;
    private Sorting sorting;

    public Paginate(Integer page, Integer pageSize, Sorting sorting, T sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.sorting = sorting;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getOffset() {
        return (this.page * this.pageSize) - this.pageSize;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public T getSort() {
        return sort;
    }
}
