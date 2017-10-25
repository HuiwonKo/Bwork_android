package com.perples.recosample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haein on 2017-05-02.
 */
public class SignUp extends Activity {

    EditText idEntry, pwEntry, nameEntry, emailEntry, phoneEntry, flextimeEntry;
    RadioGroup flex_radio_group;
    RadioButton flex_yes_radio, flex_no_radio;
    //LinearLayout flextimelayout;
    Button signupbtn1;

    boolean is_flextime;
    String flextime;
    String nick, password, username, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        idEntry = (EditText) findViewById(R.id.idEntry);
        pwEntry = (EditText) findViewById(R.id.pwEntry);
        nameEntry = (EditText) findViewById(R.id.nameEntry);
        emailEntry = (EditText) findViewById(R.id.emailEntry);
        phoneEntry = (EditText) findViewById(R.id.phoneEntry);
        flex_radio_group = (RadioGroup) findViewById(R.id.flex_radio_group);
        flex_yes_radio = (RadioButton) findViewById(R.id.flex_yes_radio);
        flex_no_radio = (RadioButton) findViewById(R.id.flex_no_radio);
        flextimeEntry = (EditText) findViewById(R.id.flextimeEntry);
        signupbtn1 = (Button) findViewById(R.id.signupbtn1);

        signupbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMembers();
                //finish(); //액티비티 종료
            }
        });

    }

    private void updateMembers()
    {
        String Nick = nameEntry.getText().toString();
        String Password = pwEntry.getText().toString();
        String Username = idEntry.getText().toString();
        String Email = emailEntry.getText().toString();
        String Phone = phoneEntry.getText().toString();
        String Is_flextime = Boolean.toString(is_flextime);
        String Flextime = flextimeEntry.getText().toString();

        SignUp_ApiInterface signUp_apiInterface = Retrofit_ApiClient.getApiClient().create(SignUp_ApiInterface.class);
        Call<SignUpClass> call = signUp_apiInterface.updateMembers(Username,Password,Email,Nick,Phone,Is_flextime,Flextime);

        call.enqueue(new Callback<SignUpClass>() {
            @Override
            public void onResponse(Call<SignUpClass> call, Response<SignUpClass> response) {

                SignUpClass signUpClass = response.body();
                Toast.makeText(SignUp.this, "회원가입이 완료되었습니다.",
                        Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<SignUpClass> call, Throwable t) {

                Toast.makeText(SignUp.this, "회원가입 실패!ㅠㅠ 다시 입력해주세용",
                        Toast.LENGTH_LONG).show();

            }
        });

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.flex_yes_radio:
                if (checked)
                    is_flextime = true;
                    /*flextimelayout.setVisibility(View.VISIBLE);*/
                    /*flextime = flextimeEntry.getText().toString();*/
                    break;
            case R.id.flex_no_radio:
                if (checked)
                    is_flextime = false;
                    /*flextime = "0";*/
                    break;
        }
    }

}
