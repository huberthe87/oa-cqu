package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.smlightwai.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import jiekou.Utils;
import jiekou.appapi;

public class xunzhi_page extends Activity implements View.OnClickListener {

  private ViewPager viewPager;
  private ArrayList<View> pageview;
  private TextView videoLayout;
  private TextView musicLayout;
  private String shijianstr;


  private int flag = -1;//0����ά����1�������

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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.xunzhiguanli_both);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    viewPager = (ViewPager) findViewById(R.id.viewPager);
    //���Ҳ����ļ���LayoutInflater.inflate
    LayoutInflater inflater = getLayoutInflater();
    View xunzhixinxitianxie = inflater.inflate(R.layout.xunzhixinxitianxie, null);
    View bancichaxun = inflater.inflate(R.layout.bancichaxun_both, null);
    videoLayout = (TextView) findViewById(R.id.weixiusqhecx_sq);
    musicLayout = (TextView) findViewById(R.id.weixiusqhecx_cx);
    scrollbar = (ImageView) findViewById(R.id.scrollbar);


//		        ImageButton btn_backsq= (ImageButton) xunzhixinxitianxie.findViewById(R.id.fanhuinfc);
//		        ImageButton btn_backcx= (ImageButton) bancichaxun.findViewById(R.id.fanhuibancichaxun);
    Button btn_shishibanci = (Button) bancichaxun.findViewById(R.id.shishibancichaxun);
    Button btn_lishibanci = (Button) bancichaxun.findViewById(R.id.lishibancichaxun);
    Button btn_xunzhixiaoxi = (Button) xunzhixinxitianxie.findViewById(R.id.xunzhixiaoxitianxie);
    Button btn_nfcdakaxiaoxi = (Button) xunzhixinxitianxie.findViewById(R.id.nfcdakaxinxitianxie);
    Button chaxundakajilu = (Button) xunzhixinxitianxie.findViewById(R.id.chaxundakajilu);


    btn_shishibanci.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new dangtianzhibanThread()).start();
        }
      }
    });

    btn_lishibanci.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(xunzhi_page.this, lishizhibanriqixuanze.class);
          startActivity(intent);
        }


      }
    });

    btn_xunzhixiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(xunzhi_page.this, xunzhineirngtianxie.class);
          startActivity(intent);

        }

      }
    });

    btn_nfcdakaxiaoxi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(xunzhi_page.this, nfcdaka.class);
          startActivity(intent);

        }


      }
    });


//		        btn_backsq.setOnClickListener(new View.OnClickListener(){  
//					@Override  
//					public void onClick(View v){  
//					//Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������  
//					Intent intent = new Intent(xunzhi_page.this , Roleengineer.class);  
//					////����  
//					startActivity(intent);  
//					}  
//					}); 
//		        
//		        
//		        btn_backcx.setOnClickListener(new View.OnClickListener(){  
//					@Override  
//					public void onClick(View v){  
//						
//						Intent intent = new Intent(xunzhi_page.this , Roleengineer.class);  
//						startActivity(intent);  
//
//					}  
//					}); 


    videoLayout.setOnClickListener(this);
    musicLayout.setOnClickListener(this);
    pageview = new ArrayList<View>();
    //�����Ҫ�л��Ľ���
    pageview.add(xunzhixinxitianxie);
    pageview.add(bancichaxun);
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


  class dangtianzhibanThread extends Thread {
    public void run() {
      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/workManage/getWorkListCal");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("date", shijianstr);

        String resStr = appapi.getWebServiceRes(params, url);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(xunzhi_page.this, dangtianzhibanlist.class);
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
