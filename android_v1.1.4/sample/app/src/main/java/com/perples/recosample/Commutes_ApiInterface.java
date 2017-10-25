package com.perples.recosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by haein on 2017-08-30.
 */
public interface Commutes_ApiInterface {

    @FormUrlEncoded
    @POST("/api/commutes/create/")
    Call<CommutesClass> commutes_in(@Field("user") String user, @Field("is_in") boolean is_in, @Field("in_time") String in_time, @Field("custom_pk") String custom_pk);

    @FormUrlEncoded
    @PUT("/api/commutes/{custom_pk}/update/")
    Call<CommutesClass> commutes_out(@Path("custom_pk") String custom_pk,
                                     @Field("user") String user, @Field("is_in") boolean is_in, @Field("is_out") boolean is_out, @Field("out_time") String out_time);


    @GET("/api/commutes/user/{user_pk}/")
    Call<List<CommutesClass>> getAllInfo(@Path("user_pk") int user_pk);
}
