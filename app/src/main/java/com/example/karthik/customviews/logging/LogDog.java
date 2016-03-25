package com.example.karthik.customviews.logging;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

public class LogDog {
  private Context context;
  private Class<?> serviceClass;

  public LogDog(Context context) {
    this.context = context;
  }

  public void withService(Class<?> serviceClass) {
    this.serviceClass = serviceClass;
  }

  public String simStatus() {
    TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    int simState = telMgr.getSimState();
    switch (simState) {
      case TelephonyManager.SIM_STATE_ABSENT:
        return "SIM card absent";
      case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
        return "SIM Network locked";
      case TelephonyManager.SIM_STATE_PIN_REQUIRED:
        return "SIM card PIN required";
      case TelephonyManager.SIM_STATE_PUK_REQUIRED:
        return "SIM card PUK code required";
      case TelephonyManager.SIM_STATE_READY:
        return "OK";
      case TelephonyManager.SIM_STATE_UNKNOWN:
        return "UNKNOWN";
      default:
        return "UNKNOWN";
    }
  }

  public String logAll(String tag) {
    StringBuilder builder = new StringBuilder();
    builder.append("SIM Status : ")
        .append(simStatus())
        .append(" Network Status : ")
        .append(NetworkUtil.getConnectivityStatus(context))
        .append(" GPS enabled : ")
        .append(getGpsStatus());
    if(serviceClass != null) {
      builder.append(" ")
          .append(serviceClass.getSimpleName())
          .append( " isON : ")
          .append(isServiceRunning());
    }
    builder.append(" Logged Time : ")
        .append(deviceDateTime());
    Log.d(tag, builder.toString());
    return builder.toString();
  }

  public boolean isServiceRunning() {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
        Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  public String deviceDateTime() {
    return new Date().toString();
  }

  public String getGpsStatus() {
    final LocationManager manager = (LocationManager) context.getSystemService(Context
        .LOCATION_SERVICE);
    return String.valueOf(manager.isProviderEnabled(LocationManager.GPS_PROVIDER));
  }

  public static class NetworkUtil {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;


    private static int getConnectionStatus(Context context) {
      ConnectivityManager cm = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);

      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
      if (null != activeNetwork) {
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
          return TYPE_WIFI;

        if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
          return TYPE_MOBILE;
      }
      return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatus(Context context) {
      int conn = NetworkUtil.getConnectionStatus(context);
      String status = null;
      if (conn == NetworkUtil.TYPE_WIFI) {
        status = "Wifi enabled";
      } else if (conn == NetworkUtil.TYPE_MOBILE) {
        status = getMobileDataType(context) + " Data enabled";
      } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
        status = "Not connected to Internet";
      }
      return status;
    }
  }

  public static String getMobileDataType(Context context) {
    TelephonyManager mTelephonyManager = (TelephonyManager)
        context.getSystemService(Context.TELEPHONY_SERVICE);
    int networkType = mTelephonyManager.getNetworkType();
    switch (networkType) {
      case TelephonyManager.NETWORK_TYPE_GPRS:
      case TelephonyManager.NETWORK_TYPE_EDGE:
      case TelephonyManager.NETWORK_TYPE_CDMA:
      case TelephonyManager.NETWORK_TYPE_1xRTT:
      case TelephonyManager.NETWORK_TYPE_IDEN:
        return "2G";
      case TelephonyManager.NETWORK_TYPE_UMTS:
      case TelephonyManager.NETWORK_TYPE_EVDO_0:
      case TelephonyManager.NETWORK_TYPE_EVDO_A:
      case TelephonyManager.NETWORK_TYPE_HSDPA:
      case TelephonyManager.NETWORK_TYPE_HSUPA:
      case TelephonyManager.NETWORK_TYPE_HSPA:
      case TelephonyManager.NETWORK_TYPE_EVDO_B:
      case TelephonyManager.NETWORK_TYPE_EHRPD:
      case TelephonyManager.NETWORK_TYPE_HSPAP:
        return "3G";
      case TelephonyManager.NETWORK_TYPE_LTE:
        return "4G";
      default:
        return "Unknown";
    }
  }
}
