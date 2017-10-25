package com.perples.recosample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haein on 2017-05-26.
 */
public class NavSecondLayout extends Fragment implements RECOServiceConnectListener, RECORangingListener, RECOMonitoringListener {
    View v;
    private TextView todaytv;
    private TextView connection;
    private TextView isCommute_in;
    private TextView isCommute_out;
    private TextView commute_in_time;
    private TextView commute_out_time;
    private TextView sugo;

    private String today;
    private String date;
    private String id;
    private String custom_pk;
    private String in_time;
    private boolean is_in, is_out;
    private String out_time;

    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;
    private RecoMonitoringListAdapter mMonitoringListAdapter;
    private boolean mInitialSetting = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_second_layout, container, false);

        todaytv = (TextView) v.findViewById(R.id.todaytv);
        sugo = (TextView) v.findViewById(R.id.sugo);

        isCommute_in = (TextView) v.findViewById(R.id.isCommute_in);
        isCommute_out = (TextView) v.findViewById(R.id.isCommute_out);
        commute_in_time = (TextView) v.findViewById(R.id.commute_in_time);
        commute_out_time = (TextView) v.findViewById(R.id.commute_out_time);

        today = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(new Date());
        date = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
        todaytv.setText(today);

        /*Bundle bundle = getArguments();  /*//*****NavigationActivity로부터 id값 받기*******************8
        id = bundle.getString("id");*/

        final CurrentUser currentUser= new CurrentUser(getContext()); //*****************
        id = currentUser.getCurrentUserId();
        custom_pk = id+"-"+date;
        //custom_pk = "a";

        //RECOBeaconManager의 인스턴스를 생성합니다
        //RECOBeaconManager.getInstance(Context, boolean, boolean)의 경우,
        //Context, RECO 비콘만을 대상으로 동작 여부를 설정하는 값, 그리고 백그라운드 monitoring 중 ranging 시 timeout을 설정하는 값을 매개변수로 받습니다.
        recoManager = RECOBeaconManager.getInstance(this.getActivity(), true, true);
        recoManager.bind(this);
        recoManager.setRangingListener(this);

        return v;
    }

    //연결이 되었을때 제공되는 서비스 부분
    @Override
    public void onServiceConnect() {
        //connection.setText(connection.getText() + "\n연결되었습니다");

        //리스트에 비콘 값 저장
        rangingRegions = new ArrayList<RECOBeaconRegion>();
        //비콘의UUID, Major, 이름
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, "회사 출입구1"));
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 502, "회사 출입구2"));

        for (RECOBeaconRegion region : rangingRegions) {
            try {
                recoManager.startRangingBeaconsInRegion(region);
                recoManager.requestStateForRegion(region);
            } catch (RemoteException e) {
                // RemoteException 발생 시 작성 코드

            } catch (NullPointerException e) {
                // NullPointerException 발생 시 작성 코드

            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode arg0) {
        // TODO Auto-generated method stub

    }

    int n=2;
    //1초마다 비콘변화 감지
    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {
        // TODO Auto-generated method stub
        //int n = 2; //count variable
        //*****DB에 scan한 값을 다 일단 넣고 그 중 MIN, MAX를 가져와서 각각 출근, 퇴근에 넣기(수정)

        if (arg1.getMajor() == 501 || arg1.getMajor() == 502) {
            if (arg0.size() != 0) { //in 상태이고 최초 region 접근 시
                if (n == 2) {
                    //나중에 서버에서 받아올 때 시각이 default값이 아니면 출근완료 처리
                    /*isCommute_in.setText("출근 완료");
                    isCommute_in.setTextColor(Color.RED);
                    n++;
                    String in_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date());
                    commute_in_time.setText(in_time);*/

                    n++;
                    in_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date());
                    is_in = true;

                    Commutes_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(Commutes_ApiInterface.class);
                    Call<CommutesClass> call = apiInterface.commutes_in(id, is_in, in_time, custom_pk);
                    call.enqueue(new Callback<CommutesClass>() {
                        @Override
                        public void onResponse(Call<CommutesClass> call, Response<CommutesClass> response) {
                            isCommute_in.setText("출근 완료");
                            isCommute_in.setTextColor(Color.RED);
                            commute_in_time.setText(in_time);
                            //다음 접근 시 출근 완료, 출근 시각은 일단 이거 되면 onCreateView에서 따로 또 retrofit으로 서버에서 받아오게 짜기
                        }

                        @Override
                        public void onFailure(Call<CommutesClass> call, Throwable t) {
                            Toast.makeText(getActivity(), "출근 정보 전송 실패!", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    if (n > 2) {
                        if(n < 6){
                            n++;
                        }else {
                            //퇴근하시겠습니까 팝업 창 띄우고 YES면 퇴근 처리 / 나중에 서버에서 받아올 때 시각이 default값이 아니면 퇴근완료 처리
                            /*isCommute_in.setTextColor(Color.GRAY);
                            isCommute_out.setText("퇴근 완료");
                            isCommute_out.setTextColor(Color.CYAN);
                            //n++;
                            n = 1;
                            String out_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date());
                            commute_out_time.setText(out_time);*/

                            n = 1;
                            out_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date());
                            is_out = true;
                            is_in = true;

                            Commutes_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(Commutes_ApiInterface.class);
                            Call<CommutesClass> call = apiInterface.commutes_out(custom_pk, id, is_in, is_out, out_time);
                            call.enqueue(new Callback<CommutesClass>() {
                                @Override
                                public void onResponse(Call<CommutesClass> call, Response<CommutesClass> response) {
                                    isCommute_in.setTextColor(Color.GRAY);
                                    isCommute_out.setText("퇴근 완료");
                                    isCommute_out.setTextColor(Color.CYAN);
                                    //commute_in_time.setText(response.body().getIn_time());
                                    commute_out_time.setText(out_time);
                                    //다음 접근 시 퇴근 완료, 퇴근 시각은 일단 이거 되면 onCreateView에서 따로 또 retrofit으로 서버에서 받아오게 짜기
                                }

                                @Override
                                public void onFailure(Call<CommutesClass> call, Throwable t) {
                                    Toast.makeText(getActivity(), "퇴근 정보 전송 실패!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else{
                        if (n == 1) {
                            sugo.setText("수고했어, 오늘도!");
                        }
                    }
                }
            }
        }
    }


    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode recoErrorCode) {

    }


    @Override
    public void didEnterRegion(RECOBeaconRegion recoBeaconRegion, Collection<RECOBeacon> collection) {

    }

    @Override
    public void didExitRegion(RECOBeaconRegion recoBeaconRegion) {

    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion arg1) {

    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState recoRegionState, RECOBeaconRegion arg1) {
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode recoErrorCode) {

    }
}
