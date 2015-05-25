package com.cc.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 文件操作工具类
 * @author Ron
 * @createTime 2014.08.30
 */
public class FileUtils {
    public static Logger log = Logger.getLogger(FileUtils.class);
    public static final String FILE_FORMAT_CHAR = ".";

    /**
     * 创建文件，如果文件名称为空，则返回null
     * 
     * @param fileName
     * @return
     */
    public static File createFile(String fileName) {

        if (StringUtils.isNotBlank(fileName)) {
            return new File(fileName);
        }

        return null;
    }

    /**
     * 在文件夹下创建文件
     * 
     * @param folderName
     * @param fileName
     * @return
     */
    public static File createFileInFolder(String folderName, String fileName) {

        if (StringUtils.isNotBlank(fileName)) {
            File folder = createFolder(folderName);
            if (folder != null) {
                return new File(folder, fileName);
            }
        }

        return null;
    }

    /**
     * 在文件夹下创建文件
     * 
     * @param folder
     * @param fileName
     * @return
     */
    public static File createFileInFolder(File folder, String fileName) {

        if (StringUtils.isNotBlank(fileName)) {
            if (folder != null) {
                return new File(folder, fileName);
            }
        }

        return null;
    }

    /**
     * 创建文件夹
     * 
     * @param folderName
     * @return
     */
    public static File createFolder(String folderName) {

        if (StringUtils.isNotBlank(folderName)) {
            File file = new File(folderName);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                } else {
                    log.warn("create folder[" + folderName + "] failed.");
                }
            } else {
                return file;
            }
        }
        return null;
    }

    /**
     * 在文件夹下创建文件夹
     * 
     * @param folder
     * @param folderName
     * @return
     */
    public static File createFolder(File folder, String folderName) {

        if (StringUtils.isNotBlank(folderName)) {
            if (folder == null) {
                return null;
            }
            File file = new File(folder, folderName);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                } else {
                    log.warn("create folder[" + folderName + "] failed.");
                }
            }
        }
        return null;
    }

    /**
     * 在文件夹下创建文件夹
     * 
     * @param folder
     * @param folderName
     * @return
     */
    public static File createFolder(String parentFolderName, String folderName) {

        if (StringUtils.isNotBlank(folderName) && StringUtils.isNotBlank(parentFolderName)) {
            parentFolderName = parentFolderName + File.separator + folderName;
            return createFolder(parentFolderName);
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileName
     */
    public static void deleteFile(String fileName) {

        if (StringUtils.isBlank(fileName)) {
            return;
        }

        File file = new File(fileName);
        file.deleteOnExit();
    }

    /**
     * 将文件sourceFile重命名targetFile
     * @param sourceFile
     * @param targetFile
     */
    public static void moveFile(String sourceFile, String targetFile) {

        if (StringUtils.isBlank(sourceFile) || StringUtils.isBlank(targetFile)) {
            return;
        }

        File file = new File(sourceFile);
        file.renameTo(new File(targetFile));
    }

    /**
     * 将文件sourceFile重命名targetFile
     * @param sourceFile
     * @param targetFolder
     * @param targetFile
     */
    public static void moveFile(String sourceFile, String targetFolder, String targetFile) {

        if (StringUtils.isNotBlank(targetFolder)) {
            createFolder(targetFolder);
        }

        moveFile(sourceFile, targetFile);
    }

    /**
     * 获取文件格式
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {

        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }

        int index = fileName.lastIndexOf(FILE_FORMAT_CHAR);
        if (index > -1) {
            return fileName.substring(index + 1, fileName.length());
        }
        return fileName;
    }

    /**
     * 校验文件的格式
     * @param fileName 文件名称
     * @param fileTypes 合法的文件格式
     * @return
     */
    public static boolean checkFileType(String fileName, String[] fileTypes) {

        if (StringUtils.isBlank(fileName) || fileTypes == null) {
            return true;
        }

        String fileFormat = getFileFormat(fileName);

        for (String fileType : fileTypes) {
            //匹配合法格式
            if (StringUtils.equalsIgnoreCase(fileFormat, fileType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将文件内容按照行读取到list
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readLines(File file) throws IOException {

        List<String> list = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;
        do {
            line = reader.readLine();
            if (line != null) {
                list.add(line);
            }
        } while (line != null);

        return list;
    }

}
