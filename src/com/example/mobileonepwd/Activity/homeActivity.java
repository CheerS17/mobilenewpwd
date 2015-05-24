package com.example.mobileonepwd.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.mobileonepwd.R;
import com.example.mobileonepwd.dao.UserDataManager;
import com.example.mobileonepwd.entity.Info;
import com.example.mobileonepwd.entity.User;
import com.example.mobileonepwd.util.AES;

import java.text.DateFormat;
import java.util.*;

/**
 * Created by CheerS17 on 5/21/15.
 */
public class homeActivity extends Activity {

    private Button addNew;
    private Button logout;
    private Intent intent = new Intent();
    private ListView listView;
    private UserDataManager userDataManager;

    private String sitename;
    private String siteUsername;
    private int l;
    private int n;
    private int p;

    private static String plainText;

    private int mode;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        addNew = (Button) findViewById(R.id.home_add_new_button);
        logout = (Button) findViewById(R.id.home_logout_button);
        listView = (ListView) findViewById(R.id.home_listView);

        if (userDataManager == null) {
            userDataManager = new UserDataManager(this);
            userDataManager.openDataBase();
        }

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.vlist,
                new String[]{"sitename", "siteUsername", "time", "lNumber", "nNumber", "pNumber"},
                new int[]{R.id.vlist_sitename, R.id.vlist_siteUsername, R.id.vlist_time, R.id.vlist_lNumber, R.id.vlist_nNumber, R.id.vlist_pNumber});
//            listView = (ListView) findViewById(R.id.home_listView);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final View tempView = view;
                new AlertDialog.Builder(homeActivity.this).setTitle("复选框").setSingleChoiceItems(new String[]{"传送", "删除"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mode = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mode == 0) {

                            TextView sitename = (TextView) tempView.findViewById(R.id.vlist_sitename);
                            TextView siteUsername = (TextView) tempView.findViewById(R.id.vlist_siteUsername);

                            TextView lNumber = (TextView) tempView.findViewById(R.id.vlist_lNumber);
                            TextView nNumber = (TextView) tempView.findViewById(R.id.vlist_nNumber);
                            TextView pNumber = (TextView) tempView.findViewById(R.id.vlist_pNumber);

                            plainText = sitename.toString()+siteUsername.toString()+lNumber.toString()+nNumber.toString()+pNumber.toString();
                            Toast.makeText(homeActivity.this, sitename.getText().toString() + " " + mode + "", Toast.LENGTH_SHORT).show();

                            intent.setClass(homeActivity.this, CaptureActivity.class);
                            startActivityForResult(intent,101);
                        } else if (mode == 1) {
                            TextView sitename = (TextView) tempView.findViewById(R.id.vlist_sitename);
                            TextView siteUsername = (TextView) tempView.findViewById(R.id.vlist_siteUsername);

                            userDataManager.deleteUserData(sitename.getText().toString(), siteUsername.getText().toString());

                            SimpleAdapter adapter = new SimpleAdapter(homeActivity.this, getData(), R.layout.vlist,
                                    new String[]{"sitename", "siteUsername", "time", "lNumber", "nNumber", "pNumber"},
                                    new int[]{R.id.vlist_sitename, R.id.vlist_siteUsername, R.id.vlist_time, R.id.vlist_lNumber, R.id.vlist_nNumber, R.id.vlist_pNumber});
                            listView.setAdapter(adapter);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


                return false;
            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(homeActivity.this, editActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDataManager.deleteAllUserDatas();
                User user = userDataManager.getUser();
                if (user.getPhone() == null) {
                    intent.setClass(homeActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                Bundle bundle = data.getExtras();

                sitename = bundle.getString("editActivity_editText1");
                siteUsername = bundle.getString("editActivity_editText2");
                l = bundle.getInt("editActivity_l", 5);
                n = bundle.getInt("editActivity_n", 5);
                p = bundle.getInt("editActivity_p", 5);

                Info info = new Info();
                info.setSitename(sitename);
                info.setSiteUsername(siteUsername);
                info.setLCount(l);
                info.setNCount(n);
                info.setPCount(p);


                userDataManager.insertInfo(info, userDataManager.getUser().getPhone());

                SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.vlist,
                        new String[]{"sitename", "siteUsername", "time", "lNumber", "nNumber", "pNumber"},
                        new int[]{R.id.vlist_sitename, R.id.vlist_siteUsername, R.id.vlist_time, R.id.vlist_lNumber, R.id.vlist_nNumber, R.id.vlist_pNumber});
//            listView = (ListView) findViewById(R.id.home_listView);
                listView.setAdapter(adapter);

                Toast.makeText(homeActivity.this, sitename + " " + siteUsername + " " + l + " " + n + " " + p, Toast.LENGTH_SHORT).show();
            }
            if (requestCode == 101){

                Bundle bundle = data.getExtras();
                String key = bundle.getString("randomCode");
                Toast.makeText(getApplicationContext(),plainText,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),new String(AES.encrypt(plainText,key)),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        String date = "";
        date = DateFormat.getDateInstance().format(new Date());

        List<Info> infoList = userDataManager.getInfoByPhone(userDataManager.getUser().getPhone());
        for (Info info : infoList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sitename", info.getSitename());
            map.put("siteUsername", info.getSiteUsername());
            map.put("time", date);
            map.put("lNumber", info.getLCount() + "");
            map.put("nNumber", info.getNCount() + "");
            map.put("pNumber", info.getPCount() + "");

            list.add(map);
        }
        return list;
    }
}