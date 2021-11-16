package com.lx.jacoco.util;

import static java.lang.String.valueOf;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @auther ken
 * @date 2021/8/17
 */
public class UploadUtil {
    //private static final String UPLOAD_URL = "http://10.1.130.173:8000/kenlu/file/upload";
    // private static final String UPLOAD_URL = "http://localhost:8088/kenlu/file/upload";
     private static final String UPLOAD_URL = "http://10.9.16.223:8888/kenlu/file/upload";

    public static boolean upload(final Map<String, Object> map, File file) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = RequestBody.create(mediaType, file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(UPLOAD_URL).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i("lfq", response.message() + " , body " + str);

                } else {
                    Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        });

        return true;

    }

    public static boolean upload(File file) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = RequestBody.create(mediaType, file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        Request request = new Request.Builder().url(UPLOAD_URL).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i("lfq", response.message() + " , body " + str);

                } else {
                    Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        });
        return true;

    }
    public static void main(String[] args) {

//        Map<String,Object> map = new HashMap<>();
//        map.put("version","1.0.1");
//        map.put("branch","v6.7.0");
//        File file =new File("/Users/lexin/Desktop/build.gradle");
//        upload(file);

        File dir = new File("/Users/lexin/Desktop/test-jacocofiles/");
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isFile() && file.getName().endsWith(".ec") &&file.getName().contains("c8c97f24")){
                upload(file);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

