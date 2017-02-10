package com.qiubangbang.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiubangbang on 2017/2/8.
 * patch文件生成命令(需要patch.bat脚本)：apkpatch -o D:/patchs/ -k vmoney.jks -p weixin_vmoney -a appvmoney -e weixin_appVmoney -f app_release_1.apk -t app_release_raw.apk
 * <p>
 * 我理解的原理：andFix：作用只是对方法的替换，不会增加类和修改资源文件，目前觉得oncreate等系统回调方法的修改会出问题（待再次求证）
 * 1：首先通过新旧编译文件对比生成补丁文件
 * 2：有博客指出 在反编译补丁文件后看到方法上多了个@MethodReplace的注解
 * 3：对有注解的方法 通过c++ jni来修改方法的引用，指向新的方法
 * <p>
 * 断断续续的看了两天的andfix 决定放弃在项目中使用。坑确实很多，加了混淆文件 老是报错com.alibaba.annation.xxxx那个注解的类找不到
 * 然后我上网查了，有文章说加-applyMapping ..仍然不起作用。
 * 我干脆去掉混淆，然后开启app不崩溃了，但是进入主页又崩溃了（我改了主页的某些方法）。错误是：getresource（）找不到这个方法。
 * 网上查，有的说andfix对某些方法无效。
 *
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
