package com.example.smlightwai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smlightwai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class lishizhibanlist extends Activity {
  private ListView lv;
  private JSONArray ja;//�豸����list
  private String shijianstr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.lishizhibanlist);


    ImageButton btn_back = (ImageButton) findViewById(R.id.dangtianzhibanfanghui);


//		btn_back.setOnClickListener(new View.OnClickListener(){  
//			@Override  
//			public void onClick(View v){  
//				
//				Intent intent = new Intent(dangtianzhibanlist.this , shijian_page2.class);  
//				startActivity(intent);  
//
//			}  
//			}); 		


    lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������

//		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//		    @Override
//		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		    	//Intent��һ������ʱ�󶨣�run-time binding�����ƣ������ڳ������й���������������ͬ�������
//		    	
//					Intent intent = new Intent(dangtianzhibanlist.this , jieshouxiaoxineirong.class);  //?????????
//					////����  
//					
//					Bundle bundle=new Bundle();
//				    //����name����Ϊtinyphp
//				    bundle.putString("jieshouxiaoxineirong", ja.optJSONObject(position).toString());
//				    intent.putExtras(bundle);
//					
//					startActivity(intent); 		
//		    }
//		});


    //�����ת������list��ʾ��Ϣ
    Bundle bundle = this.getIntent().getExtras();
    String shijianlist = "{\"lszblist\":" + bundle.getString("lishizhibanlist") + "}";
    try {
      JSONObject jo = new JSONObject(shijianlist);
      //����豸����list
      ja = jo.getJSONArray("lszblist");
      int n = ja.length();
      String strs[] = new String[n];
      for(int i = 0; i < n; i++) {
        JSONObject ob = ja.getJSONObject(i);
        strs[i] = "   " + ob.getString("workBeginTime") + "����" + ob.getString("workEndTime") + "                      " + ob.getString("personChn");
      }


      /*ΪListView����Adapter��������*/
      lv.setAdapter(new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, strs));
    } catch(JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }


}
