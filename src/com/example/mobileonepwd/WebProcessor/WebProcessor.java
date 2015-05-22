package com.example.mobileonepwd.WebProcessor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.example.mobileonepwd.util.UiUpdater;


/**
 * Created with IntelliJ IDEA.
 * User: leaf
 * Date: 13-11-26
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class WebProcessor<T> {
    protected UiUpdater uiUpdater;
    private Context context;
    private T obj;

    abstract protected void process();

    public void run() {
        new Thread() {
            @Override
            public void run() {
                process();
            }
        }.start();
    }

    protected void uiUpdate(T obj, int resultCode) {
        this.obj = obj;
        Looper.prepare();
        uiHandler.sendEmptyMessage(resultCode);
    }

    protected void uiUpdate(int resultCode) {
        uiUpdate(null, resultCode);
    }

    public UiUpdater getUiUpdater() {
        return uiUpdater;
    }

    public void setUiUpdater(UiUpdater uiUpdater) {
        this.uiUpdater = uiUpdater;
        context = uiUpdater.getContext();
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (uiUpdater != null)
                uiUpdater.updateUI(obj);
            if (uiUpdater == null || uiUpdater.getContext() == null) return;
            switch (msg.what) {
                case -1:
                    Toast.makeText(uiUpdater.getContext(), "服务器异常，请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 1001:
                    Toast.makeText(uiUpdater.getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1002:
                    Toast.makeText(uiUpdater.getContext(), "token无效", Toast.LENGTH_SHORT).show();
                    break;
                case 1003:
                    Toast.makeText(uiUpdater.getContext(), "socket连接无效", Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    Toast.makeText(uiUpdater.getContext(), "手机号已注册", Toast.LENGTH_SHORT).show();
                    break;
                case 1010:
                    Toast.makeText(uiUpdater.getContext(), "json格式错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1011:
                    Toast.makeText(uiUpdater.getContext(), "参数格式错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1012:
                    Toast.makeText(uiUpdater.getContext(), "参数为空", Toast.LENGTH_SHORT).show();
                    break;
                case 1013:
                    Toast.makeText(uiUpdater.getContext(), "验证码过期或无效", Toast.LENGTH_SHORT).show();
                    break;
                case 1014:
                    Toast.makeText(uiUpdater.getContext(), "当日短信请求次数过多", Toast.LENGTH_SHORT).show();
                    break;
                case 1015:
                    Toast.makeText(uiUpdater.getContext(), "旧密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1016:
                    Toast.makeText(uiUpdater.getContext(), "查询信息不存在", Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    Toast.makeText(uiUpdater.getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                default:

                    break;
            }
        }
    };

}
