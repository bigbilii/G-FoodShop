package me.guoxin.pojo;

import me.guoxin.utils.Apriori2;

import java.util.HashSet;
import java.util.Set;

public class GfsRule {
    private Long id;
    private String products;
    private Double confidence;
    private Set<Long> left;
    private Set<Long> right;

    public Set<Long> getLeft() {
        return left;
    }

    public void setLeft(Set<Long> left) {
        this.left = left;
    }

    public Set<Long> getRight() {
        return right;
    }

    public void setRight(Set<Long> right) {
        this.right = right;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public void make(){
        String p = this.products;
        String[] ss = p.split(Apriori2.CON);
        String aa = ss[0];
        String bb = ss[1];
        String[] ll = aa.split(Apriori2.ITEM_SPLIT);
        String[] rr = bb.split(Apriori2.ITEM_SPLIT);
        Set<Long> l = new HashSet<Long>();
        for (int i = 0; i < ll.length; i++) {
            l.add(Long.valueOf(ll[i]));
        }
        Set<Long> r = new HashSet<Long>();
        for (int i = 0; i < rr.length; i++) {
            r.add(Long.valueOf(rr[i]));
        }
        this.left = l;
        this.right = r;
    }

    public static void main(String[] args) {
        new GfsRule().make();
    }
}
