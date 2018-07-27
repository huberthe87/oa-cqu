package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.smlightwai.R;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;

public class lishizhibanriqixuanze extends Activity implements OnClickListener, OnDateSetListener {

  private TextView tv_date;
  private TextView end_date;
  private String startdata;
  private String enddata;
  private String flagtime;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lishizhibanxuanzegai);
    end_date = (TextView) findViewById(R.id.jieshuriqi);
    findViewById(R.id.jieshuriqi).setOnClickListener(this);

    Button btn_queren = (Button) findViewById(R.id.lishizhibanfasong);

    ImageButton xunzhineirongtianxiefanhui = (ImageButton) this.findViewById(R.id.xunzhineirongtianxiefanhui);

    xunzhineirongtianxiefanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent functionpage = new Intent(lishizhibanriqixuanze.this, xunzhi_page.class);
          ////����
          startActivity(functionpage);
        }

      }
    });


    btn_queren.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new lishizhibanThread()).start();
        }
      }
    });


  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.jieshuriqi) {
      flagtime = "start";
      Calendar calendar = Calendar.getInstance();
      DatePickerDialog dialog = new DatePickerDialog(this, this,
          calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH));
      dialog.show();
    } else {
      flagtime = "end";
      Calendar calendar = Calendar.getInstance();
      DatePickerDialog dialog = new DatePickerDialog(this, this,
          calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH));
      dialog.show();
    }
  }


  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    if(flagtime.equals("start")) {
      startdata = year + "-"
          + (monthOfYear < 9 ? "0" + (monthOfYear + 1) : monthOfYear + 1)
          + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
      end_date.setText(startdata);
    }


  }


  public static void startHome(Context mContext) {
    Intent intent = new Intent(mContext, lishizhibanriqixuanze.class);
    mContext.startActivity(intent);
  }


  class lishizhibanThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/workManage/getWorkListCal");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("date", startdata);
//							params.put("edDate", enddata);	

        String resStr = appapi.getWebServiceRes(params, url);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(lishizhibanriqixuanze.this, dangtianzhibanlist.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("dangtianzhibanlist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


}
	
	
	
	
	


