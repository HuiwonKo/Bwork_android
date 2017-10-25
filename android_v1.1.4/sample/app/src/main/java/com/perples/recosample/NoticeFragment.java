package com.perples.recosample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NoticeFragment_RecyclerAdapter adapter;
    private List<Notice> notices;
    private Notice_ApiInterface apiInterface;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.notice_detail_recyclerView);
        recyclerView.setHasFixedSize(true);
        //adapter = new Notice_RecyclerAdapter(notices);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = Retrofit_ApiClient.getApiClient().create(Notice_ApiInterface.class);

        Call<List<Notice>> call = apiInterface.getNotices();

        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {

                notices = response.body();
                adapter = new NoticeFragment_RecyclerAdapter(notices);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
            }
        });

        return rootView;
    }

}
