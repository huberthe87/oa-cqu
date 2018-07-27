package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;
import jiekou.shebeitaizhang;

public class erweimataizhang extends Activity {
  TextView shebeileixing;
  TextView shebeilouceng;
  TextView gourushijian;
  TextView shenchanchangjia;
  TextView chengbaodanwei;
  TextView fuzerenyuan;
  TextView fuzerendianhua;
  private String itemid;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            itemid = new JSONObject(jsonObject.getString("ibsItemDTO")).getString("id");
            //���������UI�������������ʾ��������
            shebeileixing.setText(new JSONObject(jsonObject.getString("ibsResourceDTO")).getString("name"));
            shebeilouceng.setText(new JSONObject(jsonObject.getString("ibsItemDTO")).getString("name"));
            gourushijian.setText(jsonObject.getString("buy_date"));
            shenchanchangjia.setText(jsonObject.getString("factory"));

            chengbaodanwei.setText(jsonObject.getString("contractorUnit"));
            fuzerenyuan.setText(jsonObject.getString("director"));
            fuzerendianhua.setText(jsonObject.getString("phone"));
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
    setContentView(R.layout.erweimashebeitaizhang);


    shebeileixing = (TextView) this.findViewById(R.id.shebeileixingneirong);
    shebeilouceng = (TextView) this.findViewById(R.id.shebeiloucengneirong);
    gourushijian = (TextView) this.findViewById(R.id.gourushijianneirong);
    shenchanchangjia = (TextView) this.findViewById(R.id.shenchanchangjia);
    chengbaodanwei = (TextView) this.findViewById(R.id.chengbaodanwei);
    fuzerenyuan = (TextView) this.findViewById(R.id.fuzerenyuanneirong);
    fuzerendianhua = (TextView) this.findViewById(R.id.fuzerendianhua);
    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.fanhuishebeichaun);
    Button btn_weibao = (Button) this.findViewById(R.id.weibaoxinxichaxun);


    btn_fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(erweimataizhang.this, shebei_page1.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_weibao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          new Thread(new erweimaweibaoThread()).start();
        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String cxnr = bundle.getString("erweimataizhang");

    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);


  }

  class erweimaweibaoThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintainPlan/getMaintainPlanByItemId/" + itemid);

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        String resStr = appapi.getWebServiceRes(params, url);


        String shebeibaojinglist = "{\"sblist\":" + resStr + "}";

        Log.d("shebei_page1", "wahaha��" + shebeibaojinglist);

        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(erweimataizhang.this, erweimaweibao.class);
        ////����

        Bundle bundle = new Bundle();
        bundle.putString("erweimaweibao", shebeibaojinglist);

        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


}
