package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.tuisong_shijianyishen.shijianThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;

public class tuisong_yishentuihui extends Activity {

  TextView shenqingren;
  TextView shenqingshijian;
  TextView shenqingneirong;
  TextView shijianzhuangtai;
  TextView chuliyijian;
  String cxnr;
  String tupianone;
  String tupiantwo;
  String tupianthree;
  String cxnr2;
  String cxnr3;
  String shijianid;
  private JSONObject jo;
  private JSONObject jo2;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:
          try {

            //���������UI�������������ʾ��������
            tupianone = jo.getString("imageOne");
            tupiantwo = jo.getString("imageOne");
            tupianthree = jo.getString("imageOne");

            shenqingren.setText(jo.getString("senderName"));
            shenqingshijian.setText(jo.getString("submissionDate"));
            shenqingneirong.setText(jo.getString("content"));
            chuliyijian.setText(jo.getString("returnReason") + ("(����Ա�˻���ʾ)"));


            if(jo2.getString("dealStatus").equals("0")) {
              shijianzhuangtai.setText("��δ����");
            } else if(jo2.getString("dealStatus").equals("1")) {
              shijianzhuangtai.setText("���ڴ���");
            } else if(jo2.getString("dealStatus").equals("2")) {
              shijianzhuangtai.setText("�������");
            } else if(jo2.getString("dealStatus").equals("3")) {
              shijianzhuangtai.setText("���˻�");
            }


//					chuliyijian.setText(jsonObject.getString("returnReason")+("(����Ա�˻���ʾ)"));
            if(jo.getString("returnReason").equals("null") || jo.getString("returnReason").length() == 0) {

              chuliyijian.setText(jo2.getString("returnReason") + ("(�ֳ������¼)"));

            } else {

              chuliyijian.setText(jo.getString("returnReason") + ("(����Ա�˻���ʾ)"));

            }


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
    setContentView(R.layout.shijianchaxunneirong);


    shenqingren = (TextView) this.findViewById(R.id.shenqingneirong19);
    shenqingshijian = (TextView) this.findViewById(R.id.shenqingneirong20);
    shenqingneirong = (TextView) this.findViewById(R.id.shenqingshijian4333530);
    shijianzhuangtai = (TextView) this.findViewById(R.id.shijianzhuangtai);
    chuliyijian = (TextView) this.findViewById(R.id.chuliyijian);
    ImageButton fanghui = (ImageButton) this.findViewById(R.id.fanghui);
    Button chakantupian = (Button) this.findViewById(R.id.chakantupian);


    fanghui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          try {
            Intent intent;
            if(Loginpage.yhjo.getString("organizationId").equals("2")) {
              intent = new Intent(tuisong_yishentuihui.this, Roleshichang.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("3")) {
              intent = new Intent(tuisong_yishentuihui.this, Roleanbao.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("4")) {
              intent = new Intent(tuisong_yishentuihui.this, Roleengineer.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("5")) {
              intent = new Intent(tuisong_yishentuihui.this, Rolecehua.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("6")) {
              intent = new Intent(tuisong_yishentuihui.this, Rolexingzheng.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("7")) {
              intent = new Intent(tuisong_yishentuihui.this, Rolekefu.class);
              startActivity(intent);

            } else if(Loginpage.yhjo.getString("organizationId").equals("8")) {
              intent = new Intent(tuisong_yishentuihui.this, Rolezongjingli.class);
              startActivity(intent);
            }
          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }


        }
      }
    });


    chakantupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tuisong_yishentuihui.this, shijian_chakantupian.class);
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
    shijianid = bundle.getString("shijianid");
    new Thread(new shijianThread()).start();


  }

  class shijianThread extends Thread {
    public void run() {

      try {


        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/searchById");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("id", shijianid);

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("wahahahahaahhahahah", resStr);
        jo = new JSONObject(resStr);


        new Thread(new xiaoxi2Thread()).start();

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


  class xiaoxi2Thread extends Thread {
    public void run() {

      try {


        URL url2 = new URL("http://222.178.109.129:9082/ibs/api/eventDeal/searchOne");

        Map<String, Object> params2 = new LinkedHashMap<String, Object>();


        params2.put("id", shijianid);

        String resStr2 = appapi.getWebServiceRes(params2, url2);
        Log.d("wahahahahaahhahahah22222222", resStr2);
        jo2 = new JSONObject(resStr2);


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

	
	

	

