package com.perples.recosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by haein on 2017-07-10.
 */
public interface Notice_ApiInterface {

    /*@POST("readnotices.php") //로그인 직후 default screen에 공지사항 title 띄우는 것*/
    @GET("/api/notices/")
    Call<List<Notice>> getNotices();

}
