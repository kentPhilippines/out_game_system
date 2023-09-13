package com.lt.win.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * 文件缓存通用类
 * </p>
 *
 * @author andy
 * @since 2020/5/7
 */
public class FileUtils {
    /**
     * 文件缓存目录
     */
    public static final String FILE_CACHE_PATH = "FILE_CACHE";
    /**
     * 公告管理-查询UID(站内信):文件名
     */
    public static final String FILE_NAME_TO_UID_LIST = "uidList.txt";
    public static final String FILE_NAME_AG_GAME_TYPES_FILENAME = "ag_game_types_";
    public static final String FILE_NAME_AG_GAME_PLAY_TYPES_FILENAME = "ag_game_play_types_";

    private FileUtils() {
    }

    /**
     * 公告管理-查询UID(站内信):文件名
     * <p>
     * 描述：文件全路径+文件名
     *
     * @return
     */
    public static Path getToUidListFileName() {
        Path path = Paths.get(mkdir(FILE_CACHE_PATH).toURI());
        if (null != path) {
            path = Paths.get(path.getFileName() + File.separator + FILE_NAME_TO_UID_LIST);
        }
        return path;
    }

    /**
     * AG拉单:获取游戏玩法下注类型
     * <p>
     * 描述：文件全路径+文件名
     *
     * @return
     */
    public static Path getGamePlayTypesFileName(String language) {
        Path path = Paths.get(mkdir(FILE_CACHE_PATH).toURI());
        if (null != path) {
            path = Paths.get(path.getFileName() + File.separator + FILE_NAME_AG_GAME_PLAY_TYPES_FILENAME + language + ".txt");
        }
        return path;
    }

    /**
     * AG拉单:获取游戏类型
     * <p>
     * 描述：文件全路径+文件名
     *
     * @return
     */
    public static Path getAgGameTypesFileName(String language) {
        Path path = Paths.get(mkdir(FILE_CACHE_PATH).toURI());
        if (null != path) {
            path = Paths.get(path.getFileName() + File.separator + FILE_NAME_AG_GAME_TYPES_FILENAME + language + ".txt");
        }
        return path;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static File mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExpandedName(String fileName) {
        String ext = "";
        if (StringUtils.isNotBlank(fileName) &&
                StringUtils.contains(fileName, ".")) {
            ext = StringUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
        }
        ext = ext.toLowerCase();
        if (StringUtils.isBlank(ext)) {
            return "jpg";
        }
        return ext;
    }
}
