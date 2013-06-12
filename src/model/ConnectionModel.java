package model;

import java.security.PublicKey;

import config.UserConfig;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import tools.JsonUtils;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ConnectionModel {
	public JsonUtils jsonutils;
	private static ConnectionModel connectionmodel = null;
	public Context mContext;

	public static ConnectionModel getInstance(Context context) {
		if (connectionmodel == null) {
			connectionmodel = new ConnectionModel(context);

		}
		return connectionmodel;
	}

	private ConnectionModel(Context context) {
		mContext = context;
	}

	/**下载文件进度条
	 * 在这里可以配置进度条，loading窗口，等等效果。
	 * @param host
	 * @param ajaxParams
	 * @param jsonParser
	 */
	public <T> void getDatabyNet(String host, AjaxParams ajaxParams,
			final JsonParser jsonParser) {

		FinalHttp finalHttp = new FinalHttp();
		finalHttp.get(host, ajaxParams, new AjaxCallBack<String>() {
			@Override
			public boolean isProgress() {
				// TODO Auto-generated method stub
				return super.isProgress();
			}

			@Override
			public int getRate() {
				// TODO Auto-generated method stub
				return super.getRate();
			}

			@Override
			public AjaxCallBack<String> progress(boolean progress, int rate) {
				// TODO Auto-generated method stub
				return super.progress(progress, rate);
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				UserConfig.p(this, t);
				jsonParser.parser(t);
				super.onSuccess(t);
			}
		});
	}

	public interface JsonParser {
		void parser(String t);
	}
}
