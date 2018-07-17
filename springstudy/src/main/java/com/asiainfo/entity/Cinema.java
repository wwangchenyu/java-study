package com.asiainfo.entity;

/**
 * 电影院，单例，
 * 延迟加载,使用"initialization on demand holder"保证线程安全
 */
public class Cinema {
    private String name;
    private String address;
    private Screen screen;

    private Cinema(){}
//    private Cinema(String name,String address){
//        this.name = name;
//        this.address = address;
//    }

    private static class CinemaSingletonHolder{
        static Cinema instance = new Cinema();
    }

    public static Cinema getInstance(){
        return CinemaSingletonHolder.instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void infomation(){
        System.out.print("name=" + this.name + ",address=" + this.address);
        System.out.println(",Screen Size:" + this.screen.printSize());
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}