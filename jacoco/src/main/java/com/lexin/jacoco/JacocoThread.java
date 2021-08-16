package com.lexin.jacoco;

import android.content.Context;
import android.util.Log;

public class JacocoThread implements Runnable{

    Context context;
    public JacocoThread(Context context){
        this.context = context;
    }
    @Override
    public void run() {
//        Toast.makeText(this.context,"jacocoThread!",Toast.LENGTH_LONG).show();
        Log.i("jacocosdk","hello jacoco");
        //TODO 测试调用SDK生成覆盖率数据文件
        JacocoGenUtil.genJacocoData();
    }
}
