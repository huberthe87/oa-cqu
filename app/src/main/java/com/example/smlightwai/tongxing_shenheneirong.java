package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class tongxing_shenheneirong extends Activity {


  TextView texthuowuleixing;
  TextView texthuowumiaoshu;
  TextView textshijian;
  EditText texttuihuiyuanying;

  Button btn_tongxingtongguo;
  Button btn_tongxingdahui;
  JSONObject jsonObject;
  private String shijianstr;

  String tupianone;
  String tupiantwo;
  String tupianthree;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            texthuowuleixing.setText(jsonObject.getString("goodsType"));
            texthuowumiaoshu.setText(jsonObject.getString("goodsDescription"));
            textshijian.setText(jsonObject.getString("submissionDate"));

            tupianone = jsonObject.getString("imageUrl1");
            tupiantwo = jsonObject.getString("imageUrl2");
            tupianthree = jsonObject.getString("imageUrl3");

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
    setContentView(R.layout.tongxing_shenheneirong);

    texthuowuleixing = (TextView) this.findViewById(R.id.texthuowuleixing);
    texthuowumiaoshu = (TextView) this.findViewById(R.id.texthuowumiaoshu);
    textshijian = (TextView) this.findViewById(R.id.textshijian);
    texttuihuiyuanying = (EditText) this.findViewById(R.id.texttuihuiyuanying);
    btn_tongxingtongguo = (Button) this.findViewById(R.id.tongxingtongguo);
    btn_tongxingdahui = (Button) this.findViewById(R.id.tongxingdahui);
    Button tupianchakan = (Button) this.findViewById(R.id.tupianchakan);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    tupianchakan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_shenheneirong.this, tongxing_tupianchakan.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          Log.d("tupiandizhi1", tupianone);
          bundle.putString("tupiandizhi2", tupiantwo);
          bundle.putString("tupiandizhi3", tupianthree);
          intent.putExtras(bundle);
          ////����
          startActivity(intent);
        }
      }
    });

    btn_tongxingtongguo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new tongxingtongguoThread()).start();
        }


      }
    });

    btn_tongxingdahui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new tongxingtuihiThread()).start();
        }

      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String clxx = bundle.getString("tongxingshenhe_neirong");

    Message message = new Message();
    message.what = 0;
    message.obj = clxx.toString();
    handler.sendMessage(message);
  }


  class tongxingtongguoThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/updateIbsPassRecordStatusForAPP");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("passRecordId", jsonObject.getString("id"));
        params.put("checkStatus", "1");
        params.put("noticePersonCheckIds", "1");
        params.put("checkDate", shijianstr);
        params.put("adminUserId", Loginpage.yhjo.getString("id"));

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("dasdashdahdhasdasdgjkgjdgjsgjk", resStr);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(tongxing_shenheneirong.this, "ͨ�����ɹ���������ͨ��", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tongxing_shenheneirong.this, "ͨ���ɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        }

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class tongxingtuihiThread extends Thread {
    public void run() {

      //post����

      try {

        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/updateIbsPassRecordStatusForAPP");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("returnReason", texttuihuiyuanying.getText().toString());
        params.put("passRecordId", jsonObject.getString("id"));
        params.put("checkStatus", "2");
        params.put("checkDate", shijianstr);
        params.put("adminUserId", Loginpage.yhjo.getString("id"));


        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("aaaaaasdssaaaajsgjk", resStr);
        if(resStr.equals("false")) {
          Looper.prepare();
          Toast.makeText(tongxing_shenheneirong.this, "�˻ز��ɹ����������˻�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(tongxing_shenheneirong.this, "�˻سɹ�", Toast.LENGTH_LONG).show();
          Looper.loop();
          return;
        }

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }

}