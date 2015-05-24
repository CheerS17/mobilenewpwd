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

    private Button mRegisterButton;
    private Button mLoginButton;
    private Button loginButtonPlease;
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



        mAccount = (EditText) findViewById(R.id.login_edit_accountt);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);

        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        loginButtonPlease = (Button) findViewById(R.id.login_btn_login_please);
//        loginView=findViewById(R.id.login_view);
//        loginSuccessView=findViewById(R.id.login_success_view);
//        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        mRegisterButton.setOnClickListener(mListener);
        mLoginButton.setOnClickListener(mListener);
        loginButtonPlease.setOnClickListener(mListener);

        User user = userDataManager.getUser();
        if (user.getPhone() != null) {
            mAccount.setVisibility(EditText.INVISIBLE);
            mRegisterButton.setVisibility(View.INVISIBLE);
            loginButtonPlease.setVisibility(View.INVISIBLE);
        } else {
            mLoginButton.setVisibility(View.INVISIBLE);
        }

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:
                    register();
                    break;
                case R.id.login_btn_login:
                    login();
                    break;
                case R.id.login_btn_login_please:
                    login_please();
                    break;

            }
        }
    };

    public void register() {

        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     *
     */
    public void login() {

        if (verify(userDataManager.getUser().getPhone(), mPwd.getText().toString())) {
            intent.setClass(this, homeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"输入的用户名或者密码有误!", Toast.LENGTH_SHORT).show();
            mPwd.setText("");
        }
    }

    /**
     *
     */
    public void login_please() {

        if (verify(mAccount.getText().toString(), mPwd.getText().toString())) {
            User user = new User();
            user.setPhone(mAccount.getText().toString());
            user.setPwd(mPwd.getText().toString());
            user.setToken("123123");
            userDataManager.insertUserData(user);
            intent.setClass(this, homeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"输入的用户名或者密码有误!", Toast.LENGTH_SHORT).show();
            mPwd.setText("");
        }
    }

    private Boolean verify(String phone, String pwd) {
        return true;
    }
}
