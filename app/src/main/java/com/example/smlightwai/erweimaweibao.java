package com.example.smlightwai;

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
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;

public class erweimaweibao extends Activity {


  TextView shebeileixing;
  TextView jihuagongshi;
  TextView weibaoshijian;
  TextView zhidingren;
  TextView weibaochangjia;
  TextView fuzerenyuan;
  TextView weibaoeirong;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {
            String str = (String) msg.obj;

            JSONObject cxwdddd = new JSONObject(str);
            JSONArray ob = cxwdddd.getJSONArray("sblist");

            JSONObject cxwb1 = ob.getJSONObject(0);


            //���������UI�������������ʾ��������
            shebeileixing.setText(cxwb1.getString("planName"));
            jihuagongshi.setText(cxwb1.getString("planWorkHour"));
            weibaoshijian.setText(cxwb1.getString("startDate"));
            zhidingren.setText(cxwb1.getString("planMaker"));

            weibaochangjia.setText(cxwb1.getString("maintainCompany"));
            fuzerenyuan.setText(cxwb1.getString("planUsers"));
            weibaoeirong.setText(cxwb1.getString("content"));
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
    setContentView(R.layout.erweimaweibao);

    shebeileixing = (TextView) this.findViewById(R.id.shebeileixingneirong);
    jihuagongshi = (TextView) this.findViewById(R.id.jihuagongshi);
    weibaoshijian = (TextView) this.findViewById(R.id.weibaoshijian);
    zhidingren = (TextView) this.findViewById(R.id.zhidingren);
    weibaochangjia = (TextView) this.findViewById(R.id.weibaochangjia);
    fuzerenyuan = (TextView) this.findViewById(R.id.fuzerenyuanneirong);
    weibaoeirong = (TextView) this.findViewById(R.id.weibaoneirong);
    ImageButton btn_fanhui = (ImageButton) this.findViewById(R.id.erweimaweibaojihua);


    btn_fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(erweimaweibao.this, shebeilist.class);
          ////����
          startActivity(intent);
        }
      }
    });


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    String cxnr = bundle.getString("erweimaweibao");


    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);


  }


}


