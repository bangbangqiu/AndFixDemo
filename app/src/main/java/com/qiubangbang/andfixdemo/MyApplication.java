package com.qiubangbang.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiubangbang on 2017/2/8.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private PatchManager patchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // 1.Initialize PatchManager
        patchManager = new PatchManager(this);
        patchManager.init(BuildConfig.VERSION_NAME);
        // 2.Load patch
        patchManager.loadPatch();

        //3 add patch
        File file = new File(Environment.getExternalStorageDirectory(), BuildConfig.VERSION_NAME + ".apatch");

        if (patchManager != null) {
            //add patch
            try {
                patchManager.addPatch(file.getAbsolutePath());
            } catch (IOException e) {
                Log.e(TAG, "onAddPatchClick: " + e.toString());
            }
        }
    }
}
