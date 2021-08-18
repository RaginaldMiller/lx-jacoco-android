package com.lexin.jacoco;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther ken
 * @date 2021/8/18
 */
public class TimerTest {

    static class MyTimerTask extends TimerTask {
        public void run() {
            System.out.println("我爱你中国");
        }
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 5000);

    }
}

