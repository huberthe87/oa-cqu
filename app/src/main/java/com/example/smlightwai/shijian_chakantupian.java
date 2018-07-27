package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;

import com.example.smlightwai.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import jiekou.Utils;
import jiekou.appapi;
import jiekou.jieshoutupian;

public class shijian_chakantupian extends Activity {

  private ImageView img_chakantupian;
  String imag1 = null;
  String imag2 = null;
  String imag3 = null;
  Bitmap bitmap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.chakantupian_shijian);

    img_chakantupian = (ImageView) this.findViewById(R.id.imageView1);
    Button tupian1 = (Button) this.findViewById(R.id.tupian1);
    Button tupian2 = (Button) this.findViewById(R.id.tupian2);
    Button tupian3 = (Button) this.findViewById(R.id.tupian3);

    tupian1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new chakantupianThread()).start();
          img_chakantupian.setImageBitmap(bitmap);
        }
      }
    });


    Bundle bundle = this.getIntent().getExtras();
    //����nameֵ
    imag1 = bundle.getString("tupiandizhi1");


  }


  class chakantupianThread extends Thread {
    public void run() {

      bitmap = new jieshoutupian().loadRmoteImage("http://222.178.109.129:9082/ibs" + imag1);


    }
  }


}
