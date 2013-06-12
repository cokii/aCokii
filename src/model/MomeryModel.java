package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalDb;
import android.R.id;
import android.content.Context;
import config.UserConfig;

public class MomeryModel<T> {
	private Context context;
	static MomeryModel sqliteModel;
	public Map<String, Object> map;

	private FinalDb db;

	private MomeryModel(Context context) {
		this.context = context;
		db = FinalDb.create(context, "mybigpeoject");
		map = new HashMap<String, Object>();
	}

	public static MomeryModel getinstance(Context context) {
		if (sqliteModel == null) {
			sqliteModel = new MomeryModel(context);
		}
		return sqliteModel;
	}

	/**
	 * 把对象保存到数据库
	 * 
	 * @param entity
	 */
	public void save(Object entity) {
		if (entity != null) {
			// 加入内存
			map.put(getObjectMapKey(entity), entity);
			try {
				// 保存进去数据库
				db.save(entity);
			} catch (Exception e) {
				db.update(entity);
			}
		}

	}

	/**
	 * 把对象从数据库中取出，先判断这一对象是否存在内存中，不在的话再取。
	 * 
	 * @param entity
	 */
	public <T> T get(Class<T> vo, String id) {
		if (map.get(vo.getName() + ":" + id) != null) {
			UserConfig.p(this, "从内存拿");
			return (T) map.get(vo.getName() + ":" + id);
		} else {
			UserConfig.p(this, "从数据库拿");
			T entity = db.findById(id, vo);
			map.put(getObjectMapKey(entity), entity);
			return entity;
		}

	}

	/**
	 * 获取实例的{名称:id}作为缓存map的key
	 * 
	 * @param entity
	 * @return
	 */
	private String getObjectMapKey(Object entity) {

		Method m;
		String value = null;
		try {
			if (entity == null) {
				System.out.println("空");
			}
			m = entity.getClass().getMethod("getId");
			value = (String) m.invoke(entity);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity.getClass().getName() + ":" + value;
	}
}
