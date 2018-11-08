package com.reizx.jcv;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.reizx.jcv.component.cv.ImageRecognitionUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ImageRecognitionTest {
    @Test
    public void test() {
        String target = "/sdcard/find_2.png";
        ImageRecognitionUtil.MatchResult mr = ImageRecognitionUtil.findInScreen(target);
        Log.d("xxx", "xxxxx");
    }
}
