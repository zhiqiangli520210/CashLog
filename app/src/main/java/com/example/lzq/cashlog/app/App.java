package com.example.lzq.cashlog.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lzq.cashlog.utils.CrashHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public final class App extends Application {
    public static boolean debug = true;

    public static String TOKEN_NAME = "ticket";// 登陆获取的TOKEN
    public static String province;
    public static String city;
    public static String cardNum;// 已绑定银行卡的卡号
    // public static String bankId;//已绑定银行卡的Id
    public static String bankName; // 已绑定银行卡的名字
    public static String bankCardId;// 银行卡id
    public static boolean isCheck = false;//优惠券。被选中的利息券
    private static WeakReference<Application> wr;
    String TAG = App.class.getSimpleName();
    public static String position = null;//优惠券第一个position
    public static int cashCoupons = 0;//现金券
    public static double interestCoupons = 0;//利息券
    public static Set<String> cashSet; //现金券Set
    public static Set<String> interestSet;//利息券set
    public static String nickName;
    public static boolean quickPay = false;
    public static String quickParams;//快捷第一步参数

    public static int currentFragment = 1;//标记的frag
    public static boolean goAccount = true;//用于标记登录成功之后是否要去我的账户。true就是去我的账户。
    //版本更新
    public static int localVersion = 0;
    public static int serverVersion = 0;
    public static String downloadDir = "app/download/";

    public static Boolean isHasContact=false;//是否已经获取通讯录好友数据


    public static List<Activity> myCacheList = new ArrayList<Activity>();
    public static List<Activity> myCacheList2 = new ArrayList<Activity>();
    public static List<Activity> myCacheList3 = new ArrayList<Activity>();
    //暂时保存本地更改关注数
    public static String followNum;

    //用于接收点击“使用密码登录”时，调用密码登录校验接口，返回code=3208时请设置密码页
    public static String phoneStr = "";
    //用于接收点击“去注册”时，跳转"注册/登录惠逛街"页面，同时将手机号带过去
    public static String phoneStr1 = "";
    //是否是第一次打开app
    public static boolean isFirst=false;
    public static String friend;//通讯录中 已经注册惠逛街的好友个数
    public static String friendSize;//本地通讯录中人数



    /**
     * 将activity添加进缓存
     *
     * @param ac
     * @param
     * @author Msz 2014/5/20
     */

    public static void addActivity(Activity ac) {
        myCacheList.add(ac);
    }

    public static void addActivity2(Activity ac) {
        myCacheList2.add(ac);
    }

    public static void addActivity3(Activity ac) {
        myCacheList3.add(ac);
    }

    /**
     * 将activity缓存清空，循环finish掉
     *
     * @author Msz 2014/5/20
     */

    public static void finishActivity() {
        for (int i = 0; i < myCacheList.size(); i++) {
            myCacheList.get(i).finish();
        }
    }

    public static void finishActivity2() {
        for (int i = 0; i < myCacheList2.size(); i++) {
            myCacheList2.get(i).finish();
        }
    }

    public static void finishActivity3() {
        for (int i = 0; i < myCacheList3.size(); i++) {
            myCacheList3.get(i).finish();
        }
    }


    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        App.debug = debug;
    }

    public static String getProvince() {
        return province;
    }

    public static void setProvince(String province) {
        App.province = province;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        App.city = city;
    }

    public static WeakReference<Application> getWr() {
        return wr;
    }

    public static void setWr(WeakReference<Application> wr) {
        App.wr = wr;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String tAG) {
        TAG = tAG;
    }

	/*
     * public static MyActivityManager getmActManager() { return mActManager; }
	 *
	 * public static void setmActManager(MyActivityManager mActManager) {
	 * App.mActManager = mActManager; }
	 */

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 JPush
        wr = new WeakReference<Application>(this);
        Log.i(TAG, "onCreate");


        //抓错误日志
		new Thread(){
			@Override
			public void run() {
				//把异常处理的handler设置到主线程里面
                CrashHandler ch = CrashHandler.getInstance();
				ch.init(getApplicationContext());
			}
		}.start();

    }


    public static Application getApp() {
        return wr.get();
    }

    // 设置公共Title
    public static void setMySubTitleText(TextView title, int titleID) {
        title.setText(titleID);
        title.setTextSize(19);
    }

    public static void setMySubTitleText(TextView title, String strTitle) {

        title.setText(strTitle);
        // title.setTextColor(Color.BLACK);
        // title.setTextSize(24);
    }

    /**
     * 检查网络状态的公用方法, ps:如果没有联网,会先输出Toast提示再返回fslse
     */
    public static boolean isConnectedInternet(Context c) {
        try {
            ConnectivityManager info = (ConnectivityManager) c
                    .getSystemService(c.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = info.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


	/*
	 * // act压栈 public static void addActivity(Activity act) { mActManager =
	 * MyActivityManager.getActivityManager(); mActManager.pushActivity(act); }
	 * 
	 * // act弹栈 public static void removeActivity(Activity act) { mActManager =
	 * MyActivityManager.getActivityManager(); mActManager.popActivity(act); }
	 */

    /*
     * 获取SDK版本号
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;
    }

    /*
     * 获取应用的VersionCode
     */
    public static int getVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /*
     * 获取应用的VersionName
     */
    public static String getVersionName() {
        try {
            return "Android"
                    + getApp().getPackageManager().getPackageInfo(
                    getApp().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return "Android:Exception";
    }

    /**
     * 设置Activity的大小及位置
     *
     * @param atv 当前Activity,一般传this
     * @param x   顶点x 坐标, 如果填0, 则此Activity将在H位置的正中
     * @param y   顶点y 坐标, 如果填0, 则此Activity将在V位置的正中
     * @param f   Activity的宽度
     * @param d   Activity的高度
     */
    public static void setAtvLayout(Activity atv, int x, int y, float f, float d) {
        WindowManager.LayoutParams params = atv.getWindow().getAttributes();
        params.width = (int) f;
        params.height = (int) d;
        params.x = x;
        params.y = y;
        atv.getWindow().setAttributes(params);
    }

    /**
     * 退出程序
     *
     *  0代表正常退出，-1代表异常退出
     */
    public static void exitApplication(Context context) {
		/*
		 * if (FileUtils.isNull(Config.DATA_CACHE) > 100) { // 清除缓存文件
		 * FileUtils.deleteFile(Config.DATA_CACHE); try { App.dbHelper.open(0);
		 * if (App.dbHelper.searchAll(Config.DB_TABLE_NAME[0]).getCount() > 100)
		 * { App.dbHelper.clearTable(Config.DB_TABLE_NAME[0]); } } catch
		 * (Exception e) { e.printStackTrace(); } }
		 */
    }

    /**
     * 检查用户是否登录
     *
     * @return
     */
    public static boolean isUserLogin() {
        try {
			/*
			 * if (？) return true; else return false;
			 */
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
