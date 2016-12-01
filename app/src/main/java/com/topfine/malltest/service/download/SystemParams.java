package com.topfine.malltest.service.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.topfine.malltest.base.MyApplication;

/**
 * 
 * 保存系统信息
 * @author song
 * @date 2015年11月4日
 */
public class SystemParams {

	private static SystemParams instance;
	private static SharedPreferences sharedPrederences = null;

	private SystemParams() {
		sharedPrederences = MyApplication.getContext().getSharedPreferences("update", Context.MODE_PRIVATE);
	}

	public static SystemParams getInstance() {
		
		if(instance == null) {
			synchronized (SystemParams.class) {
				if(instance == null) {
					 instance = new SystemParams();
				}
			}
		}
		return instance;
	}
	

	public long getLong(String key, long defValue) {
		return sharedPrederences.getLong(key, defValue);
	}		

	public String getString(String key, String defValue) {
		return sharedPrederences.getString(key, defValue);
	}	

	public void setLong(String key, long value) {
		Editor editor = sharedPrederences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public void setString(String key, String value) {
		Editor editor = sharedPrederences.edit();
		editor.putString(key, value);
		editor.commit();
	}

}
