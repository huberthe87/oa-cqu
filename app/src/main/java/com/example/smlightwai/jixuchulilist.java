package com.example.smlightwai;

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

public class jixuchulilist extends Activity {
  private ListView lv;
  private JSONArray ja;//�豸����list


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.jixuchulishijianlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhuishijianlist);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(jixuchulilist.this, shijian_page1.class);
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
          Intent intent = new Intent(jixuchulilist.this, jixuchulineirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("jixuchulishijianneirong", ja.optJSONObject(position).toString());
          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    String shijianlist = "{\"jxcllist\":" + bundle.getString("jixuchulilist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("jxcllist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        if(ob.getString("date").equals("null") || ob.getString("date").length() == 0) {
          Log.d("" + i, "111111111111111111");
          strs[i] = "                " + "δȷ��" + "                  " + new JSONObject(ob.getString("eventDto")).getString("title");

        } else if(ob.getString("dealStatus").equals("3")) {
          Log.d("" + i, "111111111111111111");
          strs[i] = ob.getString("date") + "             " + new JSONObject(ob.getString("eventDto")).getString("title") + " (���˻�)";

        } else {
          strs[i] = ob.getString("date") + "             " + new JSONObject(ob.getString("eventDto")).getString("title");
        }
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




