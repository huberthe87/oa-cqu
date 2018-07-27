package jiekou;

public class Utils5000 {
	// ���ε����ť֮��ĵ�������������1000����
    private static final int MIN_CLICK_DELAY_TIME = 5000;
    private static long lastClickTime;
 
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
