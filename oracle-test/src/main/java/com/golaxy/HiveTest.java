package com.golaxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

public class HiveTest {

    public static void main(String[] args) throws Exception {
        //Class.forName("oracle.jdbc.OracleDriver");


        Connection conn = getDBConnection();


        conn.setAutoCommit(false);


        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY);

        stmt.setFetchSize(5000);

        ResultSet rs = stmt.executeQuery("select * from ods_vw_kbxx ");
        //ResultSet rs = stmt.executeQuery("select KCDM,KCMC,ZXS,XF,KCTXMC,KCSXMC,KCXZMC,KCFALX,XN,XQ,QSZ,JSZ,DSZ,SKXQ,DDJ,SKDD,SKJSJGH,SKJSMC,SKJSZC,BJMC,XKFS,KKDM,XH,XM from JW01.VW_XSKB");
        int i = 0;
        long allTime = System.nanoTime();
        long time = System.nanoTime();
        while (rs.next()) {

            if(((System.nanoTime()-time)/(1000*1000*1000)) > 5L) {
                System.out.println(new Date() + " ---->  "+ i +" -> "+ (System.nanoTime()-time)/(1000*1000*1000) );
            }else {
                System.out.println(new Date() + "-)  "+ i +" -> "+ (System.nanoTime()-time)/(1000*1000*1000) );
            }
            i++;
            time = System.nanoTime();
        }

        System.out.println("end... " + (System.nanoTime() - allTime)/(1000*1000*1000));

    }

    public static Connection getDBConnection()  {
        Connection conn = null;
        try {

            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Properties prop = new Properties();
            prop.put("user", "lih");
            prop.put("password", "ABcd1234");
            conn = DriverManager.getConnection("jdbc:hive2://36.111.28.104:10000", prop);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

}
