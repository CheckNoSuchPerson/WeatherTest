package com.example.weathertest.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Create by Hastur
 * on 2022/8/7
 */
public class DatabaseCopyUtil {
    public static void copyDataBase(Context context, String dbName) throws IOException {

        InputStream myInput = context.getAssets().open(dbName);

        String outFileName = "/data/data/"

                + context.getApplicationContext().getPackageName()

                + "/databases/";

        File file = new File(outFileName);

        if (!file.exists()) {
            file.mkdirs();
        }

        outFileName = outFileName +  dbName;
        file = new File(outFileName);
        if (file.exists()){
            Log.d("debugt","db exists");
            return;
        }
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];

        int length;

        while ((length = myInput.read(buffer)) > 0) {

            myOutput.write(buffer, 0, length);

        }

        myOutput.flush();

        myOutput.close();

        myInput.close();

    }
}
