package jiekou;

import android.view.View;

public abstract class OnMultiClickListener implements View.OnClickListener{
    // ���ε����ť֮��ĵ�������������1000����
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
 
    public abstract void onMultiClick(View v);
 
    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // �������������ٽ�lastClickTime����Ϊ��ǰ���ʱ��
            lastClickTime = curClickTime;
            onMultiClick(v);
        }
    }
}
