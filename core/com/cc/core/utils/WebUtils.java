package com.cc.core.utils;

import java.io.File;

import com.cc.core.constant.AplicationKeyConstant;

/**
 * 获取项目路径格式
 * @author Ron
 * @createTime 2014.08.30
 */
public class WebUtils {

    /**
     * <li>功能简述:获取项目的实际路径
     * <li>详细描述:WEB-INF
     */
    public static String getContextPath() {

        String name = WebUtils.class.getName();
        name = AplicationKeyConstant.LEFT_SLASH + name.replaceAll(AplicationKeyConstant.CHARACTER_ALL, AplicationKeyConstant.CHARACTER_LEFT) + AplicationKeyConstant.CLASS_FILE_EXTEND_NAME;
        String space = AplicationKeyConstant.SPACE_REPLEACE_STRING;
        String path = WebUtils.class.getResource(name).getPath();
        path = path.substring(0, path.indexOf(AplicationKeyConstant.CONFIG_ROOT) + AplicationKeyConstant.CONFIG_ROOT.length());
        path = path.replaceAll(space, AplicationKeyConstant.CHARACTER_BLANK);
        if (path.startsWith(AplicationKeyConstant.FILE_PROTOCOL)) {
            path = path.substring(AplicationKeyConstant.FILE_PROTOCOL.length());
        }
        return path;
    }

    /**
     * <li>功能简述:获得发布目录路径
     * <li>详细描述:webapps
     */
    public static String getDeployPath() {

        File tempDir = new File(getContextPath());
        return tempDir.getParentFile().getParentFile().getAbsolutePath();
    }

    /**
     * <li>功能简述:获得项目目录
     */
    public static String getWebPath() {

        File tempDir = new File(getContextPath());
        return tempDir.getParentFile().getAbsolutePath();
    }
}
