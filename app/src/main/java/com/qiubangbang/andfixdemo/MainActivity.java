package com.qiubangbang.andfixdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView mTv;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(this, "i am patch", Toast.LENGTH_SHORT).show();

        //patch 文件的位置 这种方式貌似不行，改到application中试一试
//        File file = new File(Environment.getExternalStorageDirectory(), BuildConfig.VERSION_NAME + ".apatch");
//
//        MyApplication myApp = (MyApplication) getApplication();
//        if (myApp.patchManager != null) {
//            //add patch
//            try {
//                myApp.patchManager.addPatch(file.getAbsolutePath());
//            } catch (IOException e) {
//                Log.e(TAG, "onAddPatchClick: " + e.toString());
//            }
//        }
    }


}
