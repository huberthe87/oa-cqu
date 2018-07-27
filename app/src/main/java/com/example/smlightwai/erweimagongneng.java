package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jiekou.Utils;
import jiekou.Utils5000;
import jiekou.appapi;
import jiekou.shebeitaizhang;

public class erweimagongneng extends Activity {

  private Context context;
  private TextView tv;
  private ImageView iv;
  private Button btn2;
  private Button btn_chaxun;
  private EditText et;
  private String str;
  String result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.erweima);
    btn_chaxun = (Button) findViewById(R.id.erweimachaxun);
    btn_chaxun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils5000.isFastClick()) {
          new Thread(new sbewmcxThread()).start();
        }
      }
    });


    context = this;

    scan();// ɨ���ά�벢��ʾ���

    generate();

  }

  public void generate() {
    iv = (ImageView) findViewById(R.id.iv);
    btn2 = (Button) findViewById(R.id.btn2);
    et = (EditText) findViewById(R.id.et);


    et.addTextChangedListener(new TextWatcher() {

      private String str2;

      @Override
      public void onTextChanged(CharSequence s, int start, int before,
                                int count) {
        // TODO Auto-generated method stub

      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
                                    int after) {
        // TODO Auto-generated method stub

      }

      @Override
      public void afterTextChanged(Editable s) {
        str2 = s.toString();
        btn2.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            if(TextUtils.isEmpty(str2)) {
              Toast.makeText(context, "����������", 0).show();
            } else {
              try {
                Bitmap bitmap = EncodingHandler.createQRCode(
                    str2, 300);
                iv.setImageBitmap(bitmap);
              } catch(WriterException e) {
                e.printStackTrace();
              }
            }
          }
        });
      }
    });
  }

  public void scan() {
    tv = (TextView) findViewById(R.id.tv);
    Button btn1 = (Button) findViewById(R.id.btn1);
    btn1.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, CaptureActivity.class);
        startActivityForResult(intent, 0);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK) {
      result = data.getExtras().getString("result");
      tv.setText(result);
    }
  }


  class sbewmcxThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/asset/getItemAssetListByCodeForApp");


        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("code", result);

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("shebei_page1", "wahaha��" + resStr);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(erweimagongneng.this, erweimataizhang.class);
        ////����
        Bundle bundle = new Bundle();

        bundle.putString("erweimataizhang", resStr);
        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }
}