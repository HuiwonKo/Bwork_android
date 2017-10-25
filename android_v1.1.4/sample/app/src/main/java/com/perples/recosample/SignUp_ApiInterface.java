package com.perples.recosample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haein on 2017-07-06.
 */

public interface SignUp_ApiInterface {

    @FormUrlEncoded
    @POST("/api/accounts/create/")  //서버측 회원 가입 코드 파일
    Call<SignUpClass> updateMembers(@Field("username") String username, @Field("password") String password, @Field("email") String email,
                         @Field("nick") String nick, @Field("phone") String phone,
                                    @Field("is_flextime") String is_flextime, @Field("flextime") String flextime);

}
