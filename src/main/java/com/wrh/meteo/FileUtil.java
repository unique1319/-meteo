package com.wrh.meteo;

import com.wrh.meteo.component.exception.FileNotOnlyException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author wrh
 * @version 1.0
 * @describe
 */
public class FileUtil {

    /**
     * 从本地目录读取文件
     *
     * @param dirModel      目录模板,例如：E:/data/${yyyy}/{MM}/${dd}/
     * @param fileNameModel 文件名模板,例如：Z_NWGD_C_BABJ_#[yyyyMMddHHmmss]_P_RFFC_SCMOC-TMP01_${yyyyMMddHHmm}_02401.GRB2
     * @param localDateTime 文件名时次对应localDateTime
     * @return File
     * @throws FileNotFoundException 文件未找到
     * @throws FileNotOnlyException  文件不唯一
     */
    public static File getFileByTimeFmtModel(
            String dirModel,
            String fileNameModel,
            LocalDateTime localDateTime) throws FileNotFoundException, FileNotOnlyException {
        String dir = PropertyPlaceholdeUtil.resolveByTimeFmt(dirModel, localDateTime);
        String fileName = PropertyPlaceholdeUtil.resolveByTimeFmt(fileNameModel, localDateTime);
        fileName = PropertyPlaceholdeUtil.resolveUnFixed2Regex(fileName);
        Collection<File> files = FileUtils.listFiles(new File(dir), FileFilterUtils.and(new RegexFileFilter(fileName)), null);
        if (files.size() <= 0) {
            throw new FileNotFoundException("文件未找到。dir: " + dir + " fileName: " + fileName);
        } else if (files.size() > 1) {
            throw new FileNotOnlyException("文件不唯一。dir: " + dir + " fileName: " + fileName);
        } else {
            return (File) files.toArray()[0];
        }
    }

}
