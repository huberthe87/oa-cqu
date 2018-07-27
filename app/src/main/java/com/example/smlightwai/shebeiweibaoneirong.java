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
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shebeiweibaoneirong extends Activity {


  private String shijianstr;
  JSONObject cxwb1;
  JSONObject cxwb2;
  TextView shebeileixing;
  TextView jihuagongshi;
  TextView weibaoshijian;
  TextView zhidingren;
  TextView weibaozhuangtai;
  TextView fuzerenyuan;
  TextView weibaoeirong;
  private EditText edt_weibaoneirong;
  //	EditText edt_weibaoneirong = new EditText(this);
  private String weibaoneirong;
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {

            //���������UI�������������ʾ��������
            shebeileixing.setText(cxwb1.getString("itemNames"));
            jihuagongshi.setText(cxwb1.getString("planWorkHour"));
            weibaoshijian.setText(cxwb1.getString("startDate"));
            zhidingren.setText(cxwb1.getString("planMaker"));

            weibaozhuangtai.setText(cxwb2.getString("status"));
            fuzerenyuan.setText(cxwb1.getString("maintainPerson"));
            weibaoeirong.setText(cxwb2.getString("maintainContent"));
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
    setContentView(R.layout.shebeiweibaojihuaneirong);

    shebeileixing = (TextView) this.findViewById(R.id.shebeileixingneirong);
    jihuagongshi = (TextView) this.findViewById(R.id.jihuagongshi);
    weibaoshijian = (TextView) this.findViewById(R.id.jihuashijian);
    zhidingren = (TextView) this.findViewById(R.id.zhidingren);
    weibaozhuangtai = (TextView) this.findViewById(R.id.weibaozhuangtai);
    fuzerenyuan = (TextView) this.findViewById(R.id.fuzerenyuanneirong);
    weibaoeirong = (TextView) this.findViewById(R.id.weibaoneirong);
    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.fanhuishebeiweibaojihua);
    Button btn_weibaowanchen = (Button) this.findViewById(R.id.weibaowanchen);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    btn_fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shebeiweibaoneirong.this, shebeilist.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_weibaowanchen.setOnClickListener(chuliClick);


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    try {
      //����nameֵ
      Log.d("33333333333333", bundle.getString("yiduide"));
      Log.d("444444444444444", bundle.getString("xiangxide"));
      cxwb1 = new JSONObject(bundle.getString("yiduide"));
      cxwb2 = new JSONObject(bundle.getString("xiangxide"));

    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Message message = new Message();
    message.what = 0;
    message.obj = "";


    handler.sendMessage(message);
  }

  private View.OnClickListener chuliClick = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      // TODO Auto-generated method stub
      new AlertDialog.Builder(shebeiweibaoneirong.this)
          .setTitle("����ά������")
          .setIcon(android.R.drawable.ic_dialog_info)
          .setView(new EditText(shebeiweibaoneirong.this))
          .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

              new Thread(new querenweibaoThread()).start();

            }
          })
          .setNegativeButton("ȡ��", null)
          .show();

    }
  };


  class querenweibaoThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/saveOrUpdate ");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("maintainPerson", Loginpage.yhjo.getString("name") + "(�ֳ�ά����Ա)");
        params.put("id", cxwb2.getString("id"));
        params.put("maintainDate", shijianstr + "(�ֳ�ά��ʱ��)");
        params.put("maintainContent", cxwb2.getString("id"));
        params.put("planId", cxwb1.getString("id"));
        params.put("maintainStatus", "1");

        String resStr = appapi.getWebServiceRes(params, url);

        if(resStr.equals("ture")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ�ϳɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "ȷ��ʧ�ܣ�������ȷ��",
              Toast.LENGTH_SHORT).show();
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
