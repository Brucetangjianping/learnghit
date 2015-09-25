package com.youzijie.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/21.
 */
public class FileHelper {
    /**
     * 显示一个目录下的所有指定类型文件
     * @param dirName 目录名
     * @param types 文件类型
     * @param start 开始的文件索引
     * @param size 返回的文件数量
     * @return 目录下文件对象
     */
    public static List<File> listFile(String dirName, String[] types, int start, int size) {
        File dir = new File(dirName);
        List<File> files = new ArrayList<File>();

        if ( !dir.exists() ) {
            return files;
        }

        if ( !dir.isDirectory() ) {
            return files;
        }

        List<File> l = new ArrayList<File>(FileUtils.listFiles(dir, types, true));
        if (start < 0 || start >= l.size() || size <= 0) {
            return files;
        } else {
            files = l.subList(start, start + size);
            return files;
        }
    }
}
