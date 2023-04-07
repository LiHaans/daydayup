package com.golaxy.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Component
public class HiveWriteUtil {

    private static String fieldDelimiter = "\u0001";
    private static String timestampFormatStr = "yyyy-MM-dd HH:mm:ss";
    private static String dateFormatStr = "yyyy-MM-dd";

    private FileSystem fileSystem;
    private  Configuration conf;

    @Value("${hive-writer.defaultFS}")
    private String defaultFS;
    @Value("${hive-writer.jdbc-url}")
    private String jdbcUrl;
    @Value("${hive-writer.username}")
    private String username;
    @Value("${hive-writer.password}")
    private String password;
    @Value("${hive-writer.tmp-path}")
    private String tmpPath;
    @Value("${hive-writer.name-services}")
    private String nameServices;
    @Value("${hive-writer.ha-node1-service-name}")
    private String node1;
    @Value("${hive-writer.ha-node2-service-name}")
    private String node2;
    @Value("${hive-writer.ha-node1-service-url}")
    private String node1Url;
    @Value("${hive-writer.ha-node2-service-url}")
    private String node2Url;

    public void startWrite(String[] strLines, String tableName) throws Exception {
        System.setProperty("HADOOP_USER_NAME","root");

        // 配置hdfs连接
        conf=new Configuration();
        conf.set("fs.defaultFS", defaultFS);
        conf.set("dfs.nameservices", nameServices);
        conf.set("dfs.ha.namenodes."+nameServices, node1+","+ node2);
        conf.set("dfs.namenode.rpc-address."+nameServices+"."+node1, node1Url);
        conf.set("dfs.namenode.rpc-address."+nameServices+"."+node2, node2Url);
        conf.set("dfs.client.failover.proxy.provider."+nameServices,
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

        fileSystem = FileSystem.get(conf);
        checkFilePath(tmpPath);
        String fileName = tmpPath + tableName + "_" + System.nanoTime();

        // excel数据写入hdfs
        FSDataOutputStream out = fileSystem.create(new Path(fileName));
        for (String str:strLines) {
            out.write(str.getBytes());
            out.writeBytes("\n");
        }
        out.close();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getDBConnection();

            // load文件到hive
            String sql = "LOAD DATA INPATH '" + fileName + "' INTO TABLE "
                    + tableName ;
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(sql);
            boolean execute = preparedStatement.execute();
            log.info(String.valueOf(execute));
        }catch (Exception e) {
            throw e;
        } finally {
            // 关闭连接
            try {
                if (preparedStatement != null ){
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        // 删除临时文件
        log.info("delete file {}.", fileName);
        Path path = new Path(fileName);
        try {
            fileSystem.delete(path,true);
        } catch (Exception e) {
            String message = String.format("删除文件[%s]时发生IO异常,请检查您的网络是否正常！",
                    path);
            log.error(message);
            throw new Exception(message);
        }
    }

    /**
     *  检查文件路径
     * @param filePath
     * @throws Exception
     */
    private void checkFilePath(String filePath) throws Exception {
        //若路径已经存在，检查path是否是目录
        if(!isPathexists(filePath)){
            if(!mkdir(filePath)) {
                throw new Exception(String.format("您配置的path: [%s] 创建失败.", filePath));
            }
        }

        if(!isPathDir(filePath)){
            throw new Exception(
                    String.format("您配置的path: [%s] 不是一个合法的目录, 请您注意文件重名, 不合法目录名等情况.",
                            filePath));
        }

    }

    /**
     *  创建文件夹
     * @param filePath
     * @return
     * @throws Exception
     */
    public boolean mkdir(String filePath) throws Exception {
        Path path = new Path(filePath);
        boolean exist = false;
        try {
            exist = fileSystem.mkdirs(path);
        } catch (IOException e) {
            String message = String.format("创建文件路径[%s]是否存在时发生网络IO异常,请检查您的网络是否正常！",
                    "message:filePath =" + filePath);
            log.error(message);
            throw new Exception(message);
        }
        return exist;
    }

    /**
     *  判断路径是否存在
     * @param filePath
     * @return
     * @throws Exception
     */
    public boolean isPathexists(String filePath) throws Exception {
        Path path = new Path(filePath);
        boolean exist = false;
        try {
            exist = fileSystem.exists(path);
        } catch (IOException e) {
            String message = String.format("判断文件路径[%s]是否存在时发生网络IO异常,请检查您的网络是否正常！",
                    "message:filePath =" + filePath);
            log.error(message);
            throw new Exception(message);
        }
        return exist;
    }


    /**
     *  判断路径是否是文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public boolean isPathDir(String filePath) throws Exception {
        Path path = new Path(filePath);
        boolean isDir = false;
        try {
            isDir = fileSystem.isDirectory(path);
        } catch (IOException e) {
            String message = String.format("判断路径[%s]是否是目录时发生网络IO异常,请检查您的网络是否正常！", filePath);
            log.error(message);
            throw new Exception(message);
        }
        return isDir;
    }

    /**
     *  获取hive jdbc连接
     * @return
     */
    public Connection getDBConnection()  {
        Connection conn = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Properties prop = new Properties();
            prop.put("user", username);
            prop.put("password", password);
            conn = DriverManager.getConnection(jdbcUrl, prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
