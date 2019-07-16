package jp.kentan.dev.customappstore;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(final Context context, final Intent intent) {
        super.onEnabled(context, intent);
        Log.d("MyDeviceAdminReceiver", "onEnabled");
    }

    @Override
    public void onDisabled(final Context context, final Intent intent) {
        super.onDisabled(context, intent);
        Log.d("MyDeviceAdminReceiver", "onDisabled");
    }
}
