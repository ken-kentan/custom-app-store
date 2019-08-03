package jp.kentan.dev.customappstore;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;

class MyPackageInstaller {

    static void installPackage(Context context, String packageName, InputStream apkStream) throws Exception {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();

        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setAppPackageName(packageName);

        int sessionId = packageInstaller.createSession(params);
        PackageInstaller.Session session = packageInstaller.openSession(sessionId);

        Log.d("MyPackageInstaller", "open session: " + sessionId);

        OutputStream out = session.openWrite(packageName, 0, -1);

        byte[] buffer = new byte[1024];
        int length;
        int count = 0;
        while ((length = apkStream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
            count += length;
        }

        Log.d("MyPackageInstaller", "write: " + count);

        session.fsync(out);
        out.close();
        apkStream.close();

        session.commit(PendingIntent.getBroadcast(context, sessionId,
                new Intent("android.intent.action.MAIN"), 0).getIntentSender());

        Log.d("MyPackageInstaller", "session commit");

        session.close();

        Log.d("MyPackageInstaller", "session close");
    }

    static void uninstallPackage(Activity activity, String packageName) throws Exception {
        PackageInstaller packageInstaller = activity.getPackageManager().getPackageInstaller();

        Intent intent = new Intent(activity, activity.getClass());
        PendingIntent sender = PendingIntent.getActivity(activity, 0, intent, 0);

        packageInstaller.uninstall(packageName, sender.getIntentSender());
    }
}