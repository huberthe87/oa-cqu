package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shebeijihuaneirong extends Activity {


  TextView jihuamingcheng;
  TextView jihuagongshi;
  TextView jihuashijian;
  TextView zhidingren;
  TextView chengbaodanwei;
  TextView weixiushebei;
  TextView jihuaneirong;
  public static String planid;
  String cxnr;
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            jihuamingcheng.setText(jsonObject.getString("planName"));
            jihuagongshi.setText(jsonObject.getString("planWorkHour"));
            jihuashijian.setText(jsonObject.getString("startDate"));
            zhidingren.setText(jsonObject.getString("planMaker"));
            planid = jsonObject.getString("id");
            chengbaodanwei.setText(jsonObject.getString("maintainCompany"));
            weixiushebei.setText(jsonObject.getString("itemNames"));
            jihuaneirong.setText(jsonObject.getString("content"));

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
    setContentView(R.layout.shebeijihuaneirong);

    jihuamingcheng = (TextView) this.findViewById(R.id.jihuamingcheng);
    jihuagongshi = (TextView) this.findViewById(R.id.jihuagongshi);
    jihuashijian = (TextView) this.findViewById(R.id.jihuashijian);
    zhidingren = (TextView) this.findViewById(R.id.zhidingren);
    chengbaodanwei = (TextView) this.findViewById(R.id.chengbaodanwei);
    weixiushebei = (TextView) this.findViewById(R.id.weixiushebei);
    jihuaneirong = (TextView) this.findViewById(R.id.jihuaneirong);
    weixiushebei.setMovementMethod(ScrollingMovementMethod.getInstance());
    jihuaneirong.setMovementMethod(ScrollingMovementMethod.getInstance());
//          ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.fanhuijihuaneirong);		
    Button btn_chakanxiangqing = (Button) this.findViewById(R.id.chakanxiangqing);
//		 
//         
//		 btn_fanhui.setOnClickListener(new View.OnClickListener(){  
//				@Override  
//				public void onClick(View v){  
//				//Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������  
//				Intent intent = new Intent(shebeijihuaneirong.this , shebeijihualist.class);  
//				////����  
//				startActivity(intent);  
//				}  
//				}); 


    btn_chakanxiangqing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          new Thread(new weibaoxiangqingThread()).start();
        }

      }
    });


    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    cxnr = bundle.getString("shebeijihuaneirong");

    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);


  }

  class weibaoxiangqingThread extends Thread {
    public void run() {


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/getMaintainContentListByPlanId/{planId}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("planId", planid);


        String resStr = appapi.getWebServiceRes(params, url);

        Intent intent = new Intent(shebeijihuaneirong.this, weibaoxiangqinglist.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("weibaoxiangqinglist", resStr);

        intent.putExtras(bundle);

        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }

}
	
	
	
	
	
	
	
	

