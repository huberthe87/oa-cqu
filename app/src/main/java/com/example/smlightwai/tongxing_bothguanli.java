package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;

import com.example.smlightwai.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import jiekou.Utils;
import jiekou.appapi;

public class tongxing_bothguanli extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.tongxing_fenleiguanli);

    ImageButton btn_back = (ImageButton) findViewById(R.id.tongxinguanlifenleifanhui);
    Button btn_tongxingchaxun = (Button) findViewById(R.id.tongxingguanlixiaoxichaxun);
    Button btn_tongxingshenhe = (Button) findViewById(R.id.tongxingguanlixiaoxishenheanniu);


    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(tongxing_bothguanli.this, Roleshichang.class);
          startActivity(intent);
        }

      }
    });


    btn_tongxingchaxun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new tongxinggunlichaxunlistThread()).start();
        }

      }
    });


    btn_tongxingshenhe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new tongxingshenhelistThread()).start();
        }
      }
    });

  }


  class tongxinggunlichaxunlistThread extends Thread {
    public void run() {

      //post����


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/getAllIbsPassRecord");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(tongxing_bothguanli.this, tongxing_gerenchaxunlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("tongxingyuangonglist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }

  }


  class tongxingshenhelistThread extends Thread {
    public void run() {

      //post����


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/passRecord/getAllPassRecordByStatus");

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("status", 0);
        String resStr = appapi.getWebServiceRes(params, url);


        Intent intent = new Intent(tongxing_bothguanli.this, tongxing_shenhelist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("tongxingshenhelist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }


  }


}
	
	
	
	
	
	
	



	
	
