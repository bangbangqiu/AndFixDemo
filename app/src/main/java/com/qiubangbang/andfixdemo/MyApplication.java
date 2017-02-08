package com.qiubangbang.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiubangbang on 2017/2/8.
 * patch文件生成命令(需要patch.bat脚本)：apkpatch -o D:/patchs/ -k And
 * FixDemo.jks -p 123456 -a 123456 -e 123456 -f app_release_fix.apk -t app_release.
 * apk
 * <p>
 * 我理解的原理：andFix：作用只是对方法的替换，不会增加类和修改资源文件，目前觉得oncreate等系统回调方法的修改会出问题（待再次求证）
 * 1：首先通过新旧编译文件对比生成补丁文件
 * 2：有博客指出 在反编译补丁文件后看到方法上多了个@MethodReplace的注解
 * 3：对有注解的方法 通过c++ jni来修改方法的引用，指向新的方法
 * <p>
 * 评论区：问题1：a.apk - b.apk 生成apatch文件修改bug，然后这是又发现bug，理论觉得是c.apk和b.apk生成apatch文件
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
