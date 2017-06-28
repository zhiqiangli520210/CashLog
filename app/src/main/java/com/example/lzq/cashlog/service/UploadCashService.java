package com.example.lzq.cashlog.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @Description: TODO()
 * @author zzw
 * @date 2015-8-7 下午5:25:15
 */
public class UploadCashService extends IntentService {

	public UploadCashService(String name) {
		super(name);
	}
	public UploadCashService() {
		super("UploadCashService");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		upLoadCash(getApplicationContext());
		return super.onStartCommand(intent, flags, startId);

	}
	private void upLoadCash(final Context context) {
		String path= Environment.getExternalStorageDirectory() + "/usershopping/crash.txt";
		final File file=new File(path);
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put("ticket","D3AFEFFC56D8DACDA23B243EFACC2945");
		final Map<String, String> params = new HashMap<String, String>();
		PostFormBuilder post = OkHttpUtils.post()
				.url("http://web.test.hgjvip.cn/selene/res/log/upload")
				.params(params)
				.headers(headers);

			post.addFile("mFile" , System.currentTimeMillis() + Math.random() * 10 + ".txt",
					file);
		post.build().execute(new Callback<Object>() {

					@Override
					public Object parseNetworkResponse(
							Response response)
							throws Exception {
						// TODO Auto-generated method stub
						//上传成功后 删除
//						if (file.exists()){
//							file.delete();
//						}

						return null;
					}

					@Override
					public void onError(Call call,
										Exception e) {
						// TODO Auto-generated method stub
						Toast.makeText(context,"错误日志上传失败",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void inProgress(float progress) {
						super.inProgress(progress);

					}

					@Override
					public void onResponse(Object response) {
						// TODO Auto-generated method stub

					}
				});
	}
}
