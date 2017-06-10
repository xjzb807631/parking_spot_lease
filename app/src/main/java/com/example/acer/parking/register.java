package com.example.acer.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import data.*;
import client.*;


public class register extends AppCompatActivity
{

    private EditText mAccount;                        //用户名编辑
    private EditText mNickName;                      //昵称便捷
    private EditText mRealName;                     //真实姓名便捷
    private EditText mIdentityNumber;               //身份证号编辑
    private EditText mWeChat;                      //微信号编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码重复编辑
    StringBuffer result;



    void doPost_sure()
    {
        String userName = mAccount.getText().toString().trim();
        String nickName = mNickName.getText().toString().trim();
        String realName = mRealName.getText().toString().trim();
        String identityNumber= mIdentityNumber.getText().toString().trim();
        String weChat = mWeChat.getText().toString().trim();
        String userPwd = mPwd.getText().toString().trim();
        String userPwdCheck = mPwdCheck.getText().toString().trim();
        if(userPwd.equals(userPwdCheck)==true)
        {
            User user = new User(userName, nickName, realName, identityNumber, weChat, userPwd);
            ClientAccountHandler clientAccountHandler = new ClientAccountHandler();
            clientAccountHandler.register(user,result);
            Intent intent_register_to_personal_information=new Intent(register.this,personal_information.class);
            startActivity(intent_register_to_personal_information);
        }
    }
    void doPost_cancel()
    {
        Intent intent_Register_to_Login = new Intent(register.this,load.class) ;    //切换User Activity至Login Activity
        startActivity(intent_Register_to_Login);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAccount = (EditText) findViewById(R.id.edit_account_number);
        mNickName = (EditText) findViewById(R.id.edit_nick_name);
        mRealName = (EditText) findViewById(R.id.edit_real_name);
        mIdentityNumber = (EditText) findViewById(R.id.edit_id_number);
        mWeChat = (EditText) findViewById(R.id.edit_we_chat_number);
        mPwd = (EditText) findViewById(R.id.edit_password);
        mPwdCheck = (EditText) findViewById(R.id.edit_password_confirm);

        Button mSureButton = (Button) findViewById(R.id.confirm);
        Button mCancelButton = (Button) findViewById(R.id.cancel);

        mSureButton.setOnClickListener(view->doPost_sure());

        mCancelButton.setOnClickListener(view->doPost_cancel());

    }

}

