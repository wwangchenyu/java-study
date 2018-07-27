package com.asiainfo.entity;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 电影院，单例，
 * 延迟加载,使用"initialization on demand holder"保证线程安全
 */
public class Cinema {
    private String name;
    private String address;
//    @Autowired(required = false)    //如果配置required=true（默认值），若找不到相应bean，则会抛出异常NoSuchBeanDefinitionException
    private Screen screen;
    private List<Door> doors;
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
        System.out.print(",Screen Size:" + this.screen.printSize());
        System.out.println(",door count:" + this.doors.size());
        for(int i= 0,size = doors.size(); i < size; i++){
            System.out.println(doors.get(i).printInfo());
        }
    }

    @Autowired
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }
}
