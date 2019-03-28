package com.framework.file.pojo;


/***
 **@project: base
 **@description: page usage
 **@Author: twj
 **@Date: 2019/03/28
 **/
public class PageBody {

    private int pageSize;

    private int pageNum;

    private Long total;

    private ResultBody resultBody;

    public PageBody() {
    }

    public PageBody(int pageSize, int pageNum, Long total, ResultBody resultBody) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.total = total;
        this.resultBody = resultBody;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
