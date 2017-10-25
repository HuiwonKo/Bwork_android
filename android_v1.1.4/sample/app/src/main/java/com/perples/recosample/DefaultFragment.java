package com.perples.recosample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Notice_RecyclerAdapter adapter;
    private List<Notice> notices;
    private Notice_ApiInterface apiInterface;
    private ImageButton detailbtn;

    private CalendarView calendarView;

    public DefaultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_default, container, false);

        calendarView = (CalendarView) rootView.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                Toast.makeText(getActivity(), "" + year + "/" +
                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.notice_recyclerView);
        recyclerView.setHasFixedSize(true);
        //adapter = new Notice_RecyclerAdapter(notices);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        detailbtn = (ImageButton) rootView.findViewById(R.id.detailbtn);
        detailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment = new NoticeFragment();
                //FragmentManager manager = getFragmentManager();
                //manager.beginTransaction().replace(R.id.container, noticeFragment, noticeFragment.getTag()).commit();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, noticeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        apiInterface = Retrofit_ApiClient.getApiClient().create(Notice_ApiInterface.class);

        Call<List<Notice>> call = apiInterface.getNotices();

        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {

                //Toast.makeText(getActivity(), "서버에 연결 성공!", Toast.LENGTH_LONG).show();
                notices = response.body();
                adapter = new Notice_RecyclerAdapter(notices);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Toast.makeText(getActivity(), "서버에 연결 실패", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

}
