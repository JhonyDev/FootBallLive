package com.bakrin.fblive.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {


	/**
	 * check Internet connection available in system
	 */
	public static boolean isConnectingToInternet(Context context){
		try {
			ConnectivityManager connectivity = (ConnectivityManager)
					context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null){
				NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
				if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			        return true;
			    }
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
