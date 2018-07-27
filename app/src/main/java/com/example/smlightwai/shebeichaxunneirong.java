package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;
import com.example.smlightwai.shebei_page1.sblxThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.appapi;

public class shebeichaxunneirong extends Activity {


  TextView shebeileixing;
  TextView shebeilouceng;
  TextView gourushijian;
  TextView shenchanchangjia;
  TextView chengbaodanwei;
  TextView fuzerenyuan;
  TextView fuzerendianhua;
  TextView edingdianliu;
  TextView edingdianya;
  TextView edinggonglv;
  TextView zixitongmingcheng;
  TextView suozailouceng;
  TextView suozaiquyu;
  TextView gongchenbianma;
  String itemid;


  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;
            JSONObject jsonObject = new JSONObject(str);
            //���������UI�������������ʾ��������
            shebeileixing.setText(new JSONObject(jsonObject.getString("deviceType")).getString("name"));
            shebeilouceng.setText(new JSONObject(jsonObject.getString("ibsItemDTO")).getString("name"));
            gourushijian.setText(jsonObject.getString("buy_date"));
            shenchanchangjia.setText(jsonObject.getString("factory"));

            chengbaodanwei.setText(jsonObject.getString("contractorUnit"));
            fuzerenyuan.setText(jsonObject.getString("director"));
            fuzerendianhua.setText(jsonObject.getString("phone"));
            edingdianliu.setText(jsonObject.getString("rated_current"));
            edingdianya.setText(jsonObject.getString("rated_voltage"));
            edinggonglv.setText(jsonObject.getString("rated_power"));

            zixitongmingcheng.setText(new JSONObject(jsonObject.getString("ibsResourceDTO")).getString("name"));
            suozailouceng.setText(new JSONObject(jsonObject.getString("floorInfo")).getString("name"));
            suozaiquyu.setText(jsonObject.getString("area"));
            gongchenbianma.setText(jsonObject.getString("project_code"));

            itemid = jsonObject.getString("id");


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
    setContentView(R.layout.shebeichaxunneirong);


    shebeileixing = (TextView) this.findViewById(R.id.shebeileixingneirong);
    shebeilouceng = (TextView) this.findViewById(R.id.shebeiloucengneirong);
    gourushijian = (TextView) this.findViewById(R.id.gourushijianneirong);
    shenchanchangjia = (TextView) this.findViewById(R.id.shenchanchangjia);
    chengbaodanwei = (TextView) this.findViewById(R.id.chengbaodanwei);
    fuzerenyuan = (TextView) this.findViewById(R.id.fuzerenyuanneirong);
    fuzerendianhua = (TextView) this.findViewById(R.id.fuzerendianhua);
    edinggonglv = (TextView) this.findViewById(R.id.edinggonglv);
    edingdianliu = (TextView) this.findViewById(R.id.edingdianliu);
    edingdianya = (TextView) this.findViewById(R.id.edingdianya);
    zixitongmingcheng = (TextView) this.findViewById(R.id.zixitongmingcheng);
    suozailouceng = (TextView) this.findViewById(R.id.suozailouceng);
    suozaiquyu = (TextView) this.findViewById(R.id.suozaiquyu);
    gongchenbianma = (TextView) this.findViewById(R.id.gongchenbianma);


    Button weibojihua = (Button) this.findViewById(R.id.weibojihua);


    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.fanhuishebeichaun);


    btn_fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shebeichaxunneirong.this, shebeilist.class);
          ////����
          startActivity(intent);
        }
      }
    });

    weibojihua.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          new Thread(new shebeichaweibaolistThread()).start();
        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String cxnr = bundle.getString("chaxunxiangxixinxi");

    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);


  }

  class shebeichaweibaolistThread extends Thread {
    public void run() {

      try {
        //post����
        URL url = new URL("http://222.178.109.129:9082/ibs/api/maintainPlan/getMaintainPlanByItemId/" + "68792");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("MainActivity", ">>>>>>>>>>>>>��" + resStr);
        Log.d("itemid", itemid);

        Intent intent = new Intent(shebeichaxunneirong.this, shebeichaweibaolist.class);
        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shebeichaweibaolist", resStr);
        intent.putExtras(bundle);
        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }
  }


}
