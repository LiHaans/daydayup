package cn.golaxy.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

import java.net.UnknownHostException;

public class FtpTest {

    public static void main(String[] args) {
        String host = "192.168.130.130";
        Integer port = 21;
        String username = "ftpuser";
        String password = "golaxy@123";
        String connectMode = "PASV";
        String directoryPath = "/";

        FTPClient ftpClient = new FTPClient();
        try {
            // 连接
            ftpClient.connect(host, port);
            // 登录
            ftpClient.login(username, password);
            // 不需要写死ftp server的OS TYPE,FTPClient getSystemType()方法会自动识别
             //ftpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
            ftpClient.setConnectTimeout(60000);
            ftpClient.setDataTimeout(60000);
            if ("PASV".equals(connectMode)) {
                ftpClient.enterRemotePassiveMode();
                ftpClient.enterLocalPassiveMode();
            } else if ("PORT".equals(connectMode)) {
                ftpClient.enterLocalActiveMode();
                // ftpClient.enterRemoteActiveMode(host, port);
            }
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                String message = String.format("与ftp服务器建立连接失败,请检查用户名和密码是否正确: [%s]",
                        "message:host =" + host + ",username = " + username + ",port =" + port);
                System.out.println(message);
            }
            //设置命令传输编码
            //String fileEncoding = System.getProperty("file.encoding");
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            System.out.println(" ftp 服务器连接成功 ");

            boolean b1 = ftpClient.changeWorkingDirectory(new String(directoryPath.getBytes(), FTP.DEFAULT_CONTROL_ENCODING));

            System.out.println("b1 : " +b1);
            ftpClient.enterLocalPassiveMode();
            FTPFile[] fs = ftpClient.listFiles();

            for (FTPFile ff : fs) {
                System.out.println(ff);
                String strName = ff.getName();
                if (ff.isDirectory()) {

                } else if (ff.isFile()) {

                } else if(ff.isSymbolicLink()){

                }else {

                }
            }

        } catch (UnknownHostException e) {
            String message = String.format("请确认ftp服务器地址是否正确，无法连接到地址为: [%s] 的ftp服务器", host);
            System.out.println(message);
        } catch (IllegalArgumentException e) {
            String message = String.format("请确认连接ftp服务器端口是否正确，错误的端口: [%s] ", port);
            System.out.println(message);

        } catch (Exception e) {
            String message = String.format("与ftp服务器建立连接失败 : [%s]",
                    "message:host =" + host + ",username = " + username + ",port =" + port);
            System.out.println(message);
        }

    }
}
