package com.android.appfruit.entity;

public class PaginationResponse {

    private int page;
    private int limit;
    private int totalPages;
    private int totalItems;

    @Override
    public String toString() {
        return "PaginationResponse{" +
                "page=" + page +
                ", limit=" + limit +
                ", totalPages=" + totalPages +
                ", totalItems=" + totalItems +
                '}';
    }

    public PaginationResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
