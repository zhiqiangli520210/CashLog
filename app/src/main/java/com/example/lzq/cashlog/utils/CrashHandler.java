package com.example.lzq.cashlog.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类 来接管程序,并记录错误报告.
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	private static CrashHandler myCrashHandler;

	private CrashHandler() {
	};

	private Context context;
	public final static String LOGPATH =Environment.getExternalStorageDirectory() + "/usershopping/crash.txt" ;
	private UncaughtExceptionHandler defaultExceptionHandler;
	public synchronized static CrashHandler getInstance() {
		if (myCrashHandler == null) {
			myCrashHandler = new CrashHandler();
		}
		return myCrashHandler;

	}

	public void init(Context context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void uncaughtException(Thread thread, Throwable ex) {
		if(!sdCardIsAvailable()){
			ex.printStackTrace();
			new Thread() {

				@Override
				public void run() {
					Looper.prepare();
					Toast.makeText(context, "异常退出", Toast.LENGTH_LONG).show();
					Looper.loop();
				}

			}.start();

			new Thread() {

				@Override
				public void run() {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					android.os.Process.killProcess(android.os.Process.myPid());
				}

			}.start();

		    return;
		}
		try {
//			Writer info2 = new StringWriter();   
//	        PrintWriter printWriter = new PrintWriter(info2);   
//	        ex.printStackTrace(printWriter);   
//	        Throwable cause = ex.getCause();   
//	        while (cause != null) {   
//	            cause.printStackTrace(printWriter);   
//	            cause = cause.getCause();   
//	        }   
//	        result = info2.toString(); 
			// 在throwable的参数里面保存的有程序的异常信息
			StringBuffer sb = new StringBuffer();
			// 1.得到手机的版本信息 硬件信息
			Field[] fields = Build.class.getDeclaredFields();
			for (Field filed : fields) {
				filed.setAccessible(true); // 暴力反射
				String name = filed.getName();
				String value = filed.get(null).toString();
				sb.append(name);
				sb.append("=");
				sb.append(value);
				sb.append("\n");
			}

			// 2.得到当前程序的版本号
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			sb.append(info.versionName);
			sb.append("\n");
			// 3.得到当前程序的异常信息
			Writer writer = new StringWriter();
			PrintWriter printwriter = new PrintWriter(writer);

			ex.printStackTrace(printwriter);
			printwriter.flush();
			printwriter.close();

			sb.append(writer.toString());

			File ff = new File(LOGPATH);
			ff.createNewFile(); 
			FileWriter fw = new FileWriter(new File(LOGPATH));
			fw.write(sb.toString());
//			System.out.println(sb);
			fw.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		new Thread() {

			@Override
			public void run() {
				Looper.prepare();

				Toast.makeText(context, "异常退出", Toast.LENGTH_LONG).show();
				Looper.loop();

			}

		}.start();

		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
			}

		}.start();

	}

	/**
	 * 检测sdcard是否可用
	 *
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}
}
