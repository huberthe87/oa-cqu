package com.example.smlightwai;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
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
import jiekou.Utils5000;
import jiekou.WeiboDialogUtils;
import jiekou.appapi;

public class shijian_page3 extends Activity implements View.OnClickListener {

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

  private Bundle bundle;
  private EditText edit_sqadtress;
  private EditText edit_sqbeizhue;
  private Spinner spinner_sqleibie = null;
  private Spinner spinner_sqbumen = null;
  private Spinner spinner_mubiaobumen = null;
  private String shijianleixing;
  private String fasongbumen;
  private String mubiaobumenname;
  public static int mubiaobumenid = 4;
  private String shijianstr;
  private long orginazitionID;
  private String shijiantype;


  private File fileName = null;

  private ImageView imageView;

  private Dialog mWeiboDialog;

  String BOUNDARY = UUID.randomUUID().toString(); //�߽��ʶ �������
  String PREFIX = "--", LINE_END = "\r\n";
  String CONTENT_TYPE = "multipart/form-data";
  private static final String TAG = "uploadFile";
  private static final int TIME_OUT = 10 * 10000000; //��ʱʱ��
  private static final String CHARSET = "utf-8"; //���ñ���
  private static final String SUCCESS = "1";
  private static final String FAILURE = "0";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.weixiusqandcx);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
    shijianstr = formatter.format(curDate);


    viewPager = (ViewPager) findViewById(R.id.viewPager);
    //���Ҳ����ļ���LayoutInflater.inflate
    LayoutInflater inflater = getLayoutInflater();
    View weixiusq = inflater.inflate(R.layout.weixiushenqing, null);
    View shijianguanlifenlei = inflater.inflate(R.layout.shijianguanlifenleizongjingli, null);
    videoLayout = (TextView) findViewById(R.id.weixiusqhecx_sq);
    musicLayout = (TextView) findViewById(R.id.weixiusqhecx_cx);
    scrollbar = (ImageView) findViewById(R.id.scrollbar);


    ImageButton btn_backsq = (ImageButton) weixiusq.findViewById(R.id.fanhui);
    ImageButton btn_backcx = (ImageButton) shijianguanlifenlei.findViewById(R.id.fanhui);
    Button btn_weixiusq = (Button) weixiusq.findViewById(R.id.shenqing);
    Button btn_gerenshijian = (Button) shijianguanlifenlei.findViewById(R.id.gerenshijianguanli);
    Button btn_benbumenzongheshijian = (Button) shijianguanlifenlei.findViewById(R.id.benbumenzongheshijianguanli);
    Button btn_tabumenzongheshijianguanli = (Button) shijianguanlifenlei.findViewById(R.id.tabumenzongheshijianguanli);
    edit_sqadtress = (EditText) weixiusq.findViewById(R.id.baoxiuren);
    Button btn_paizhao = (Button) weixiusq.findViewById(R.id.paizhao);

    edit_sqbeizhue = (EditText) weixiusq.findViewById(R.id.beizhu);
    spinner_sqleibie = (Spinner) weixiusq.findViewById(R.id.jingjichengdu);
    spinner_sqbumen = (Spinner) weixiusq.findViewById(R.id.baoxiushijian);
    spinner_mubiaobumen = (Spinner) shijianguanlifenlei.findViewById(R.id.mubiaobumen);


    spinner_sqleibie.setOnItemSelectedListener(new ProvOnItemSelectedListener());
    spinner_sqbumen.setOnItemSelectedListener(new ProvOnItemSelectedListener_bumen());
    spinner_mubiaobumen.setOnItemSelectedListener(new ProvOnItemSelectedListener_mubiaobumen());

    imageView = (ImageView) weixiusq.findViewById(R.id.imageView1);


    btn_paizhao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        File filePath = new File(Environment.getExternalStorageDirectory(), "myCamera");
        if(!filePath.exists()) {
          filePath.mkdirs();
        }
        fileName = new File(filePath, System.currentTimeMillis() + ".jpg");
        try {
          if(!fileName.exists()) {
            fileName.createNewFile();
          }
        } catch(Exception e) {
          e.printStackTrace();
        }
        // intent��������ϵͳ�Դ���Camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ��ϵͳCamera��������д�뵽�ļ�
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileName));
        // ����intent��Ӧ��Activity������Ĭ����Ϣ
        startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
      }
    });


    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//��������
        startActivityForResult(intent, 0x23);
      }
    });


    btn_weixiusq.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils5000.isFastClick()) {
          mWeiboDialog = WeiboDialogUtils.createLoadingDialog(shijian_page3.this, "������...");
          new Thread(new SjsqThread()).start();
        } else {
          Toast.makeText(getApplicationContext(), "�����ظ�����", Toast.LENGTH_SHORT).show();
        }

      }
    });


    btn_backsq.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shijian_page3.this, Rolezongjingli.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_backcx.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shijian_page3.this, Rolezongjingli.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_gerenshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Loginpage.gerenshijiaguanlifrom = "shijian_page3";
          Intent intent = new Intent(shijian_page3.this, gerenshijianguanli.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_benbumenzongheshijian.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shijian_page3.this, benbumenzongheshijianguanli.class);
          ////����
          startActivity(intent);
        }
      }
    });


    btn_tabumenzongheshijianguanli.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Utils.isFastClick()) {
          //Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
          Intent intent = new Intent(shijian_page3.this, tabumenzongheshijianguanli.class);
          ////����
          startActivity(intent);
        }
      }
    });


    videoLayout.setOnClickListener(this);
    musicLayout.setOnClickListener(this);
    pageview = new ArrayList<View>();
    //�����Ҫ�л��Ľ���
    pageview.add(weixiusq);
    pageview.add(shijianguanlifenlei);
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


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == Activity.DEFAULT_KEYS_DIALER) {
      // MainActivity����Camera���ص���Ϣ��Ȼ���Ѿ�д���ͼƬ��ʾ��ImageView��
      Bitmap cameraImageBitmap = getBitmapFromBigFile(fileName.getPath(), 480, 720);
      try {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
        cameraImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
      } catch(FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch(IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      imageView.setImageBitmap(cameraImageBitmap);
//    		imageView.setImageURI(Uri.fromFile(fileName));  
    } else if(requestCode == 0x23) {
      if(resultCode == RESULT_OK) {
        String selectedImagePath2;

        Uri uri2 = data.getData();
        selectedImagePath2 = getPath(uri2);
        fileName = new File(selectedImagePath2);
        Toast.makeText(getApplicationContext(), selectedImagePath2,
            Toast.LENGTH_LONG).show();
        int xiangcedegree = getBitmapDegree(selectedImagePath2);
        Bitmap weitu = lessenUriImage(selectedImagePath2);
        Bitmap tu = rotateBitmapByDegree(weitu, xiangcedegree);
        imageView.setImageBitmap(tu);//չʾѡ���ͼƬ
      }
    }
  }

  public final static Bitmap lessenUriImage(String path) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    Bitmap bitmap = BitmapFactory.decodeFile(path, options); //��ʱ���� bm Ϊ��
    options.inJustDecodeBounds = false; //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
    int be = (int) (options.outHeight / (float) 320);
    if(be <= 0)
      be = 1;
    options.inSampleSize = be; //���¶���ͼƬ��ע���ʱ�Ѿ��� options.inJustDecodeBounds ��� false ��
    bitmap = BitmapFactory.decodeFile(path, options);
    int w = bitmap.getWidth();
    int h = bitmap.getHeight();
    System.out.println(w + " " + h); //after zoom
    return bitmap;
  }

  public String getPath(Uri uri) {
    String[] projection = {MediaStore.Images.Media.DATA};
    Cursor cursor = managedQuery(uri, projection, null, null, null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    return cursor.getString(column_index);
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


  class SjsqThread extends Thread {
    public void run() {

      try {
        URL url = new URL("http://222.178.109.129:9082/ibs/api/event/save");

        Map<String, Object> params = new LinkedHashMap<String, Object>();


        params.put("senderId", Loginpage.yhjo.getString("id"));
        params.put("title", edit_sqadtress.getText().toString());
        params.put("content", edit_sqbeizhue.getText().toString());
        params.put("submissionDate", shijianstr);
        params.put("eventType", shijiantype);
        params.put("organizationId", orginazitionID);


        Map<String, Object> imgMap = new HashMap<String, Object>();
        imgMap.put("1", "imgOne");
        imgMap.put("2", fileName);
        String resStr = appapi.getWebServiceRestupianwenben(url, params, imgMap);


        mWeiboDialog.dismiss();


        if(resStr.equals("true")) {
          Looper.prepare();
          Toast.makeText(getApplicationContext(), "����ɹ�",
              Toast.LENGTH_SHORT).show();

          if(Loginpage.yhjo.getString("organizationId").equals("2")) {
            Intent intent = new Intent(shijian_page3.this, Roleshichang.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("3")) {
            Intent intent = new Intent(shijian_page3.this, Roleanbao.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("4")) {
            Intent intent = new Intent(shijian_page3.this, Roleengineer.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("5")) {
            Intent intent = new Intent(shijian_page3.this, Rolecehua.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("6")) {
            Intent intent = new Intent(shijian_page3.this, Rolexingzheng.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("7")) {
            Intent intent = new Intent(shijian_page3.this, Rolekefu.class);
            startActivity(intent);

          } else if(Loginpage.yhjo.getString("organizationId").equals("8")) {
            Intent intent = new Intent(shijian_page3.this, Rolezongjingli.class);
            startActivity(intent);
          }
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
      } catch(Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }


    }

  }


  private class ProvOnItemSelectedListener implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      shijianleixing = adapter.getItemAtPosition(position).toString();
      if(shijianleixing.equals("һ���¼�")) {
        shijiantype = "0";
      } else if(shijianleixing.equals("�����¼�")) {
        shijiantype = "1";
      }
      Toast.makeText(getApplicationContext(), shijianleixing, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
      shijianleixing = "ʲôҲûѡ��";
      Toast.makeText(getApplicationContext(), shijianleixing, Toast.LENGTH_LONG).show();

    }

  }


  private class ProvOnItemSelectedListener_mubiaobumen implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      mubiaobumenname = adapter.getItemAtPosition(position).toString();
      if(mubiaobumenname.equals("��ҵ���̲�")) {
        mubiaobumenid = 4;
      } else if(mubiaobumenname.equals("������")) {
        mubiaobumenid = 3;
      } else if(mubiaobumenname.equals("�߻���")) {
        mubiaobumenid = 5;
      } else if(mubiaobumenname.equals("�г���")) {
        mubiaobumenid = 2;
      } else if(mubiaobumenname.equals("������")) {
        mubiaobumenid = 6;
      } else if(mubiaobumenname.equals("�ͷ���")) {
        mubiaobumenid = 7;
      }
      Toast.makeText(getApplicationContext(), "��ѡ��Ŀ�겿��" + mubiaobumenname, Toast.LENGTH_LONG).show();
//		            Log.d("2312312asdsad", mubiaobumenid);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
      shijianleixing = "ʲôҲûѡ��";
      Toast.makeText(getApplicationContext(), shijianleixing, Toast.LENGTH_LONG).show();

    }

  }


  private class ProvOnItemSelectedListener_bumen implements OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
      //��ȡѡ������ֵ
      fasongbumen = adapter.getItemAtPosition(position).toString();
      Toast.makeText(getApplicationContext(), fasongbumen, Toast.LENGTH_LONG).show();
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


  private static Bitmap getBitmapFromBigFile(String path, int width, int height) {
    //У����ת�Ƕ�
    Matrix matrix = new Matrix();
    matrix.postRotate(getBitmapDegree(path));
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    // BitmapFactory.decodeResource(res, resId, options);
    BitmapFactory.decodeFile(path, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, width, height);
    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    Bitmap decodeFile = BitmapFactory.decodeFile(path, options);

    decodeFile = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);

    return decodeFile;
  }


  private static int getBitmapDegree(String path) {
    int degree = 0;
    try {
      // ��ָ��·���¶�ȡͼƬ������ȡ��EXIF��Ϣ
      ExifInterface exifInterface = new ExifInterface(path);
      // ��ȡͼƬ����ת��Ϣ
      int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_NORMAL);
      switch(orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    return degree;
  }

  public Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
    Bitmap returnBm = null;

    // ������ת�Ƕȣ�������ת����
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    try {
      // ��ԭʼͼƬ������ת���������ת�����õ��µ�ͼƬ
      returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    } catch(OutOfMemoryError e) {
    }
    if(returnBm == null) {
      returnBm = bm;
    }
    if(bm != returnBm) {
      bm.recycle();
    }
    return returnBm;
  }

  /**
   * ����������inSampleSize
   *
   * @param options
   * @param reqWidth
   * @param reqHeight
   * @return
   */
  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if(height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and
      // keeps both
      // height and width larger than the requested height and width.
      while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }

}
