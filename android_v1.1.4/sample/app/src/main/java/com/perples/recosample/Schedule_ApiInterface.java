package com.perples.recosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by haein on 2017-08-30.
 */
public interface Schedule_ApiInterface {

    @GET("/api/meetings/meeting/")  // 회의 목록 받아올 때 이용
    Call<List<Schedule>> getSchedules();

    @FormUrlEncoded
    @POST("/api/meetings/meeting/create/")  // 회의 등록할 때 이용*******
    Call<Schedule> updateSchedule(@Field("plan") String plan, @Field("location") String location,
                                  @Field("time") String time);


    @FormUrlEncoded
    @POST("/api/meetings/participant/create/")  // 참석자 등록할 때 이용******(미팅 pk, 유저 pk) 전달
    Call<Participant> updateParticipant(@Field("user") int user, @Field("meeting") int meeting, @Field("custom_pk") String custom_pk);

    @GET("/api/meetings/meeting/name/") //meeting name으로 meeting pk를 받아오기 위한 url
    Call<List<Schedule>> findMeeting(@Query("plan") String plan);

    @GET("/api/meetings/participant/{pk}/")  // 참석자 정보(회의록, 이름 등) 가져올 때 이용***
    Call<Participant> getParticipant(@Path("pk") String pk);

    @FormUrlEncoded
    @PUT("/api/meetings/participant/{pk}/update/")
    Call<Participant> participating(@Path("pk") String pk, @Field("is_participate") boolean is_participate, @Field("meeting") int meeting);

    @FormUrlEncoded
    @PUT ("/api/meetings/participant/{pk}/update/")  // 회의록 등록할 때 이용******* participant_pk = meetingpk-userpk
    Call<Participant> updateMinutes(@Path("pk") String pk, @Field("minutes") String minutes, @Field("meeting") int meeting);


    @GET("/api/meetings/participant/meeting/{meeting}/") //참석자 목록 받아오려구*****
    Call<List<Participant>> findParticipants(@Path("meeting") int meeting);
}
