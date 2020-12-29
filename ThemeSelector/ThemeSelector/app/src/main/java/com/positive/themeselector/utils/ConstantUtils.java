package com.positive.themeselector.utils;

import android.content.Context;

import java.io.File;

public class ConstantUtils {

    public static String GetSoundPath(Context context) {
        String spath = context.getApplicationInfo().dataDir + "/files/Sound/";
        if (!new File(spath).exists())
            new File(spath).mkdirs();

        return spath;
    }

    public static String GetImagePath(Context context) {
        String spath = context.getApplicationInfo().dataDir + "/files/Images/";
        if (!new File(spath).exists())
            new File(spath).mkdirs();

        return spath;
    }

}
