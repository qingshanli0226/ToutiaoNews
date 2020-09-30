package com.example.toutiaonews.expression;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static String readAssetsFile(Context context, String fileName) {
        try {
            InputStream is = null;
            try {
                is = context.getAssets().open(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int fileLength = is.available();
            byte[] buffer = new byte[fileLength];
            int readLength = is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件是否存在";
    }
}
