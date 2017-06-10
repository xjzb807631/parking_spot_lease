package com.example.acer.parking;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.SharedPreferences;
import android.view.*;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Toast;
import data.*;
import client.*;

public class load extends AppCompatActivity
{

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑




    void doPost_Login()
    {
        String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
        String userPwd = mPwd.getText().toString().trim();
        Account account=new Account(userName,null,userPwd);
        ClientAccountHandler clientAccountHandler=new ClientAccountHandler();
        clientAccountHandler.login(account);
        Intent intent_Login_to_user = new Intent(load.this,user.class) ;
        startActivity(intent_Login_to_user);
    }

    void doPost_Register()
    {
        Intent intent_Login_to_Register = new Intent(load.this,register.class) ;    //切换Login Activity至User Activity
        startActivity(intent_Login_to_Register);
    }

    void doPost_ForgetPassword()
    {
        Intent intent_Login_to_ForgetPassword=new Intent(load.this,forget_password.class);
        startActivity(intent_Login_to_ForgetPassword);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.flush();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        mAccount = (EditText) findViewById(R.id.edit_user_id);
        mPwd = (EditText) findViewById(R.id.edit_user_password);
        Button mRegisterButton = (Button) findViewById(R.id.register);
        Button mLoginButton = (Button) findViewById(R.id.load);
        Button mForgetPasswordButton=(Button) findViewById(R.id.forget_password);
        //通过id找到相应的控件
        mLoginButton.setOnClickListener(view ->doPost_Login());

        mRegisterButton.setOnClickListener(view->doPost_Register());

        mForgetPasswordButton.setOnClickListener(view->doPost_ForgetPassword());


    }
}
