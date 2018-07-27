package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jiekou.Utils;
import jiekou.appapi;

public class tuisong_weibaoqueren extends Activity {


  private ListView lv;
  private JSONArray ja;//�豸����list
  String weibaoid;
  String resStr;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String shijianlist = "{\"wbxqlist\":" + resStr + "}";
            Log.d("222222222222222ewqeqw", shijianlist);
            JSONObject jo = new JSONObject(shijianlist);
            //����豸����list
            ja = jo.getJSONArray("wbxqlist");
            int n = ja.length();
            String strs[] = new String[n];

            for(int i = 0; i < n; i++) {
              JSONObject ob = ja.getJSONObject(i);
              strs[i] = "     " + ob.getString("maintainDate") + "                  " + ob.getString("status");
            }


            /*ΪListView����Adapter��������*/
            lv.setAdapter(new ArrayAdapter<String>(tuisong_weibaoqueren.this, android.R.layout.simple_list_item_1, strs));
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
    setContentView(R.layout.shebeiweibaonianjianlist);


//        ImageButton btn_back = (ImageButton) findViewById(R.id.fanghui);
//		
//		
//		
//		btn_back.setOnClickListener(new View.OnClickListener(){  
//			@Override  
//			public void onClick(View v){  
//				
//				Intent intent = new Intent(weibaoxiangqinglist.this , Roleanbao.class);  
//				startActivity(intent);  
//
//			}  
//			}); 		


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        if(Utils.isFastClick()) {
          Log.d("11111", "1111111");
          try {
            if(Loginpage.yhjo.getString("roleId").equals("8")) {
              Log.d("222222", "2222222222");
              Intent intent = new Intent(tuisong_weibaoqueren.this, weibaoxiangqingyiban.class);  //?????????
              ////����

              Bundle bundle = new Bundle();
              //����name����Ϊtinyphp
              bundle.putString("weibaoxiangqing", ja.optJSONObject(position).toString());

              intent.putExtras(bundle);

              startActivity(intent);

            } else {
              Log.d("3333333333", "33333333333333");
              Intent intent = new Intent(tuisong_weibaoqueren.this, weibaoxiangqingguanli.class);  //?????????
              ////����

              Bundle bundle = new Bundle();
              //����name����Ϊtinyphp
              bundle.putString("weibaoxiangqing", ja.optJSONObject(position).toString());

              intent.putExtras(bundle);

              startActivity(intent);

            }

          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      }
    });


    Bundle bundle = this.getIntent().getExtras();
    weibaoid = bundle.getString("weibaoid");
    new Thread(new weibaoxiangqingThread()).start();


  }


  class weibaoxiangqingThread extends Thread {
    public void run() {


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/getMaintainContentListByPlanId/{planId}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("planId", weibaoid);


        resStr = appapi.getWebServiceRes(params, url);

        Log.d("111111111111111ewqeqw", resStr);


        Message message = new Message();
        message.what = 0;
        message.obj = "";
        handler.sendMessage(message);

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


}
	
	