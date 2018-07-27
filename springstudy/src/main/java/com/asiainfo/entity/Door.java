package com.asiainfo.entity;

public class Door {
    private int height;
    private int width;
    private String material;    //材料

    public Door(int height, int width, String material) {
        this.height = height;
        this.width = width;
        this.material = material;
    }

    public String printInfo(){
        return "height:" + height + ",width:" + width + ",material:" + material;
    }
}
