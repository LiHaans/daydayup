package com.golaxy;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.Properties;

@Slf4j
public class OracleTest {

    public static void main(String[] args) throws SQLException {
        oracleOci();
    }

    public static void oracleOci() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:@//10.150.11.31:1521/orcl");
        ods.setUser("jw01");
        ods.setPassword("ABcd1234");

        Connection connection = ods.getConnection();
        OracleStatement statement = (OracleStatement)connection.createStatement();
        statement.setFetchSize(2000);
        ResultSet rs = statement.executeQuery("select KBXXH,PKBH,YXH,KCBH,KCMC,XQH,MXBH,PKJCH,PKJCS,PKJS,PKBJ,RKJSGH,RKJSXM,SFKFCXJS,SFKFCXXS,DKBZ,KKDLB,XQ from JW01.VW_KBXX ");

        //ResultSet rs = statement.executeQuery("select XM from JW01.VW_XSKB");

        int i = 0;
        long allTime = System.nanoTime();
        long time = System.nanoTime();
        while (rs.next()) {
            System.out.println(rs.getObject(1));
            if(((System.nanoTime()-time)/(1000*1000*1000)) > 5L) {
                System.out.println(i +" -> "+ (System.nanoTime()-time)/(1000*1000*1000) );
            }
            i++;
            time = System.nanoTime();
        }

        System.out.println("end... " + (System.nanoTime() - allTime)/(1000*1000*1000));

    }

    public static Connection getDBConnection()  {
        Connection conn = null;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties prop = new Properties();
            prop.put("user", "jw01");
            prop.put("password", "ABcd1234");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.150.11.31:1521/orcl", prop);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }


}
