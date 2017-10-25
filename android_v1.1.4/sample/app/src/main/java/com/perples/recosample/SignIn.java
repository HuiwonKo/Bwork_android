package com.perples.recosample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haein on 2017-05-02.
 */
public class SignIn extends Activity {

    EditText idEntry;
    EditText passwordEntry;
    Button signinbtn, freepassbtn;

    String username, password;

    /*Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://192.168.0.24:8000/") //서버 앱 주소
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    SignIn_ApiInterface signIn_apiInterface = retrofit.create(SignIn_ApiInterface.class);*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        idEntry = (EditText)findViewById(R.id.idEntry);
        passwordEntry = (EditText) findViewById(R.id.passwordEntry);
        signinbtn = (Button) findViewById(R.id.signinbtn);

        //freepassbtn = (Button) findViewById(R.id.freepassbtn);

        signinbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signin();
            }
        } );

       /* freepassbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });*/
    }

    //private static String token;
    private static String id;

    private void signin(){

        //SignInClass signInClass = new SignInClass(idEntry.getText().toString(), passwordEntry.getText().toString());
        //Call<SignUpClass> call = signIn_apiInterface.signin(signInClass);

        final String Username = idEntry.getText().toString();
        String Password = passwordEntry.getText().toString();

        SignIn_ApiInterface signIn_apiInterface = Retrofit_ApiClient.getApiClient().create(SignIn_ApiInterface.class); //***
        Call<SignUpClass> call = signIn_apiInterface.signin(Username, Password); //***

        call.enqueue(new Callback<SignUpClass>() {
            @Override
            public void onResponse(Call<SignUpClass> call, Response<SignUpClass> response) {
                if(response.isSuccessful()){

                    id = response.body().getId().toString();
                    //token = response.body().getToken().toString();//토큰 값 저장

                    //Toast.makeText(SignIn.this, id, Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);

                    intent.putExtra("username", Username); //*****
                    intent.putExtra("id", id); //*****

                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignIn.this, "signin not correct:(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpClass> call, Throwable t) {
                Toast.makeText(SignIn.this, "error:(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void getSecret(){ //로그인을 한 다음에야 토큰이 생성되었음을 확인 가능
        SignIn_ApiInterface signIn_apiInterface = Retrofit_ApiClient.getApiClient().create(SignIn_ApiInterface.class); /*//***

        Call<ResponseBody> call = signIn_apiInterface.getSecret(token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Toast.makeText(SignIn.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(SignIn.this, "token is not correct:(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignIn.this, "error:(", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}