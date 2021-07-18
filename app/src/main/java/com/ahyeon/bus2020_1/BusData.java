package com.ahyeon.bus2020_1;

import java.io.Serializable;

public class BusData implements Serializable {
    private String line_name;
    private String dir_pass;
    private String line_id;

    private String busstop_name;
    private String ars_id;

    private String dir_down_name;
    private String dir_up_name;
    private String remain_min;
    private String remain_stop;
    private String dir_start;
    private String dir_end;
    private String next_busstop;
    private String busstop_id;
    private String seleted_busstop_name;
    private String seleted_Ars_id;
    private int viewType;

    public String getSeleted_Ars_id() {
        return seleted_Ars_id;
    }

    public void setSeleted_Ars_id(String seleted_Ars_id) {
        this.seleted_Ars_id = seleted_Ars_id;
    }


    public String getSeleted_busstop_name() {
        return seleted_busstop_name;
    }

    public void setSeleted_busstop_name(String seleted_busstop_name) {
        this.seleted_busstop_name = seleted_busstop_name;
    }





    public BusData(int viewType) {
        this.viewType = viewType;
    }
    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    public String getNext_busstop() {
        return next_busstop;
    }

    public void setNext_busstop(String next_busstop) {
        this.next_busstop = next_busstop;
    }


    public String getDir_down_name() {
        return dir_down_name;
    }

    public void setDir_down_name(String dir_down_name) {
        this.dir_down_name = dir_down_name;
    }

    public String getDir_up_name() {
        return dir_up_name;
    }

    public void setDir_up_name(String dir_up_name) {
        this.dir_up_name = dir_up_name;
    }

    public String getBusstop_id() {
        return busstop_id;
    }

    public void setBusstop_id(String busstop_id) {
        this.busstop_id = busstop_id;
    }

    public String getRemain_min() {
        return remain_min;
    }

    public void setRemain_min(String remain_min) {
        this.remain_min = remain_min;
    }

    public String getRemain_stop() {
        return remain_stop;
    }

    public void setRemain_stop(String remain_stop) {
        this.remain_stop = remain_stop;
    }

    public String getDir_start() {
        return dir_start;
    }

    public void setDir_start(String dir_start) {
        this.dir_start = dir_start;
    }

    public String getDir_end() {
        return dir_end;
    }

    public void setDir_end(String dir_end) {
        this.dir_end = dir_end;
    }

    public String getArs_id() {
        return ars_id;
    }

    public void setArs_id(String ars_id) {
        this.ars_id = ars_id;
    }
    public String getBusstop_name() {
        return busstop_name;
    }

    public void setBusstop_name(String busstop_name) {
        this.busstop_name = busstop_name;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }



    public String getDir_pass() {
        return dir_pass;
    }

    public void setDir_pass(String dir_pass) {
        this.dir_pass = dir_pass;
    }



}