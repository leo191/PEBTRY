package com.example.shubu.peb;

import java.util.Date;

/**
 * Created by leo on 23/07/17.
 */

public class LEOvisitors {

    public String duration;
    public String name,DestAddress,SourAddress,phone_no,god,up,purpose,inTime,outTime;
    public String curr_date;
    public long inTimeInmilis;
    public String carNo;
    public LEOvisitors(){}

    public LEOvisitors(String name,
                       String destAddress,
                       String sourAddress,
                       String phone_no,
                       String purpose,
                       String inTime,
                       String curr_date,
                       long inTimeInmilis,
                       String carNo) {
        this.name = name;
        DestAddress = destAddress;
        SourAddress = sourAddress;
        this.phone_no = phone_no;
        this.purpose = purpose;
        this.inTime = inTime;
        this.curr_date = curr_date;
        this.inTimeInmilis = inTimeInmilis;
        this.carNo = carNo;
        this.duration="";
        this.outTime ="";
    }
}
