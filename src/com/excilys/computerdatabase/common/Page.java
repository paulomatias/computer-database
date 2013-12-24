package com.excilys.computerdatabase.common;

import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.common
 * User: lortola
 * Date: 04/12/13
 * Description: N/A
 */
public class Page<E> {

    private List<E> items;
    private int recordCount;
    private int totalCount;
    private int pageCount;
    private int currentPage;
    private int limit;

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
