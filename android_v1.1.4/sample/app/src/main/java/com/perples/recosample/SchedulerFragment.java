package com.perples.recosample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haein on 2017-08-30.
 */
public class SchedulerFragment extends Fragment{

    SchedulerDialog schedulerDialog; //****************

    private String pk;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Scheduler_RecyclerAdapter adapter;
    private List<Schedule> schedules;
    private Schedule_ApiInterface apiInterface;

    private ImageButton addbtn;

    public SchedulerFragment() {
        // Required empty public constructor
    }

    //private CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scheduler, container, false);

        schedulerDialog = new SchedulerDialog(getActivity()); ///****************

        /*calendarView = (CalendarView) v.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                *//*Toast.makeText(getActivity(), "" + year + "/" +
                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();*//*
                schedulerDialog.show();
            }
        });*/

        final CurrentUser currentUser= new CurrentUser(getContext()); //*****************
        addbtn = (ImageButton) v.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppUsersActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity(),currentUser.getCurrentUserId(), Toast.LENGTH_SHORT).show();//****************
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.schedule_recyclerView);
        recyclerView.setHasFixedSize(true);
        //adapter = new Notice_RecyclerAdapter(notices);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = Retrofit_ApiClient.getApiClient().create(Schedule_ApiInterface.class);

        Call<List<Schedule>> call = apiInterface.getSchedules();

        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {

                schedules = response.body();
                adapter = new Scheduler_RecyclerAdapter(schedules, getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
            }
        });

        /*recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                        Intent intent = new Intent(getActivity(), AppUsersActivity.class); /*//******
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/

        return v;
    }
}
