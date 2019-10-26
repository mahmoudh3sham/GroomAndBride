package com.groomandbride.data.models;

public class MainCategory {
    private String id;
    private String name;
    private int imgDrawble;
    private int imgDrawble2;

    private int limitAll;
    private int pageAll;

    public MainCategory(String id, String name, int imgDrawble, int imgDrawble2) {
        this.id = id;
        this.name = name;
        this.imgDrawble = imgDrawble;
        this.imgDrawble2 = imgDrawble2;
    }

    public MainCategory(String name, int imgDrawble, int limitAll, int pageAll, int imgDrawble2) {
        this.name = name;
        this.imgDrawble = imgDrawble;
        this.limitAll = limitAll;
        this.pageAll = pageAll;
        this.imgDrawble2 = imgDrawble2;
    }

    public int getLimitAll() {
        return limitAll;
    }

    public void setLimitAll(int limitAll) {
        this.limitAll = limitAll;
    }

    public int getPageAll() {
        return pageAll;
    }

    public void setPageAll(int pageAll) {
        this.pageAll = pageAll;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgDrawble() {
        return imgDrawble;
    }

    public void setImgDrawble(int imgDrawble) {
        this.imgDrawble = imgDrawble;
    }

    public int getImgDrawble2() {
        return imgDrawble2;
    }

    public void setImgDrawble2(int imgDrawble2) {
        this.imgDrawble2 = imgDrawble2;
    }
}
