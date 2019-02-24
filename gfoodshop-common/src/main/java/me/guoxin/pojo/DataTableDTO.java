package me.guoxin.pojo;


public class DataTableDTO {
    private Page page;
    private String search;
    private Order order;

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
