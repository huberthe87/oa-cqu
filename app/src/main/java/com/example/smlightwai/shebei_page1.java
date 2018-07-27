package com.example.smlightwai;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.smlightwai.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import jiekou.Utils;
import jiekou.appapi;
import jiekou.shebeitaizhang;

public class shebei_page1 extends Activity implements View.OnClickListener {

  private ViewPager viewPager;
  private ArrayList<View> pageview;
  private TextView videoLayout;
  private TextView musicLayout;

  public static String shebeixitong = "���ֱ���ϵͳ";
  public static String shebeilouceng = "LG";
  public static String shebeileixing = "��������";
  public static String shebeixitongid = "21";
  public static String shebeiloucengid = "10080";
  public static String shebeileixingid = "101";


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
    setContentView(R.layout.shebeizonghe);

    viewPager = (ViewPager) findViewById(R.id.viewPager);
    //���Ҳ����ļ���LayoutInflater.inflate
    LayoutInflater inflater = getLayoutInflater();
    View shebeichaxun = inflater.inflate(R.layout.shebeichaxun, null);
    View weibaoxinxi = inflater.inflate(R.layout.weibaoxinxi, null);
    videoLayout = (TextView) findViewById(R.id.weixiusqhecx_sq);
    musicLayout = (TextView) findViewById(R.id.weixiusqhecx_cx);
    scrollbar = (ImageView) findViewById(R.id.scrollbar);


    ImageButton btn_backsq = (ImageButton) shebeichaxun.findViewById(R.id.fanhui);
    ImageButton btn_backcx = (ImageButton) weibaoxinxi.findViewById(R.id.fanhui);
    Button btn_erewima = (Button) shebeichaxun.findViewById(R.id.erweima);
    Button btn_shebeichaxun = (Button) shebeichaxun.findViewById(R.id.shebeichaxun);
    Button btn_nianjianxinxiguanli = (Button) weibaoxinxi.findViewById(R.id.nianjianxinxiguanli);
    Button btn_weibaoxinxiguanli = (Button) weibaoxinxi.findViewById(R.id.weibaoxinxiguanli);
    TextView text_shebeixitong = (TextView) shebeichaxun.findViewById(R.id.shebeixitong);
    TextView text_shebeilouceng = (TextView) shebeichaxun.findViewById(R.id.shebeilouceng);
    TextView text_shebeileixing = (TextView) shebeichaxun.findViewById(R.id.shebeileixing);

    text_shebeixitong.setText(shebeixitong);
    text_shebeilouceng.setText(shebeilouceng);
    text_shebeileixing.setText(shebeileixing);


    btn_shebeichaxun.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new sbcxThread()).start();
        }

      }
    });


    btn_erewima.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shebei_page1.this, erweimagongneng.class);
          ////����
          startActivity(intent);
        }
      }
    });


    text_shebeixitong.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new sbxtThread()).start();
        }
      }
    });


    text_shebeilouceng.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new sblcThread()).start();
        }
      }
    });


    text_shebeileixing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          new Thread(new sblxThread()).start();
        }
      }
    });


    btn_backsq.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shebei_page1.this, Roleengineer.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_backcx.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          Intent intent = new Intent(shebei_page1.this, Roleengineer.class);
          startActivity(intent);
        }

      }
    });

    btn_nianjianxinxiguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flag = 1;
          new Thread(new nianjianjihuaThread()).start();
        }
      }
    });

    btn_weibaoxinxiguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          flag = 0;
          new Thread(new nianjianjihuaThread()).start();
        }

      }
    });


    videoLayout.setOnClickListener(this);
    musicLayout.setOnClickListener(this);
    pageview = new ArrayList<View>();
    //�����Ҫ�л��Ľ���
    pageview.add(shebeichaxun);
    pageview.add(weibaoxinxi);
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


  class sbcxThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/asset/getAllIbsItemAssets");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("systemId", shebeixitongid);
        params.put("typeId", shebeileixingid);
        params.put("floorId", shebeiloucengid);


        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("shebei_page1", "wahaha��" + resStr);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(shebei_page1.this, shebeilist.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        shebeitaizhang.getInstance().setData(resStr);
        ;
        bundle.putString("flag", "sbcx");
        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class nianjianjihuaThread extends Thread {
    public void run() {


      //post����
      try {
        String str = "http://222.178.109.129:9082/ibs/api/maintainPlan/" + flag;
        URL url = new URL(str);


        Map<String, Object> params = new LinkedHashMap<String, Object>();

        params.put("planType", flag + "");
        String resStr = appapi.getWebServiceRes(params, url);


        if(flag == 1) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shebei_page1.this, shebeijihualist.class);
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shebeinianjianlist", resStr);
          bundle.putString("flag", "sbnianjian");
          intent.putExtras(bundle);

          startActivity(intent);
        } else if(flag == 0) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shebei_page1.this, shebeijihualist.class);
          ////����

          Bundle bundle = new Bundle();
          //����name����Ϊtinyphp
          bundle.putString("shebeiweibaolist", resStr);
          bundle.putString("flag", "sbweibao");
          intent.putExtras(bundle);

          startActivity(intent);
        } else {
          ///?????????????????????
        }


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class sbxtThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/asset/getSystemId");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        String resStr = appapi.getWebServiceRes(params, url);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(shebei_page1.this, shebeixitong.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shebeixitonglist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);

      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class sblcThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/asset/getFloorId/" + shebeileixingid);

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("sblcThread", resStr);

        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(shebei_page1.this, shebeiquyu.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shebeiquyulist", resStr);

        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


  class sblxThread extends Thread {
    public void run() {


      //post����
      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/asset/getTypeId/" + shebeixitongid);

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        String resStr = appapi.getWebServiceRes(params, url);
        Log.d("sblxThread", resStr);


        //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
        Intent intent = new Intent(shebei_page1.this, shebeileibie.class);
        ////����

        Bundle bundle = new Bundle();
        //����name����Ϊtinyphp
        bundle.putString("shebeilleibieist", resStr);
        intent.putExtras(bundle);

        startActivity(intent);


      } catch(MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }


  }


}
	    
	    
	



