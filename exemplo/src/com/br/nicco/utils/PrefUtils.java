package com.br.nicco.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

	
	
	private static String getPrefsKey() {
		// LivroAndroidApplication app = LivroAndroidApplication.getInstance();
		// String key = app.getPrefsTag();
		// return key;
		return null;
	}

	public static void setString(Context context, String flag, String valor) {
		SharedPreferences pref = context.getSharedPreferences(getPrefsKey(), 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(flag, valor);
		editor.commit();
	}

	public static String getString(Context context, String flag) {
		SharedPreferences pref = context.getSharedPreferences(getPrefsKey(), 0);
		String s = pref.getString(flag, null);
		return s;
	}

	public static boolean getBoolean(Context context, String flag) {
		SharedPreferences pref = context.getSharedPreferences(getPrefsKey(), 0);
		boolean b = pref.getBoolean(flag, false);
		return b;
	}

	public static void setBoolean(Context context, String flag, boolean b) {
		SharedPreferences pref = context.getSharedPreferences(getPrefsKey(), 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(flag, b);
		editor.commit();
	}
}
