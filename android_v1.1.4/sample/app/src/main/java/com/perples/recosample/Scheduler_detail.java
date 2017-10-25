package com.perples.recosample;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scheduler_detail extends AppCompatActivity implements RECOServiceConnectListener, RECORangingListener {

    TextView tx_plan, tx_time, tx_location;
    EditText et_minutes;
    Button btn_minutes_save, btn_participate;
    ImageButton btn_finish;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Participant_RecyclerAdapter adapter;
    private List<Participant> participants;
    private Schedule_ApiInterface apiInterface;


    private Participant participant;
    private String participant_pk;//***************************
    private int meeting_pk;
    private String pk;
    private String user_pk;
    private String plan;
    String beacon_location;
    //Date schedule_time_date;
    String location, time;

    private List<Schedule> schedules;
    private Schedule_ApiInterface schedule_apiInterface;

    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_detail);

        btn_finish = (ImageButton)findViewById(R.id.finishBtn1);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.participant_recyclerView);
        recyclerView.setHasFixedSize(true);
        //adapter = new Notice_RecyclerAdapter(notices);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //RECOBeaconManager의 인스턴스를 생성합니다
        //RECOBeaconManager.getInstance(Context, boolean, boolean)의 경우,
        //Context, RECO 비콘만을 대상으로 동작 여부를 설정하는 값, 그리고 백그라운드 monitoring 중 ranging 시 timeout을 설정하는 값을 매개변수로 받습니다.
        recoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, true);
        recoManager.bind(this);
        recoManager.setRangingListener(this);

        // final CurrentUser currentUser= new CurrentUser(getContext());
        final CurrentUser currentUser= new CurrentUser(NavigationActivity.mContext); //***
        //Toast.makeText(this, currentUser.getCurrentUserId(), Toast.LENGTH_LONG).show();
        user_pk = currentUser.getCurrentUserId(); //**** current user id를 넘겨받았다!

        tx_plan = (TextView) findViewById(R.id.d_plan);
        tx_location = (TextView) findViewById(R.id.d_location);
        tx_time = (TextView) findViewById(R.id.d_time);

        tx_plan.setText(getIntent().getStringExtra("plan"));
        tx_location.setText(getIntent().getStringExtra("location"));
        tx_time.setText(getIntent().getStringExtra("time"));

        et_minutes = (EditText) findViewById(R.id.minutes);
        plan = getIntent().getStringExtra("plan")+"/";
        location = getIntent().getStringExtra("location");
        time = getIntent().getStringExtra("time");

        schedule_apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
        Call<List<Schedule>> calll = schedule_apiInterface.findMeeting(plan);
        calll.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                schedules = response.body();
                pk = schedules.get(0).getPk().toString();

                meeting_pk = Integer.parseInt(pk);
                Toast.makeText(getApplicationContext(), pk, Toast.LENGTH_SHORT).show();
                participant_pk = meeting_pk + "-" + user_pk; //****

                //참석자 목록 띄우기
                apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                Call<List<Participant>> call4 = apiInterface.findParticipants(meeting_pk);
                call4.enqueue(new Callback<List<Participant>>() {
                    @Override
                    public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                        participants = response.body();
                        adapter = new Participant_RecyclerAdapter(participants);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Participant>> call, Throwable t) {

                    }
                });

                //회의록 띄우기
                Schedule_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                //Call<Participant> call = apiInterface.getParticipant(participant_pk); //******
                Call<Participant> call1 = apiInterface.getParticipant(participant_pk); //******
                call1.enqueue(new Callback<Participant>() {
                    @Override
                    public void onResponse(Call<Participant> call, Response<Participant> response) {
                        participant = response.body();

                    if(participant.getMinutes().equals("회의록을 적어주세요")){
                        et_minutes.setText(null);
                    }else{
                        et_minutes.setText(participant.getMinutes());
                     }
                    }

                    @Override
                    public void onFailure(Call<Participant> call, Throwable t) {

                    }
                });

                btn_minutes_save = (Button) findViewById(R.id.minutessavebtn);
                btn_minutes_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //String minutes = et_minutes.getText().toString();
                        Schedule_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                        Call<Participant> call2 = apiInterface.updateMinutes(participant_pk, et_minutes.getText().toString(), meeting_pk); //******
                        call2.enqueue(new Callback<Participant>() {
                            @Override
                            public void onResponse(Call<Participant> call, Response<Participant> response) {
                                //participant = response.body();
                                Toast.makeText(Scheduler_detail.this, "회의록이 등록되었습니다!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Participant> call, Throwable t) {

                            }
                        });
                    }
                });

                btn_participate = (Button) findViewById(R.id.participatebtn);
                Schedule_ApiInterface schedule_apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                Call<Participant> call3 = schedule_apiInterface.getParticipant(participant_pk);
                call3.enqueue(new Callback<Participant>() {
                    @Override
                    public void onResponse(Call<Participant> call, Response<Participant> response) {
                        if(response.body().getIs_participate() == true){
                            btn_participate.setText("참석");
                        }else{
                            btn_participate.setText("미참석");
                        }
                    }

                    @Override
                    public void onFailure(Call<Participant> call, Throwable t) {

                    }
                });


                btn_participate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //만약 장소(Reco)와 시간(currentTime)이 맞으면 (이거 아직 안 짬, 여기다 짜야 돼) 유저 pk, 회의 pk 묶어서 넘겨줄 것

                        //if(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(time)
                        if(time.equals(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(new Date()))){
                            if(location.equals(beacon_location)){
                                //boolean is_participate = true;
                                Schedule_ApiInterface schedule_apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class); //***
                                Call<Participant> call = schedule_apiInterface.participating(participant_pk, true, meeting_pk); //***
                                call.enqueue(new Callback<Participant>() {
                                    @Override
                                    public void onResponse(Call<Participant> call, Response<Participant> response) {
                                        btn_participate.setText("참석");
                                    }

                                    @Override
                                    public void onFailure(Call<Participant> call, Throwable t) {

                                    }
                                });
                            } else{
                                Toast.makeText(getApplicationContext(), "회의 장소가 아닙니다!", Toast.LENGTH_LONG).show();
                            }
                        } else{
                            Toast.makeText(getApplicationContext(), "회의 시각이 아닙니다!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {

            }
        });

        // 회의록 등록할 때 이용******* participant_pk = meetingpk-userpk
    }



    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {
        if (arg1.getMajor() == 501) {
            if (arg0.size() != 0) {
                beacon_location = "Seoul161";
            }

        } else if (arg1.getMajor() == 502) {
            if (arg0.size() != 0) {
                beacon_location = "사무실";
            }
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode recoErrorCode) {

    }

    @Override
    public void onServiceConnect() {
        //리스트 생성해서 비콘 값 저장
        rangingRegions = new ArrayList<RECOBeaconRegion>();
        //비콘의UUID, Major, 이름
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, "회의실"));
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 502, "사무실"));

        for (RECOBeaconRegion region : rangingRegions) {
            try {
                recoManager.startRangingBeaconsInRegion(region);
                recoManager.requestStateForRegion(region);
            } catch (RemoteException e) {
                // Remot
                // eException 발생 시 작성 코드

            } catch (NullPointerException e) {
                // NullPointerException 발생 시 작성 코드

            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode recoErrorCode) {

    }
}
