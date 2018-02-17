package com.systemplus.webservice.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.systemplus.webservice.R;
import com.systemplus.webservice.api.ApiClient;
import com.systemplus.webservice.api.ApiInterface;
import com.systemplus.webservice.api.GoldApiClient;
import com.systemplus.webservice.model.LoginRequest;
import com.systemplus.webservice.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityActivity extends BaseActivity {

    EditText edtPhoneNo, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        final ApiInterface apiService =
                GoldApiClient.getClient().create(ApiInterface.class);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = edtPhoneNo.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                LoginRequest loginRequest = new LoginRequest(phone, password);

                Call<LoginResponse> responseCall = apiService.requestLogin(loginRequest);

                showProgressDialog();
                responseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        hidProgressDialog();
                        LoginResponse loginResponse = response.body();
                        if(loginResponse.getCode().equals("200")){
                            showToast("Login successful");
                        }
                        else{
                            showToast("Login Failed");
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hidProgressDialog();
                    }
                });

            }
        });
    }
}
