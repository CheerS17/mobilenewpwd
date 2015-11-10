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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CheerS17 on 5/20/15.
 */
public class RegisterActivity extends Activity {

    private EditText phoneNumber;
    private EditText pwd;
    private EditText secPwd;
    private EditText easyPwd;

    private Button registerButton;
    private Button cancelButton;

    private UserDataManager userDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        phoneNumber = (EditText) findViewById(R.id.register_edit_account);
        pwd = (EditText) findViewById(R.id.register_edit_pwd);
        easyPwd = (EditText) findViewById(R.id.register_edit_easepwd);
        secPwd = (EditText) findViewById(R.id.register_edit_secpwd);

        easyPwd.setVisibility(View.INVISIBLE);

        registerButton = (Button) findViewById(R.id.register_btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.getText().toString().equals(secPwd.getText().toString())) {
                    if (userDataManager == null) {
                        userDataManager = new UserDataManager(RegisterActivity.this);
                        userDataManager.openDataBase();
                    }
                    User user = new User();
                    user.setPhone(phoneNumber.getText().toString());
                    user.setPwd(secPwd.getText().toString());
                    user.setToken("123123");
                    userDataManager.insertUserData(user);
                    Toast.makeText(getApplicationContext(), "注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, loginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "2次密码不一致",Toast.LENGTH_LONG).show();
                    pwd.setText("");
                    secPwd.setText("");
                }
            }
        });

    }

    public static String toSHA1(String value) {
        byte[] convertme = value.getBytes();
        final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }catch(NoSuchAlgorithmException e) {
        }
        byte[] buf = md.digest(convertme);
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

}
