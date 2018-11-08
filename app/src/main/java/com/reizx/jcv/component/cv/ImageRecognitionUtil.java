package com.reizx.jcv.component.cv;

import lombok.Data;

/**
 * 图像识别工具类。
 * <p>
 * 基于JavaCv完成
 */
public class ImageRecognitionUtil {
    @Data
    public static class MatchResult {
        private boolean isMatch;//是否匹配
        private int x;//X坐标
        private int y;//Y坐标
        private int width;//宽
        private int height;//高
    }



}
