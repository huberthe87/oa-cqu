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

public class tongxing_anbaolist extends Activity {
  private ListView lv;
  private JSONArray ja;//�豸����list
  String tongxinganbaolist;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.tongxingshichanglist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.tongxingshichanglistfanghui);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_anbaolist.this, Roleanbao.class);
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
          Intent intent = new Intent(tongxing_anbaolist.this, tongxing_tarenneirong.class);  //?????????
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tongxingshenhe_neirong", ja.optJSONObject(position).toString());
          bundle.putString("tongxinganbaolist", tongxinganbaolist);
          intent.putExtras(bundle);

          startActivity(intent);
        }
      }
    });


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    tongxinganbaolist = bundle.getString("tongxinganbaolist");

    String shijianlist = "{\"txablist\":" + bundle.getString("tongxinganbaolist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("txablist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = ob.getString("submissionDate") + "                       " + ob.getString("goodsType");
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
	


