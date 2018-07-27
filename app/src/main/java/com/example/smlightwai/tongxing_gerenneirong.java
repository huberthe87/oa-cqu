package com.example.smlightwai;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;

public class tongxing_gerenneirong extends Activity {


  TextView huowuleixing;
  TextView huowumiaoshu;
  TextView shijian;
  TextView tuihuiyuanying;
  TextView shenqingzhuangtai;
  Button huowutupian;
  JSONObject jsonObject1;
  ImageView image;
  Bitmap tupian;
  String cxnr;
  String zaichuanyibo;

  String tupianone;
  String tupiantwo;
  String tupianthree;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:

          try {


            String str = (String) msg.obj;
            Log.d("tongxing", str);
            jsonObject1 = new JSONObject(str);
            //���������UI�������������ʾ��������
            huowuleixing.setText(jsonObject1.getString("goodsType"));
            huowumiaoshu.setText(jsonObject1.getString("goodsDescription"));
            shijian.setText(jsonObject1.getString("submissionDate"));
            tuihuiyuanying.setText(jsonObject1.getString("returnReason"));
            shenqingzhuangtai.setText(jsonObject1.getString("checkStatusName"));

            tupianone = jsonObject1.getString("imageUrl1");
            tupiantwo = jsonObject1.getString("imageUrl2");
            tupianthree = jsonObject1.getString("imageUrl3");

//					image.setImageBitmap(tupian);
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
    setContentView(R.layout.tongxing_gerenneirong);


//		new Thread(new LoginThread()).start();
    huowuleixing = (TextView) this.findViewById(R.id.texthuowuleixing);
    huowumiaoshu = (TextView) this.findViewById(R.id.texthuowumiaoshu);
    image = (ImageView) this.findViewById(R.id.huowutupian);
    shijian = (TextView) this.findViewById(R.id.textshijian);
    huowutupian = (Button) this.findViewById(R.id.button1);
    ImageButton btn_back = (ImageButton) findViewById(R.id.fanhui);

    tuihuiyuanying = (TextView) this.findViewById(R.id.texttuihuiyuanying);
    shenqingzhuangtai = (TextView) this.findViewById(R.id.textshenqingzhuangtai);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_gerenneirong.this, tongxing_gerenchaxunlist.class);

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tongxingyuangonglist", zaichuanyibo);

          intent.putExtras(bundle);

          startActivity(intent);
        }

      }
    });


    huowutupian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_gerenneirong.this, tongxing_tupianchakan.class);
          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("tupiandizhi1", tupianone);
          Log.d("tupiandizhi1", tupianone);
          bundle.putString("tupiandizhi2", tupiantwo);
          bundle.putString("tupiandizhi3", tupianthree);
          intent.putExtras(bundle);
          ////����
          startActivity(intent);
        }

      }
    });


    StrictMode.setThreadPolicy(new
        StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
    StrictMode.setVmPolicy(
        new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


    //�豸��ϸ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    cxnr = bundle.getString("tongxing_neirong");
    zaichuanyibo = bundle.getString("chuanyibo");

    Message message = new Message();
    message.what = 0;
    message.obj = cxnr.toString();
    handler.sendMessage(message);
    Log.d("tupian", "dsadadasdadadas");

  }


}

