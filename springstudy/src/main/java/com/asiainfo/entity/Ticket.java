package com.asiainfo.entity;

import java.util.UUID;

public class Ticket {
    private String ticketSno;
    private String movieName;
    private String duration;    //时长
    private String startTime;

    public void init(){
        System.out.println("制造票据纸张！");
    }

    public Ticket(){
        this.ticketSno = UUID.randomUUID().toString();
    }

    public String printInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("票据SNO：" + this.ticketSno + "\n");
        sb.append("电影名：" + this.movieName + "\n");
        sb.append("开始时间：" + this.startTime + "\n");
        sb.append("电影时长：" + this.duration + "\n");
        return sb.toString();
    }

    public void setTicketSno(String ticketSno) {
        this.ticketSno = ticketSno;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
