package tools;

import model.MomeryModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vo.CouponsFamily;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class JsonUtils {
	Context context;

	public JsonUtils(Context context) {
		this.context = context;
	}

	public void parseJsonToEntity(String jsonData, Handler handler) {
		JSONArray array = null;
		CouponsFamily couponsFamily = new CouponsFamily();
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			array = jsonObject.getJSONArray("couponClassList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsb = array.getJSONObject(i);
				couponsFamily.id = jsb.getString("id");
				couponsFamily.accountnum = jsb.getString("accountNum");
				couponsFamily.accountreserved = jsb
						.getString("accountReserved");
				couponsFamily.commentsum = jsb.getString("commentSum");
				couponsFamily.coupondetail = jsb.getString("couponDetail");
				couponsFamily.couponname = jsb.getString("couponName");
				couponsFamily.createuser = jsb.getString("createUser");
				couponsFamily.validityend = jsb.getString("validityEnd");
				couponsFamily.validitydays = jsb.getString("validityDays");
				couponsFamily.couponstips = jsb.getString("couponTips");
				couponsFamily.validitybegin = jsb.getString("validityBegin");
				couponsFamily.state = jsb.getString("state");
				JSONArray phjsa = jsb.getJSONArray("imgs");
				try {
					JSONObject phjsb = phjsa.getJSONObject(0);
					couponsFamily.imageurl = phjsb.getString("imgUrl");
				} catch (Exception e) {
					// TODO: handle exception
				}
				MomeryModel.getinstance(context).save(couponsFamily);

			}
			handler.sendMessage(new Message());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
