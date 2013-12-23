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
}
