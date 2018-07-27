package com.example.smlightwai;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class weibaoxiangqingguanli extends Activity {

  private String shijianstr;
  JSONObject cxwb1;

  TextView caozuoneirong;
  TextView caozuoleixing;
  TextView caozuoshijian;
  TextView caozuoren;
  TextView weibaozhuangtai;


  String tupianone;
  String tupiantwo;
  String tupianthree;


  EditText edt_weibaoneirong;
  //	EditText edt_weibaoneirong = new EditText(this);
  private String weibaoneirong;
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {

            //���������UI�������������ʾ��������

            tupianone = cxwb1.getString("imageUrl1");
            tupiantwo = cxwb1.getString("imageUrl2");
            tupianthree = cxwb1.getString("imageUrl3");


            caozuoneirong.setText(cxwb1.getString("maintainContent"));

            if(cxwb1.getString("maintainStatus").equals("0")) {
              caozuoleixing.setText("�ƻ��ƶ�");
            } else if(cxwb1.getString("maintainStatus").equals("1")) {
              caozuoleixing.setText("�ֳ�ά��");
            } else if(cxwb1.getString("maintainStatus").equals("2")) {
              caozuoleixing.setText("ά��ȷ��");
            }
            caozuoshijian.setText(cxwb1.getString("maintainDate"));
            caozuoren.setText(cxwb1.getString("maintainPerson"));

            weibaozhuangtai.setText(cxwb1.getString("status"));
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
    setContentView(R.layout.weibaoxiangqingguanli);

    edt_weibaoneirong = new EditText(this);

    caozuoneirong = (TextView) this.findViewById(R.id.caozuoneirong);
    caozuoleixing = (TextView) this.findViewById(R.id.caozuoleixing);
    caozuoshijian = (TextView) this.findViewById(R.id.caozuoshijian);
    caozuoren = (TextView) this.findViewById(R.id.caozuoren);
    weibaozhuangtai = (TextView) this.findViewById(R.id.weibaozhuangtai);

    Button btn_weibaowanchen = (Button) this.findViewById(R.id.weibaowanchen);

    Button btn_chakantupian = (Button) this.findViewById(R.id.chakantupian);


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    btn_weibaowanchen.setOnClickListener(chuliClick);


    btn_chakantupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(weibaoxiangqingguanli.this, shijian_chakantupian.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          bundle.putString("tupiandizhi2", tupiantwo);
          bundle.putString("tupiandizhi3", tupianthree);
          intent.putExtras(bundle);
          ////����
          startActivity(intent);
        }

      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    try {

      cxwb1 = new JSONObject(bundle.getString("weibaoxiangqing"));

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
      new AlertDialog.Builder(weibaoxiangqingguanli.this)
          .setTitle("ά��ȷ����ע")
          .setIcon(android.R.drawable.ic_dialog_info)
          .setView(edt_weibaoneirong)
          .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(getApplicationContext(), "ddddkaishi�ɹ�",
                  Toast.LENGTH_SHORT).show();
              new Thread(new querenweibaoThread()).start();

            }
          })
          .setNegativeButton("ȡ��", null)
          .show();

    }
  };


  class querenweibaoThread extends Thread {
    public void run() {


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/saveOrUpdate");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", cxwb1.getString("id"));
        params.put("maintainContent", edt_weibaoneirong.getText().toString() + "(ά��ȷ����ע)");
        params.put("maintainPerson", Loginpage.yhjo.getString("name"));

        params.put("maintainDate", shijianstr);

        params.put("planId", cxwb1.getString("planId"));
        params.put("maintainStatus", "2");


        String resStr = appapi.getWebServiceRes(params, url);

        Log.d("resStrresStr", resStr);


        if(resStr.equals("true")) {
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
      } catch(Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }

  }
}
