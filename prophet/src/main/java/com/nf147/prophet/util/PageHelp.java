package com.nf147.prophet.util;

public class PageHelp {

    //当前页面是第几页
    private int pageCode;

    //总共页面有几页
    private int pageCount;

    //总条数
    private int count;

    //分页每页条数
    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static PageHelp total(int count,int pageSize,int pageCode) {
        PageHelp pageHelp = new PageHelp();
        pageHelp.setCount(count);
        pageHelp.setPageSize(pageSize);
        pageHelp.setPageCode(pageCode);

        //计算总共有多少页
        int sum = (int) (((float) count % (float) pageSize) == 0 ? Math.ceil(count / pageSize) : (Math.ceil((float) count / (float) pageSize)));
        pageHelp.setPageCount(sum);

        return pageHelp;
    }
}
