package jp.kentan.dev.customappstore;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private DevicePolicyManager mDevicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);

        mTextView = findViewById(R.id.text_view);

        mTextView.setText("isDeviceOwnerApp: " + mDevicePolicyManager.isDeviceOwnerApp(getPackageName()));

        findViewById(R.id.install_button).setOnClickListener((view) -> {
            mTextView.setText("Install clicked.");
        });

        findViewById(R.id.uninstall_button).setOnClickListener((view) -> {
            mTextView.setText("Uninstall clicked.");
        });
    }
}
