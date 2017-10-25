package com.perples.recosample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.perples.recosample.ui.activities.LoginActivity;
import com.perples.recosample.ui.activities.UserListingActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

    private SignUpClass user;

    private String id;
    //private User_ApiInterface apiInterface;

    /*private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Notice_RecyclerAdapter adapter;
    private List<Notice> notices;
    private Notice_ApiInterface apiInterface;*/

    public static Context mContext; //*********

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mContext = this; //************8

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0); //*****
        final TextView nick_tv = (TextView) nav_header_view.findViewById(R.id.nick_tv);
        final TextView email_tv = (TextView) nav_header_view.findViewById(R.id.email_tv);

        Intent intent = getIntent(); //***
        String username = intent.getStringExtra("username");
        String id = intent.getStringExtra("id"); //***

        User_ApiInterface apiInterface = Retrofit_ApiClient.getApiClient().create(User_ApiInterface.class);

        Call<SignUpClass> call = apiInterface.getUser(id); //******

        call.enqueue(new Callback<SignUpClass>(){

            @Override
            public void onResponse(Call<SignUpClass> call, Response<SignUpClass> response) {
                user = response.body();
                nick_tv.setText(user.getNick());
                email_tv.setText(user.getEmail());
            }

            @Override
            public void onFailure(Call<SignUpClass> call, Throwable t) {
                Toast.makeText(NavigationActivity.this, "navigationBar에 nick과 email을 띄울 수 없어!", Toast.LENGTH_SHORT).show();
            }
        });

        /*recyclerView = (RecyclerView) findViewById(R.id.notice_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = Notice_ApiClient.getApiClient().create(Notice_ApiInterface.class);

        Call<List<Notice>> call = apiInterface.getNotices();

        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {

                notices = response.body();
                adapter = new Notice_RecyclerAdapter(notices);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
            }
        });*/


       /* Bundle bundle = new Bundle(); //*//*******fragment로 id 값 전달**********
        bundle.putString("id", id);
        Fragment f = new NavSecondLayout(); //출퇴근 관리 fragment
        f.setArguments(bundle);*/



        //application 실행 시 블루투스 허용 여부 묻기
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }

        DefaultFragment defaultFragment = new DefaultFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, defaultFragment, defaultFragment.getTag()).commit();

    }


    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            DefaultFragment defaultFragment = new DefaultFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, defaultFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.schedule_me){

            ManageFragment manageFragment = new ManageFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, manageFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(id == R.id.in_and_out){

            NavSecondLayout navSecondLayout = new NavSecondLayout();
            /*FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, navSecondLayout, navSecondLayout.getTag()).commit();*/
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, navSecondLayout);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(id == R.id.chat_with_me){
            /*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);*/
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // if logged in redirect the user to user listing activity
                UserListingActivity.startActivity(NavigationActivity.this);
            } else {
                // otherwise redirect the user to login activity
                LoginActivity.startIntent(NavigationActivity.this);
            }

        /*}else if(id == R.id.share){
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/

        /*} else if(id == R.id.slideshow){
            NavThirdLayout navThirdLayout = new NavThirdLayout();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, navThirdLayout);
            transaction.addToBackStack(null);
            transaction.commit();*/

        } else if(id == R.id.meet_up){ //*******
            SchedulerFragment schedulerFragment = new SchedulerFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, schedulerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getUserId(){
        Intent intent = getIntent(); //***
        String id = intent.getStringExtra("id");

        //String id = "10";

        return id;
    }
}

