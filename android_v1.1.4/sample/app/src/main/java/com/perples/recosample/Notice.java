package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-07-10.
 */
public class Notice {

    @SerializedName("title")
    private String Title;

    @SerializedName("content")
    private String Content;

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}
