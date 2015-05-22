package com.example.mobileonepwd.WebProcessor;

import android.util.Log;
import com.example.mobileonepwd.util.HttpData;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created with IntelliJ IDEA.
 * User: leaf
 * Date: 14-4-14
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class LoginProcessor extends WebProcessor<String> {
    private String url;
    private String id;
    private String ifOnline;
    private String pwd;
//    private Location location;

    public LoginProcessor(String url, String id, String pwd, String ifOnline) {
        this.url = url;
        this.id = id;
        this.ifOnline = ifOnline;
        this.pwd = pwd;
//        this.location = location;
    }

    @Override
    protected void process() {
        HttpData httpData = new HttpData();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",id);
            jsonObject.put("passwd",pwd);
            jsonObject.put("ifonline",ifOnline);
//            jsonObject.put("location_x",location.getLatitude());
//            jsonObject.put("location_y",location.getLongitude());
        } catch (JSONException e) {
            uiUpdate(null, -1);
            e.printStackTrace();
        }

        String key[] = {"params"};
        String value[] = {jsonObject.toString()};
        String content = httpData.getContent(url, key, value);
        if (httpData.getCode() != 200) return;

//        JSONObject jsonObject;
        JSONTokener jsonTokener;

        try {
            jsonTokener = new JSONTokener(content);
            jsonObject = (JSONObject) jsonTokener.nextValue();
            int code = jsonObject.getInt("code");
            if (code == 200) {
                String token = jsonObject.getString("token");
                uiUpdate(token, 0);
            } else {
                uiUpdate(null, code);
            }

        } catch (JSONException e) {
            uiUpdate(null, -1);
            Log.e("WebProcessor", e.getMessage() + url);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
