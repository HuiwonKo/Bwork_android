package com.perples.recosample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<CommutesClass> commutesList;
    private Manage_RecyclerAdapter adapter;
    private Commutes_ApiInterface apiInterface;

    private TextView finalShiftTimeTv;

    private SignUpClass user;
    int user_pk;
    String flextime;

    public ManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.manage_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        final CurrentUser currentUser= new CurrentUser(NavigationActivity.mContext); //***
        user_pk = Integer.parseInt(currentUser.getCurrentUserId());

        apiInterface = Retrofit_ApiClient.getApiClient().create(Commutes_ApiInterface.class);
        Call<List<CommutesClass>> call = apiInterface.getAllInfo(user_pk);
        call.enqueue(new Callback<List<CommutesClass>>() {
            @Override
            public void onResponse(Call<List<CommutesClass>> call, Response<List<CommutesClass>> response) {
                commutesList = response.body();
                adapter = new Manage_RecyclerAdapter(commutesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CommutesClass>> call, Throwable t) {

            }
        });

        finalShiftTimeTv = (TextView) rootView.findViewById(R.id.finalShiftTimeTv);

        /*int shift = 0;
        List<CommutesClass> commutesClasses = ((Manage_RecyclerAdapter) adapter).getCommutesClasses();

        for (int i = 0; i < commutesClasses.size(); i++) {
            CommutesClass singleCommutes = commutesClasses.get(i);
            shift = shift + Integer.parseInt(singleCommutes.getShift());
        }
*/
        User_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(User_ApiInterface.class);
        Call<SignUpClass> calll = apiInterface.getUser(String.valueOf(user_pk)); //******
        calll.enqueue(new Callback<SignUpClass>(){

            @Override
            public void onResponse(Call<SignUpClass> call, Response<SignUpClass> response) {
                user = response.body();
                flextime = user.getFlextime();
            }

            @Override
            public void onFailure(Call<SignUpClass> call, Throwable t) {
            }
        });

        /*finalShiftTimeTv.setText(String.valueOf(shift) + " / " + flextime + "시간");*/
        finalShiftTimeTv.setText("8 시간 / 40 시간");

        return rootView;
    }

}
