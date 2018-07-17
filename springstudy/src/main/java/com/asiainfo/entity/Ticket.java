package com.asiainfo.entity;

import java.util.UUID;

public class Ticket {
    private String ticketSno;

    public void init(){
        System.out.println("制造票据纸张！");
    }

    public Ticket(){
        this.ticketSno = UUID.randomUUID().toString();
    }

    public String checkTicket(){
        return this.ticketSno;
    }
}
