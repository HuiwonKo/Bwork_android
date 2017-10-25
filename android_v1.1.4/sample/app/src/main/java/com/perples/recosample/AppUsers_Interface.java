package com.perples.recosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by haein on 2017-08-31.
 */
public interface AppUsers_Interface {

    @GET("/api/accounts/")
    Call<List<AppUser>> getAppUsers();
}
