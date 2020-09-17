package com.example.common.util;

import android.util.Base64;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;

public class EncryptUtil {

    //输入字符串，然后返回该字符串的信息摘要
    public static String enrypByMd5(String message) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    message.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    //对参数的value进行编码加密
    public static TreeMap<String,String> encryptParamsValueByBase64(TreeMap<String,String> params) {
        TreeMap<String,String> encryptedParams = new TreeMap<>();
        for(String key:params.keySet()) {
            String value = params.get(key);
            byte[] encryptValue = Base64.encode(value.getBytes(), Base64.DEFAULT);
            String encryptStr = new String(encryptValue);
            encryptedParams.put(key,encryptStr);
        }
        return encryptedParams;
    }

    //实现生成json的信息摘要（签名)
    public static String encryptJsonByMd5(JSONObject jParams) {
        StringBuilder sb = new StringBuilder();
        //先排序
        TreeMap<String,String> params = new TreeMap<>();
//        Iterator<String> iterator = jParams.keySet().iterator();
        Iterator<String> iterator = jParams.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                params.put(key, jParams.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //先拼接成字符串
        for(String key:params.keySet()) {
            String value = params.get(key);
            sb.append(key+"="+value+"&");
        }
        sb.append("encrypt=md5");
        String sign = enrypByMd5(sb.toString());
        return sign;
    }

    //使用Base64对jsonobject里面的参数值进行加密
    public static void encryptJsonByBase64(JSONObject jParams) {
//        Iterator<String> iterator = jParams.keySet().iterator();
        Iterator<String> iterator = jParams.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = null;
            try {
                value = jParams.get(key).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String encyptValue = new String(Base64.encode(value.getBytes(),Base64.DEFAULT));
            try {
                jParams.put(key, encyptValue);//使用加密后值来替换之前未加密的值
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java计算文件32位md5值
     * @param fis 输入流
     * @return
     */
    public static String md5HashCode32(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;//解释参见最下方
                if (val < 16) {
                    /**
                     * 如果小于16，那么val值的16进制形式必然为一位，
                     * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
                     * 此处高位补0。
                     */
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
