package com.reizx.jcv.component.cv;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;

import lombok.Data;

import static org.bytedeco.javacpp.opencv_core.CV_32FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

/**
 * 图像识别工具类。
 * <p>
 * 基于JavaCv完成
 */
public class ImageRecognitionUtil {
    /**
     * 匹配图片
     *
     * @param source 原图
     * @param target   在原图中查找的小图
     * @return
     */
    public static MatchResult match(String source, String target) {
        return match(source, target, 0.999f);
    }

    /**
     * 匹配图片
     *
     * @param source    原图
     * @param target      在原图中查找的小图
     * @param precision 范围为0-1, 一般为0.999，匹配度最高，越小匹配度越低。
     * @return
     */
    public static MatchResult match(String source, String target, double precision) {
        //read in image default colors
        opencv_core.Mat sourceColor = imread(source);
        opencv_core.Mat sourceGrey = new opencv_core.Mat(sourceColor.size(), CV_8UC1);
        cvtColor(sourceColor, sourceGrey, COLOR_BGR2GRAY);
        //load in template in grey
        opencv_core.Mat template = imread(target, CV_LOAD_IMAGE_GRAYSCALE);//int = 0
        //Size for the result image
        opencv_core.Size size = new opencv_core.Size(sourceGrey.cols() - template.cols() + 1, sourceGrey.rows() - template.rows() + 1);
        opencv_core.Mat result = new opencv_core.Mat(size, CV_32FC1);
        matchTemplate(sourceGrey, template, result, TM_CCORR_NORMED);

        DoublePointer minVal = new DoublePointer();
        DoublePointer maxVal = new DoublePointer(2);
        opencv_core.Point min = new opencv_core.Point();
        opencv_core.Point max = new opencv_core.Point();
        minMaxLoc(result, minVal, maxVal, min, max, null);//寻找最佳匹配
        if (maxVal.get(0) >= precision) {
            MatchResult matchResult = new MatchResult();
            matchResult.setMatch(true);
            matchResult.setX(max.x());
            matchResult.setY(max.y());
            matchResult.setWidth(template.cols());
            matchResult.setHeight(template.rows());
            matchResult.setMiddleX(max.x() + (template.cols() / 2));
            matchResult.setMiddleX(max.y() + (template.rows() / 2));
        }
        return new MatchResult();
    }

    @Data
    public static class MatchResult {
        private boolean isMatch = false;//是否匹配
        private int x = 0;//X坐标
        private int y = 0;//Y坐标
        private int middleX = 0;//图像中间中心点X坐标
        private int middleY = 0;//图像中间中心点Y坐标
        private int width = 0;//宽
        private int height = 0;//高
    }


}
