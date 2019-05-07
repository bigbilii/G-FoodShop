package me.guoxin.pojo;


import java.util.Date;

public class DataTableDTO {
    private Page page;
    private String search;
    private Order order;
    private Integer status;
    private Date s;
    private Date e;

    public Date getS() {
        return s;
    }

    public void setS(Date s) {
        this.s = s;
    }

    public Date getE() {
        return e;
    }

    public void setE(Date e) {
        this.e = e;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "DataTableDTO{" +
                "page=" + page +
                ", search='" + search + '\'' +
                ", order=" + order +
                '}';
    }
}
