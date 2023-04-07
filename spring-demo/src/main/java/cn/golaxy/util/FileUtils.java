package cn.golaxy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lihang
 */
@Slf4j
public class FileUtils {


    /**
     *  下载文件流到本地
     * @param inputStream
     * @param destDir
     * @param destFileName
     * @throws IOException
     */
    public static void saveInputStreamToLocal(InputStream inputStream, String destDir, String destFileName) throws Exception {

        try (FileOutputStream fileOutputStream = new FileOutputStream(destDir + destFileName);
             BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();

            fileOutputStream.close();
            outputStream.close();
            log.info("保存文件到本地, dir: {}, fileName: {}", destDir, destFileName);
        }

    }

    /**
     *  创建文件目录
     * @param directoryPath
     */
    public static void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            log.info("创建目录: {}", directoryPath);
        }
    }

    /**
     *  创建临时文件夹
     * @return
     */
    public static String createTmpDir() {
        String rootPath = System.getProperty("user.dir");
        rootPath = rootPath + "/" + "tmp" + "/" + UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "/";

        createDirectoryIfNotExists(rootPath);
        return rootPath;
    }


    /**
     *  删除本地文件
     * @param localFilePath
     */
    public static void deleteFile(String localFilePath) {
        try {
            File file = new File(localFilePath);

            if (file.exists()) {
                if (file.delete()) {
                    log.info("删除本地文件: {}", localFilePath);
                } else {
                    log.error("删除本地文件失败: {}", localFilePath);
                }
            } else {
                log.warn("删除文件失败, 文件不存在: {}", localFilePath);
            }

        } catch (Exception e) {
            log.error("删除文件异常, filePath: {}, ex: {}", localFilePath, e.getMessage());
        }
    }

    /**
     *  压缩文件到本地
     * @param srcFileNames
     * @param destDir
     * @param destFileName
     */
    public static void zip(List<String > srcFileNames, String destDir, String destFileName) {
        log.info("压缩文件到: {} {}", destDir, destFileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(destDir + destFileName);
             BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)
        ) {

            for (String srcFileName : srcFileNames) {

                ZipEntry zipEntry = null;

                try (FileInputStream fileInputStream = new FileInputStream(srcFileName);
                     BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
                ) {

                    String fileName = "";
                    int index = srcFileName.lastIndexOf("/");

                    if (index > 0) {
                        fileName = srcFileName.substring(index + 1);
                    } else {
                        fileName = srcFileName;
                    }

                    zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);

                    int len;
                    // 定义每次读取的字节数组
                    byte[] buffer = new byte[1024];
                    while ((len =  inputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0,  len);
                    }
                } catch (Exception e) {
                    log.error("压缩读取文件异常: {}", e.getMessage());
                }
                zipOutputStream.flush();
            }

        } catch (Exception e) {
            log.error("压缩文件异常: {}", e.getMessage());
        }
    }

    public static List<String> unzip(String srcFile, String destDir) throws Exception {
        ArrayList<String> destFileNames = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(srcFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             ZipArchiveInputStream zis = new ZipArchiveInputStream(bufferedInputStream)
        ) {
            byte[] buffer = new byte[1024];

            int bufferSize = 1024;

            ArchiveEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                String fileName = destDir + entry.getName();

                try (FileOutputStream fos = new FileOutputStream(fileName);
                     BufferedOutputStream bos = new BufferedOutputStream(fos);
                ) {
                    int count;

                    while ((count = zis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, count);
                    }

                    bos.flush();

                } catch (Exception e) {
                    log.info("解压文件-写入异常: {}", e.getMessage());
                }

                destFileNames.add(fileName);
            }

            log.info("解压后文件: {}", String.join(",", destFileNames));
            return destFileNames;
        }

    }

}
