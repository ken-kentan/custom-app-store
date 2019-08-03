package jp.kentan.dev.customappstore;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private DevicePolicyManager mDevicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        final PackageInstaller packageInstaller = getPackageManager().getPackageInstaller();

        mTextView = findViewById(R.id.text_view);

        mTextView.setText("isDeviceOwnerApp: " + mDevicePolicyManager.isDeviceOwnerApp(getPackageName()));

        findViewById(R.id.install_button).setOnClickListener((view) -> {
            mTextView.setText("Install clicked.");

            new Thread(() -> {
                try {
                    URL url = new URL("https://example.com/example.apk");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setAllowUserInteraction(false);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestMethod("GET");
                    conn.connect();

                    MyPackageInstaller.installPackage(getApplicationContext(), "com.example", conn.getInputStream());

//                        boolean success = installPackage(getApplicationContext(), conn.getInputStream(), "gtav");
//                        Log.d("INSTALL", "" + success);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        findViewById(R.id.uninstall_button).setOnClickListener((view) -> {
            try {
                MyPackageInstaller.uninstallPackage(MainActivity.this, "com.example");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mTextView.setText("Uninstall clicked.");
        });
    }
}
