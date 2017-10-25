package com.perples.recosample;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by haein on 2017-05-26.
 */
public class NavFirstLayout extends Fragment implements RECOServiceConnectListener, RECORangingListener {
    View v;

    private TextView tv;
    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_first_layout, container, false);

        tv = (TextView) v.findViewById(R.id.textView2);

        tv.setMaxLines(60);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());

        tv.setText("Beacon시작");

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
        tv.setText(tv.getText() + "\n연결되었습니다");
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
                // eException 발생 시 작성 코드
            } catch (NullPointerException e) {
                // NullPointerException 발생 시 작성 코드
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode arg0) {
        // TODO Auto-generated method stub

    }


    //1초마다 비콘변화 감지
    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {
        // TODO Auto-generated method stub

        //if (arg0.size() == 0) {
        //    tv.setText(tv.getText() + "\n" + arg1.getUniqueIdentifier() + " 탈주!");
        //} else {
        //    tv.setText(tv.getText() + "\n" + arg1.getUniqueIdentifier() + " 있음!");
        //}
        if (arg1.getMajor() == 501) {
            if (arg0.size() == 0) {
                tv.setText(tv.getText() + "\n회의실 OUT");
            } else {
                tv.setText(tv.getText() + "\n회의실 IN");
            }

        } else if (arg1.getMajor() == 502) {
            if (arg0.size() == 0) {
                tv.setText(tv.getText() + "\n사무실 OUT");
            } else {
                tv.setText(tv.getText() + "\n사무실 IN");
            }
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion arg0, RECOErrorCode arg1) {
        // TODO Auto-generated method stub
        tv.setText(tv.getText() + "\n실패하였습니다.");
    }
}
