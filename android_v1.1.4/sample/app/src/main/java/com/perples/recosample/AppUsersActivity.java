package com.perples.recosample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haein on 2017-08-30.
 */
public class AppUsersActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private List<SignUpClass> userclass;
    private User_ApiInterface user_apiInterface;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<AppUser> appUserList;
    private AppUsers_RecyclerAdapter adapter;
    private AppUsers_Interface apiInterface;

    private Button btnSelection, btnCreate;
    private ImageButton finishBtn;

    SchedulerDialog schedulerDialog;

    String pk;
    int user_pk, meeting_pk;
    String date, timetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appusers);

        //커스텀 다이얼로그 객체 생성
        schedulerDialog = new SchedulerDialog(AppUsersActivity.this);

        //커스텀 다이얼로그 객체의 Dismiss 리스너 설정
        //커스텀 다이얼로그가 사라졌을 때(Dismiss) 취할 행동들
        schedulerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {  //HashTable에 추가..
                /*String time, plan;  //일정(시간,내용)
                time = plandialog.getHour() + " : " + plandialog.getMinute();
                plan = plandialog.getPlan();
                listItem item = new listItem(time, plan);
                String key = curYear + "-" + curMonth + "-" + day;  //해쉬 Key

                if (ht.containsKey(key)) {  //키가 있으면 있는 ArrayList에 추가
                    ArrayList<listItem> al = ht.get(key);
                    ht.remove(key);
                    al.add(item);
                    ht.put(key, al);
                } else {  //없으면 새로운 ArrayList를 만들어서 추가
                    ArrayList<listItem> al = new ArrayList<>();
                    al.add(item);
                    ht.put(key, al);
                }
                curItem.setisPlan(true);*/

                String time, plan, location;

                //time = schedulerDialog.getHour() + " : " + schedulerDialog.getMinute();
                time = schedulerDialog.getDate()+" "+schedulerDialog.getTimetime(); //****
                plan = schedulerDialog.getPlan();
                location = schedulerDialog.getLocation();

                Schedule_ApiInterface schedule_apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                Call<Schedule> call = schedule_apiInterface.updateSchedule(plan, location, time);

                call.enqueue(new Callback<Schedule>() {
                    @Override
                    public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                        Schedule schedule = response.body();

                        pk = response.body().getPk(); //***

                        Toast.makeText(AppUsersActivity.this, "회의 저장 성공!\n" + pk, Toast.LENGTH_LONG).show();


                        //참석자 저장
                        List<AppUser> auList = ((AppUsers_RecyclerAdapter) adapter).getAuList();
                        for (int i = 0; i < auList.size(); i++) {
                            AppUser singleappuser = auList.get(i);
                            if (singleappuser.isSelected() == true) {
                                String user = singleappuser.getUsername().toString();

                                //************************
                                user_apiInterface = Retrofit_ApiClient.getApiClient().create(User_ApiInterface.class);
                                Call<List<SignUpClass>> calll = user_apiInterface.findUser(user); ///
                                calll.enqueue(new Callback<List<SignUpClass>>() {
                                    @Override
                                    public void onResponse(Call<List<SignUpClass>> call, Response<List<SignUpClass>> response) {
                                        userclass = response.body();
                                        user_pk = Integer.parseInt(userclass.get(0).getPk());
                                        meeting_pk = Integer.parseInt(pk);
                                        //***
                                        Schedule_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);
                                        Call<Participant> cal = apiInterface.updateParticipant(user_pk, meeting_pk, "a");
                                        cal.enqueue(new Callback<Participant>() {
                                            @Override
                                            public void onResponse(Call<Participant> call, Response<Participant> response) {
                                                Toast.makeText(AppUsersActivity.this, "참석자 저장 성공!"+user_pk, Toast.LENGTH_LONG).show();
                                            }

                                            @Override

                                            public void onFailure(Call<Participant> call, Throwable t) {
                                                Toast.makeText(AppUsersActivity.this, "참석자 저장 실패", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<List<SignUpClass>> call, Throwable t) {
                                        Toast.makeText(AppUsersActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<Schedule> call, Throwable t) {
                        Toast.makeText(AppUsersActivity.this, "회의 저장 실패", Toast.LENGTH_LONG).show();
                    }
                });

                //Toast.makeText(getApplicationContext(), "회의 저장\n" + new Date().toString(), Toast.LENGTH_SHORT).show();
                schedulerDialog.init();  //다이얼로그 editText 초기화
            }
        });

        schedulerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "회의 저장 취소", Toast.LENGTH_SHORT).show();
                schedulerDialog.init();  //다이얼로그 editText 초기화
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.appusers_recyclerView);
        recyclerView.setHasFixedSize(true);
        //adapter = new Notice_RecyclerAdapter(notices);
        //adapter = new AppUsers_RecyclerAdapter(appUserList); //안 되면 얘 빼 볼 것 ***
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = Retrofit_ApiClient.getApiClient().create(AppUsers_Interface.class);
        Call<List<AppUser>> call = apiInterface.getAppUsers();
        call.enqueue(new Callback<List<AppUser>>() {
            @Override
            public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                appUserList = response.body();
                adapter = new AppUsers_RecyclerAdapter(appUserList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AppUser>> call, Throwable t) {

            }
        });

        finishBtn = (ImageButton) findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSelection = (Button) findViewById(R.id.btnShow);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                List<AppUser> auList = ((AppUsers_RecyclerAdapter) adapter).getAuList();

                for (int i = 0; i < auList.size(); i++) {
                    AppUser singleappuser = auList.get(i);
                    if (singleappuser.isSelected() == true) {
                        data = data + "\n" + singleappuser.getUsername().toString();
                    }
                }

                Toast.makeText(AppUsersActivity.this, "선택된 사원: \n" + data, Toast.LENGTH_LONG).show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedulerDialog.show();
            }
        });
    }
}