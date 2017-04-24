package com.saike.tzh.utils;

import android.util.Log;

public class Logutils {
/**
 * 项目打印日志
 */
	private static boolean deBug = true;
	private static String str = "";

	public static  void setDeBug(boolean isDebug){
		deBug = isDebug;
	}

	public static void runAtTAG(String TAG){
		if(deBug){
			str = "TzhLoginDate-->>"+TAG+"------";
			Log.d(TAG, str);
		}
	}
	public static void printD(String TAG,String MSG){
		if(deBug){
		str = "TzhLoginDate-->>"+TAG+"-->>"+MSG;
		Log.d(TAG, str);	
		}
	}
	public static void printI(String TAG,String MSG){
		if(deBug){
			str = "TzhLoginDate-->>"+TAG+"-->>"+MSG;
		Log.i(TAG, str);	
		}
	}
	public static void printE(String TAG,String MSG){
		if(deBug){
			str = "TzhLoginDate-->>"+TAG+"-->>"+MSG;
		Log.e(TAG, str);	
		}
	}


}
