package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-08-30.
 */
public class CommutesClass {

    @SerializedName("in_time")
    private String In_time;

    @SerializedName("out_time")
    private String Out_time;

    @SerializedName("shift")
    private String Shift;


    public CommutesClass(String In_time, String Out_time, String shift){
        this.In_time = In_time;
        this.Out_time = Out_time;
        this.Shift = shift;
    }

    public CommutesClass() {

    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }


    public String getIn_time() {
        return In_time;
    }

    public void setIn_time(String in_time) {
        In_time = in_time;
    }

    public String getOut_time() {
        return Out_time;
    }

    public void setOut_time(String out_time) {
        Out_time = out_time;
    }
}
