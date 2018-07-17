package com.asiainfo.entity;

public class Screen {
    private int height;
    private int width;

    public Screen(int height,int width){
        this.height = height;
        this.width = width;
    }

    public String printSize(){
        return "高：" + height + "m,宽：" + width + "m";
    }
}
