package com.twinstarjoe.tvguide.tvguide;

import android.app.Application;
import android.content.Context;

public class TvGuideApplication extends Application {
    private static TvGuideApplication instance;

    public static TvGuideApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance; // or return instance.getApplicationContext();
    }

}