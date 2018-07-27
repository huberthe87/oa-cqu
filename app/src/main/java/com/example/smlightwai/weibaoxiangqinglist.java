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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import jiekou.Utils;
import jiekou.appapi;

public class weibaoxiangqinglist extends Activity {


  private ListView lv;
  private JSONArray ja;//�豸����list


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
              Intent intent = new Intent(weibaoxiangqinglist.this, weibaoxiangqingyiban.class);  //?????????
              ////����

              Bundle bundle = new Bundle();
              //����name����Ϊtinyphp
              bundle.putString("weibaoxiangqing", ja.optJSONObject(position).toString());

              intent.putExtras(bundle);

              startActivity(intent);

            } else {
              Log.d("3333333333", "33333333333333");
              Intent intent = new Intent(weibaoxiangqinglist.this, weibaoxiangqingguanli.class);  //?????????
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


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();

    String shijianlist = "{\"wbxqlist\":" + bundle.getString("weibaoxiangqinglist") + "}";

    try {
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
      lv.setAdapter(new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, strs));
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

}

	
	
	
	
	
	


