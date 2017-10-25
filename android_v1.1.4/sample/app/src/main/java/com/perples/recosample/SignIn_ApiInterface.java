package com.perples.recosample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haein on 2017-07-13.
 */
public interface SignIn_ApiInterface {

    @FormUrlEncoded
    @POST("/api/accounts/api-token-auth/")//로그인을 구현한 서버측 파일
    Call<SignUpClass> signin(@Field("username") String username, @Field("password") String password);


    //***skip 가능
/*
    @POST("/api/accounts/api-token-auth/")//token값을 전달하는 서버측 파일
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);*/
}
