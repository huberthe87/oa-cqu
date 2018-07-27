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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import jiekou.Utils;
import jiekou.appapi;

public class shijianrenyuanlist extends Activity {


  private ListView lv;
  private JSONArray ja;
  private JSONObject jasonzhuti;//�豸����list
  public static String zhipairen;
  public static String zhipairenname;
  String renarenarenaxinxi;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.shijianlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhuishijianlist);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shijianrenyuanlist.this, shijian_page2.class);
          startActivity(intent);
        }

      }
    });


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        if(Utils.isFastClick()) {
          try {
            jasonzhuti = ja.optJSONObject(position);

            zhipairen = jasonzhuti.getString("id");
            zhipairenname = jasonzhuti.getString("name");
            Log.d("dsadadadasdasdasjdgashdggdashgdhgddgagda", zhipairen);

            Intent intent = new Intent(shijianrenyuanlist.this, shijianzhipai.class);
            Bundle bundle = new Bundle();
            //����name����Ϊtinyphp
            bundle.putString("weichulishijianchaxunneirong", renarenarenaxinxi);
            intent.putExtras(bundle);
            startActivity(intent);
          } catch(JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      }
    });

    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    renarenarenaxinxi = bundle.getString("renarenarenaxinxi");
    String shijianlist = "{\"sjlist\":" + bundle.getString("renarenarena") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("sjlist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = ob.getString("name");
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

	
	
	
	






