package model;

import tools.JsonUtils;
import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import model.ConnectionModel.JsonParser;

public class CouponsFamilyModel {

	private ConnectionModel connectionModel;
	Handler handler;
	Context mContext;
	private static CouponsFamilyModel couponsFamilyModel = null;
	JsonUtils jsonUtils;

	public CouponsFamilyModel(Context context, Handler handler) {
		connectionModel = ConnectionModel.getInstance(context);
		jsonUtils = new JsonUtils(context);

		mContext = context;
	}

	public static CouponsFamilyModel getinstance(Context context,
			Handler handler) {
		if (couponsFamilyModel == null) {
			couponsFamilyModel = new CouponsFamilyModel(context, handler);
		}
		
		return couponsFamilyModel;
	}

	public void download() {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("num", "10");
		ajaxParams.put("time", "");
		ajaxParams.put("than", "");
		ajaxParams.put("orderBy", "");
		connectionModel
				.getDatabyNet(
						"http://1.onedollar.sinaapp.com/phone/coupon-class-list.action",
						ajaxParams, new JsonParser() {
							@Override
							public void parser(String t) {
								jsonUtils.parseJsonToEntity(t, handler);
							}
						});
	}
}
