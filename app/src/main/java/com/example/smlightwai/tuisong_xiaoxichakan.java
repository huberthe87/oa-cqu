package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.tuisong_huojing.baojingneirongThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.appapi;

public class tuisong_xiaoxichakan extends Activity {

  TextView jieshouxiaoxizhuti;
  TextView jieshouxiaoxifasongren;
  TextView jieshouxiaoxifasongshijian;
  TextView jieshouxiaoxineirong;
  String xiaoxiid;
  private JSONObject jo;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {

            //���������UI�������������ʾ��������
            jieshouxiaoxizhuti.setText(jo.getString("title"));
            jieshouxiaoxifasongren.setText(jo.getString("senderName"));
            jieshouxiaoxifasongshijian.setText(jo.getString("submissionDate"));
            jieshouxiaoxineirong.setText(jo.getString("content"));
            Log.d("wahahahahaahhahahah", "11111111111111111111");
          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          break;
      }
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.jieshouxiaoxineirongchakan);

    jieshouxiaoxizhuti = (TextView) this.findViewById(R.id.jieshouxiaoxizhuti);
    jieshouxiaoxifasongren = (TextView) this.findViewById(R.id.jieshouxiaoxifasongren);
    jieshouxiaoxifasongshijian = (TextView) this.findViewById(R.id.jieshouxiaoxifasongshijian);
    jieshouxiaoxineirong = (TextView) this.findViewById(R.id.jieshouxiaoxineirong);
    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.jieshouxiaoxifanhui);


    //�豸��ϸ��Ϣ


    Bundle bundle = this.getIntent().getExtras();
    xiaoxiid = bundle.getString("xiaoxiid");
    new Thread(new xiaoxiThread()).start();


  }


  class xiaoxiThread extends Thread {
    public void run() {

      try {


        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/searchById");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", xiaoxiid);

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("wahahahahaahhahahah", resStr);
        jo = new JSONObject(resStr);

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      Message message = new Message();
      message.what = 0;
      message.obj = "";
      handler.sendMessage(message);


    }
  }

}
