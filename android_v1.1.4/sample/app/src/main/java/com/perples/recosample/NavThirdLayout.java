package com.perples.recosample;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by haein on 2017-08-07.
 */
public class NavThirdLayout extends Fragment implements RECOServiceConnectListener, RECORangingListener {

    View v;

    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;
    private Button location_button;
    private EditText location_editText;

    String location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_location, container, false);

        location_button = (Button) v.findViewById(R.id.location_button);
        location_editText = (EditText) v.findViewById(R.id.location_editText);

        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_editText.setText(location);
            }
        });

        //RECOBeaconManager의 인스턴스를 생성합니다
        //RECOBeaconManager.getInstance(Context, boolean, boolean)의 경우,
        //Context, RECO 비콘만을 대상으로 동작 여부를 설정하는 값, 그리고 백그라운드 monitoring 중 ranging 시 timeout을 설정하는 값을 매개변수로 받습니다.
        recoManager = RECOBeaconManager.getInstance(this.getActivity(), true, true);
        recoManager.bind(this);
        recoManager.setRangingListener(this);

        return v;
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

    //1초마다 비콘변화 감지
    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {
        // TODO Auto-generated method stub

        if (arg1.getMajor() == 501) {
            if (arg0.size() != 0) {
                location = "@ 회의실";
            }

        } else if (arg1.getMajor() == 502) {
            if (arg0.size() != 0) {
                location = "@ 사무실";
            }
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode recoErrorCode) {

    }
}
