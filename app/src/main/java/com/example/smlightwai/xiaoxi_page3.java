package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import jiekou.Utils;
import jiekou.WeiboDialogUtils;
import jiekou.appapi;

public class xiaoxi_page3 extends Activity implements View.OnClickListener {

  private ViewPager viewPager;
  private ArrayList<View> pageview;
  private TextView videoLayout;
  private TextView musicLayout;
  // ������ͼƬ
  private ImageView scrollbar;
  // ��������ʼƫ����
  private int offset = 0;
  // ��ǰҳ���
  private int currIndex = 0;
  // ���������
  private int bmpW;
  //һ��������
  private int one;
  private EditText edit_gonggaoneirong;
  private EditText edit_gonggaozhuti;
  private String btnflag;
  private TextView fasongduixiang;

  private Dialog mWeiboDialog;
  private String fasongbumen;
  private long orginazitionID;
  private Spinner spinner_sqbumen = null;


  public static String duixiang = "��ѡ������Ա";
  public static String contentword = "����д����";
  public static String titleword = "����д����";

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch(msg.what) {
        case 0:
          Log.d("dsdada", duixiang);
          fasongduixiang.setText(duixiang);
          edit_gonggaoneirong.setText(contentword);
          edit_gonggaozhuti.setText(titleword);
          break;
      }
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.xiaoxigonggaohexiaoxi);

    viewPager = (ViewPager) findViewById(R.id.viewPager);
    //���Ҳ����ļ���LayoutInflater.inflate
    LayoutInflater inflater = getLayoutInflater();
    View xiaoxitianxie = inflater.inflate(R.layout.xiaoxi_zongjingli, null);
    View xiaoxiguanli = inflater.inflate(R.layout.xiaoxiguanlifenleizongjingli, null);

    videoLayout = (TextView) findViewById(R.id.weixiusqhecx_sq);
    musicLayout = (TextView) findViewById(R.id.weixiusqhecx_cx);
    scrollbar = (ImageView) findViewById(R.id.scrollbar);


    ImageButton btn_backsq = (ImageButton) xiaoxitianxie.findViewById(R.id.fanhuixiaoxitianxie);
    ImageButton btn_backcx = (ImageButton) xiaoxiguanli.findViewById(R.id.xiaoxiguanlifenleifanhui);
    ImageButton btn_fasongxiaoxi = (ImageButton) xiaoxitianxie.findViewById(R.id.queding);
    Button btn_weichuli = (Button) xiaoxiguanli.findViewById(R.id.gerenfasongxiaoxin);
    Button btn_yitongguo = (Button) xiaoxiguanli.findViewById(R.id.lishixiaoxi);
    Button btn_yituihui = (Button) xiaoxiguanli.findViewById(R.id.xiaoxishenghe);


    spinner_sqbumen = (Spinner) xiaoxitianxie.findViewById(R.id.xuanbumen);

    fasongduixiang = (TextView) xiaoxitianxie.findViewById(R.id.fasongduixiang);
    edit_gonggaoneirong = (EditText) xiaoxitianxie.findViewById(R.id.gonggaoneirong);
    edit_gonggaozhuti = (EditText) xiaoxitianxie.findViewById(R.id.gonggaozhuti);
    spinner_sqbumen.setOnItemSelectedListener(new ProvOnItemSelectedListener_bumen());

    Message message = new Message();
    message.what = 0;

    handler.sendMessage(message);


    btn_backsq.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(xiaoxi_page3.this, Rolezongjingli.class);
        ////����
        startActivity(intent);
      }
    });


    btn_backcx.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(xiaoxi_page3.this, Rolezongjingli.class);
        ////����
        startActivity(intent);
      }
    });


    fasongduixiang.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new renyuanThread()).start();
        }
      }
    });

    btn_fasongxiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {

          mWeiboDialog = WeiboDialogUtils.createLoadingDialog(xiaoxi_page3.this, "������...");
          new Thread(new xiaoxisqThread()).start();
        }
      }
    });


    btn_weichuli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          btnflag = "0";
          new Thread(new xiaoxiliebiaoThread()).start();
        }
      }
    });


    btn_yitongguo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          btnflag = "1";
          new Thread(new xiaoxiliebiaoThread()).start();
        }
      }
    });


    btn_yituihui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          btnflag = "2";
          new Thread(new xiaoxiliebiaoThread()).start();
        }
      }
    });


    videoLayout.setOnClickListener(this);
    musicLayout.setOnClickListener(this);
    pageview = new ArrayList<View>();
    //�����Ҫ�л��Ľ���
    pageview.add(xiaoxitianxie);
    pageview.add(xiaoxiguanli);
    //����������
    PagerAdapter mPagerAdapter = new PagerAdapter() {

      @Override
      //��ȡ��ǰ���������
      public int getCount() {
        // TODO Auto-generated method stub
        return pageview.size();
      }

      @Override
      //�ж��Ƿ��ɶ������ɽ���
      public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
      }

      //ʹ��ViewGroup���Ƴ���ǰView
      public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(pageview.get(arg1));
      }

      //����һ������������������PagerAdapter������ѡ���ĸ�������ڵ�ǰ��ViewPager��
      public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(pageview.get(arg1));
        return pageview.get(arg1);
      }
    };
    //��������
    viewPager.setAdapter(mPagerAdapter);
    //����viewPager�ĳ�ʼ����Ϊ��һ������
    viewPager.setCurrentItem(0);
    //����л�����ļ�����
    viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    // ��ȡ�������Ŀ��
    bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.background2).getWidth();
    //Ϊ�˻�ȡ��Ļ��ȣ��½�һ��DisplayMetrics����
    DisplayMetrics displayMetrics = new DisplayMetrics();
    //����ǰ���ڵ�һЩ��Ϣ����DisplayMetrics����
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //�õ���Ļ�Ŀ��
    int screenW = displayMetrics.widthPixels;
    //�������������ʼ��ƫ����
    offset = (screenW / 2 - bmpW) / 2;
    //������л�һ������ʱ����������λ����
    one = offset * 2 + bmpW;
    Matrix matrix = new Matrix();
    matrix.postTranslate(offset, 0);
    //���������ĳ�ʼλ�����ó�����߽���һ��offset
    scrollbar.setImageMatrix(matrix);
  }


  public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageSelected(int arg0) {
      Animation animation = null;
      switch(arg0) {
        case 0:
          /**
           * TranslateAnimation���ĸ����Էֱ�Ϊ
           * float fromXDelta ������ʼ�ĵ��뵱ǰView X�����ϵĲ�ֵ
           * float toXDelta ���������ĵ��뵱ǰView X�����ϵĲ�ֵ
           * float fromYDelta ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ
           * float toYDelta ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ
           **/
          animation = new TranslateAnimation(one, 0, 0, 0);
          break;
        case 1:
          animation = new TranslateAnimation(offset, one, 0, 0);
          break;
      }
      //arg0Ϊ�л�����ҳ�ı���
      currIndex = arg0;
      // ������������Ϊtrue����ʹ��ͼƬͣ�ڶ�������ʱ��λ��
      animation.setFillAfter(true);
      //��������ʱ�䣬��λΪ����
      animation.setDuration(200);
      //��������ʼ����
      scrollbar.startAnimation(animation);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
  }


  @Override
  public void onClick(View view) {
    switch(view.getId()) {
      case R.id.weixiusqhecx_sq:
        //���"��Ƶ��ʱ�л�����һҳ
        viewPager.setCurrentItem(0);
        break;
      case R.id.weixiusqhecx_cx:
        //��������֡�ʱ�л��ĵڶ�ҳ
        viewPager.setCurrentItem(1);
        break;
    }
  }


  class xiaoxisqThread extends Thread {
    public void run() {


      //post����

      try {


        StringBuffer renyuanid = new StringBuffer();
        Set<String> set = bumenrenyuanlist.map.keySet();
        for(String s : set) {
          renyuanid.append(bumenrenyuanlist.map.get(s) + ",");
          System.out.println(s + "," + bumenrenyuanlist.map.get(s));
        }

        renyuanid.deleteCharAt(renyuanid.length() - 1);
        Log.d("zuihoude", renyuanid.toString());

        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/add");


        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("senderId", Loginpage.yhjo.getString("id"));
        params.put("content", edit_gonggaoneirong.getText().toString());
        params.put("userTree", renyuanid.toString());
        params.put("title", edit_gonggaozhuti.getText().toString());

        String resStr = appapi.getWebServiceRes(params, url);

        mWeiboDialog.dismiss();
        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "����ɹ�",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        } else {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "����ʧ�ܣ�����������",
              Toast.LENGTH_SHORT).show();
          Looper.loop();
          return;
        }
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class xiaoxiliebiaoThread extends Thread {
    public void run() {

      //post����


      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/messageAll");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("organizationId", orginazitionID);
        params.put("checkStatus", btnflag);
        String resStr = appapi.getWebServiceRes(params, url);

        Intent intent = new Intent(xiaoxi_page3.this, jieshouxiaoxilist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("jieshouxiaoxichakanlist", resStr);

        intent.putExtras(bundle);

        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }


  class renyuanThread extends Thread {
    public void run() {

      //post����

      try {


        URL url = new URL("http://222.178.109.129:9082/ibs/api/message/userTree/users");

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("organizationId", orginazitionID);
        params.put("userId", Loginpage.yhjo.getString("id"));
        String resStr = appapi.getWebServiceRes(params, url);


        Log.d("resStrresStr", resStr);
        contentword = edit_gonggaoneirong.getText().toString();
        titleword = edit_gonggaozhuti.getText().toString();


        Intent intent = new Intent(xiaoxi_page3.this, bumenrenyuanlist.class);

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("bumenrenyuan", resStr);
        bundle.putString("jiemiana", "3");
        intent.putExtras(bundle);
        ////����
        startActivity(intent);
      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  private class ProvOnItemSelectedListener_bumen implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      fasongbumen = adapter.getItemAtPosition(position).toString();
//		            Toast.makeText(getApplicationContext(), fasongbumen, Toast.LENGTH_LONG).show();  
      if(fasongbumen.equals("�г���")) {
        orginazitionID = 2;
      } else if(fasongbumen.equals("������")) {
        orginazitionID = 3;
      } else if(fasongbumen.equals("��ҵ���̲�")) {
        orginazitionID = 4;
      } else if(fasongbumen.equals("�߻���")) {
        orginazitionID = 5;
      } else if(fasongbumen.equals("������")) {
        orginazitionID = 6;
      } else if(fasongbumen.equals("�ͷ���")) {
        orginazitionID = 7;
      }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
      fasongbumen = "ʲôҲûѡ��";
      Toast.makeText(getApplicationContext(), fasongbumen, Toast.LENGTH_LONG).show();
    }
  }

}
