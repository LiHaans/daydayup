package com.golaxy;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Slf4j
public class MysqlTest {

    public static void main(String[] args) throws Exception {

        Connection conn = getDBConnection1();
        System.out.println(conn);
       /* conn.setAutoCommit(false);
        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY);

        stmt.setFetchSize(5000);

        ResultSet rs = stmt.executeQuery("select KBXXH,PKBH,YXH,KCBH,KCMC,XQH,MXBH,PKJCH,PKJCS,PKJS,PKBJ,RKJSGH,RKJSXM,SFKFCXJS,SFKFCXXS,DKBZ,KKDLB,XQ from JW01.VW_KBXX ");
        //ResultSet rs = stmt.executeQuery("select KCDM,KCMC,ZXS,XF,KCTXMC,KCSXMC,KCXZMC,KCFALX,XN,XQ,QSZ,JSZ,DSZ,SKXQ,DDJ,SKDD,SKJSJGH,SKJSMC,SKJSZC,BJMC,XKFS,KKDM,XH,XM from JW01.VW_XSKB");
        int i = 0;
        long allTime = System.nanoTime();
        long time = System.nanoTime();
        while (rs.next()) {

            if(((System.nanoTime()-time)/(1000*1000*1000)) > 5L) {
                log.info("===============================");
            }
            log.info( "num: {}, 用时: {} ", i, (System.nanoTime()-time)/(1000*1000*1000) );
            i++;
            time = System.nanoTime();
        }

        log.info("end..., 用时: {} " , (System.nanoTime() - allTime)/(1000*1000*1000));*/

    }

    public static Connection getDBConnection()  {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");//加载驱动

            String DB_URL="jdbc:mysql://127.0.0.1:13306/campus?&useSSL=false&serverTimezone=UTC";
            //testjdbc是数据库的名字
            try {
                conn = DriverManager.getConnection(DB_URL,"apps","F9C90164-2689");
                //建立连接
                System.out.println(conn);//打印对象，看是否建立成功
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /* Properties prop = new Properties();
            prop.put("user", "SYSTEM");
            prop.put("password", "yanjiuyuan@123456");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/ORCL19", prop);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getDBConnection1()  {
        Connection conn = null;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties prop = new Properties();
            prop.put("user", "jw01");
            prop.put("password", "ABcd1234");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.150.11.31:1521:orcl", prop);
            /*
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Properties prop = new Properties();
            prop.put("user", "SYSTEM");
            prop.put("password", "yanjiuyuan@123456");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/ORCL19", prop);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }


}
