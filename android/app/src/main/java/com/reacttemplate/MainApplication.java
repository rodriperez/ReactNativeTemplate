package com.reacttemplate;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

import com.facebook.react.ReactApplication;
import com.remobile.toast.RCTToastPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication,MainApplication.Listener {


    @Override
    public void onPOsitionChanged() {
    }

    interface  Listener{
        void onPOsitionChanged(int currentPosition);
    }
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        li
        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new RCTToastPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        // There is a bug in React Native preventing remote debugging on Android
        // https://github.com/facebook/react-native/issues/12289
        final MediaPlayer mediaPlayer = new MediaPlayer();
        // This is a hack to get around it. Make sure you remove it before releasing
        // as you should never run network calls on the main thread
        if (BuildConfig.DEBUG) {
            strictModePermitAll();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    mediaPlayer.getCurrentPosition();
                   sendBroadcast();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }

    private static void strictModePermitAll() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (Build.VERSION.SDK_INT >= 16) {
            //restore strict mode after onCreate() returns. https://issuetracker.google.com/issues/36951662
            new Handler().postAtFrontOfQueue(new Runnable() {
                @Override
                public void run() {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
            });
        }
    }
}
