package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

public class dangtianzhibanlist extends Activity {
  private ListView lv;
  private JSONArray ja;//�豸����list
  private String shijianstr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dangtianzhibanlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.dangtianzhibanfanghui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(dangtianzhibanlist.this, xunzhi_page.class);
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
          Intent intent = new Intent(dangtianzhibanlist.this, dangtianzhibanneirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("dangtianzhibanrenyuanneirong", ja.optJSONObject(position).toString());
          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    String shijianlist = "{\"dtzblist\":" + bundle.getString("dangtianzhibanlist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("dtzblist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = "   " + ob.getString("workBeginTime") + "����" + ob.getString("workEndTime") + "                           " + ob.getString("personChn");
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








