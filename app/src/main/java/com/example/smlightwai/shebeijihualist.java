package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shebeijihualist extends Activity {


  private ListView lv;
  private JSONArray ja;
  private String flag;
  private int wbposition;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shebeijihualist);


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������

//		    		wbposition=position;	
//		    		new Thread(new ShebeiweibaojihuaThread()).start();
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shebeijihualist.this, shebeijihuaneirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shebeijihuaneirong", ja.optJSONObject(position).toString());
          intent.putExtras(bundle);

          startActivity(intent);

        }
      }
    });


    //��ҳ���������
    Bundle bundle = this.getIntent().getExtras();
    flag = bundle.getString("flag");

    if(flag.equals("sbweibao")) {
      try {
        String weibaolist = "{\"wblist\":" + bundle.getString("shebeiweibaolist") + "}";
        JSONObject jo = new JSONObject(weibaolist);
        ja = jo.getJSONArray("wblist");
        int n = ja.length();
        String strs[] = new String[n];
        for(int i = 0; i < n; i++) {
          JSONObject ob = ja.getJSONObject(i);
          strs[i] = ob.getString("startDate") + "            " + ob.getString("planName");
        }


        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, strs));
        Log.d("shebeilist", "########" + weibaolist);

      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else if(flag.equals("sbnianjian")) {
      try {
        String nianjianlist = "{\"njlist\":" + bundle.getString("shebeinianjianlist") + "}";
        JSONObject jo = new JSONObject(nianjianlist);
        ja = jo.getJSONArray("njlist");
        int n = ja.length();
        String strs[] = new String[n];
        for(int i = 0; i < n; i++) {
          JSONObject ob = ja.getJSONObject(i);
          strs[i] = ob.getString("startDate") + "            " + ob.getString("planName");
        }


        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, strs));
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }


  class ShebeiweibaojihuaThread extends Thread {
    public void run() {

      try {
        //post����
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintain/getMaintainContentListByPlanId/{planId}");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        JSONObject fjo = ja.optJSONObject(wbposition);

        params.put("planId", fjo.getString("id"));


        String resStr = appapi.getWebServiceRes(params, url);
        String houyigelist = "{\"houyigelist\":" + resStr + "}";


        Intent intent = new Intent(shebeijihualist.this, shebeiweibaolist.class);
        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("fatherjo", fjo.toString());
        bundle.putString("childjo", houyigelist);

        intent.putExtras(bundle);
        startActivity(intent);
        //Log.d("MainActivity",">>>>>>>>>>>>>��"+resStr);


      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


}
