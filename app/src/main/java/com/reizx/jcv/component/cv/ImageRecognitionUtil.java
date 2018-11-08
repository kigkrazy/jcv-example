package com.reizx.jcv.component.cv;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ShellUtils;

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
    private static String SCREENSHOT_CATCH = "/sdcard/.reizx_screenshot_catch";

    /**
     * 查找当前屏幕是否包含目标图片
     *
     * @param target
     * @return
     */
    public static MatchResult findInScreen(String target) {
        MatchResult matchResult = new MatchResult();
        String sourcImage = String.format("%s/%d.png", SCREENSHOT_CATCH, System.currentTimeMillis());

        String cmds = String.format("mkdir -p %s \n", SCREENSHOT_CATCH);
        cmds = cmds + String.format("screencap -p %s\n", sourcImage);
        ShellUtils.execCmd(cmds, true);
        matchResult = match(sourcImage, target);
        FileUtils.deleteFile(sourcImage);//删除文件
        return matchResult;
    }

    /**
     * 清除截屏缓存，避免强制退出，或者异常导致缓存文件夹无限增大。一般在一个动作流程开始前，或者动作结束后调用。
     */
    public void clearCatch() {
        String cmds = String.format("rm -rf %s/* \n", SCREENSHOT_CATCH);
        ShellUtils.execCmd(cmds, true);
    }

    /**
     * 匹配图片
     *
     * @param source 原图
     * @param target 在原图中查找的小图
     * @return
     */
    public static MatchResult match(String source, String target) {
        return match(source, target, 0.999f);
    }

    /**
     * 匹配图片
     *
     * @param source    原图
     * @param target    在原图中查找的小图
     * @param precision 范围为0-1, 一般为0.999，匹配度最高，越小匹配度越低。
     * @return
     */
    public static MatchResult match(String source, String target, double precision) {
        MatchResult matchResult = new MatchResult();//结果
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
            matchResult = new MatchResult();
            matchResult.setMatch(true);
            matchResult.setX(max.x());
            matchResult.setY(max.y());
            matchResult.setWidth(template.cols());
            matchResult.setHeight(template.rows());
            matchResult.setMiddleX(max.x() + (template.cols() / 2));
            matchResult.setMiddleY(max.y() + (template.rows() / 2));
        }
        return matchResult;
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
