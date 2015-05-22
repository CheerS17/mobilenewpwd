package com.example.mobileonepwd.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mobileonepwd.R;
import com.example.mobileonepwd.dao.UserDataManager;
import com.example.mobileonepwd.entity.User;

public class loginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private EditText mAccount;
    private EditText mPwd;
    private Button mLogoffButton;
    private Button mLoginButton;
    private Intent intent = new Intent();
    private UserDataManager userDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        if (userDataManager == null) {
            userDataManager = new UserDataManager(this);
            userDataManager.openDataBase();
        }

        User user = userDataManager.getUser();
        if (user.getPhone() == null) {
            register();
        }

//        mAccount = (EditText) findViewById(R.id.login_edit_accountt);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mLogoffButton = (Button) findViewById(R.id.login_btn_logoff);
        mLogoffButton.setVisibility(Button.INVISIBLE);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
//        loginView=findViewById(R.id.login_view);
//        loginSuccessView=findViewById(R.id.login_success_view);
//        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        mLogoffButton.setOnClickListener(mListener);
        mLoginButton.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_logoff:
//                    register();
                    break;
                case R.id.login_btn_login:
                    login();
                    break;
            }
        }
    };

    public void register() {

        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login() {
        if (true/*服务器验证信息正确*/) {
            intent.setClass(this, homeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"输入的用户名或者密码有误!", Toast.LENGTH_SHORT).show();
            mPwd.setText("");
        }
    }
}
