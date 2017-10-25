package com.perples.recosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by haein on 2017-08-17.
 */
public interface User_ApiInterface {

    @GET("/api/accounts/{id}/")
    Call<SignUpClass> getUser(@Path("id") String id);

    @GET("/api/accounts/user/") //전체 유저 list가 있는 url
    Call<List<SignUpClass>> findUser(@Query("username") String username);

}
