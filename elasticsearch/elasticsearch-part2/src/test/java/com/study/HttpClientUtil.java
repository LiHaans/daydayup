package com.golaxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpClient工具类
 */
public abstract class HttpClientUtil {

    public static String doPut(String url, Map<String, String> map, Charset encoding) {
        StringBuffer sb = new StringBuffer("");
        CloseableHttpClient httpClient = null;
        HttpPut httpPut = null;
        BufferedReader reader = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPut = new HttpPut(url);
            // 设置参数
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                nameValuePairList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (nameValuePairList.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                httpPut.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPut);
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String doJsonPut(String url, String jsonStr){
        StringBuffer sb = new StringBuffer("");
        CloseableHttpClient httpClient = null;
        HttpPut httpPut = null;
        BufferedReader reader = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPut = new HttpPut(url);

            httpPut.addHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPut.setEntity(new StringEntity(jsonStr, "UTF-8"));

            HttpResponse response = httpClient.execute(httpPut);
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 发送post请求
     *
     * @param requestUrl
     * @param jsonStr
     * @return
     */
    public static String doJsonPost(String requestUrl, String jsonStr) throws Exception {
        StringBuffer sb = new StringBuffer("");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(requestUrl);
        post.addHeader(HTTP.CONTENT_TYPE, "application/json");
        post.setEntity(new StringEntity(jsonStr, "UTF-8"));
        HttpResponse response = client.execute(post);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String temp = null;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp);
        }
        reader.close();
        return sb.toString();
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> map) throws IOException {
        StringBuffer sb = new StringBuffer("");
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        BufferedReader reader = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                nameValuePairList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }

            if (nameValuePairList.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                httpPost.setEntity(entity);
            }

            HttpResponse response = httpClient.execute(httpPost);
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception ex) {
            return ex.getMessage();
        } finally {
            reader.close();
        }
        return sb.toString();
    }

    public static String httpPut(String urlPath, String data, String charSet, String[] header) {
        String result = null;
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(urlPath);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setConnectTimeout(2000000);// 设置连接主机超时（单位：毫秒）
            httpurlconnection.setReadTimeout(2000000);// 设置从主机读取数据超时（单位：毫秒）

            if (header != null) {
                for (int i = 0; i < header.length; i++) {
                    String[] content = header[i].split(":");
                    httpurlconnection.setRequestProperty(content[0], content[1]);
                }
            }

            httpurlconnection.setRequestMethod("PUT");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (data != null && !data.equals("")) {
                httpurlconnection.getOutputStream().write(data.getBytes("UTF-8"));
            }
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
            int code = httpurlconnection.getResponseCode();

            if (code == 200) {
                DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
                int len = in.available();
                byte[] by = new byte[len];
                in.readFully(by);
                if (charSet != null && !charSet.equals("")) {
                    result = new String(by, Charset.forName(charSet));
                } else {
                    result = new String(by);
                }
                in.close();
            } else {
                // log.error("请求地址：" + urlPath + "返回状态异常，异常号为：" + code);
            }
        } catch (Exception e) {
            // log.error("访问url地址：" + urlPath + "发生异常", e);
        } finally {
            url = null;
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return result;
    }

    /**
     * get 请求
     *
     * @param url
     * @param params
     */
    public static String doGet(String url, Map<String, String> params) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置连接超时时间
                .setConnectionRequestTimeout(50000) // 设置请求超时时间
                .setSocketTimeout(5000).setRedirectsEnabled(true)// 默认允许自动重定向
                .build();

        String strResult = "";

        HttpGet httpGet = null;

        if (params != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }

            if (list.size() > 0) {
                String getParams = EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
                httpGet = new HttpGet(url + "?" + getParams);
            } else {
                httpGet = new HttpGet(url);
            }
        } else {
            httpGet = new HttpGet(url);
        }

        httpGet.setConfig(requestConfig);

        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpResponse httpResponse = httpCilent.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
            // System.out.println(srtResult);

            return strResult;
        } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
            // ..........

        } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
            // .............
        }

        return strResult;
    }

    public static void getUploadInformation(String path, String obj) throws IOException {
        // 创建连接
        URL url = new URL(path);
        HttpURLConnection connection;
        StringBuffer sbuffer = null;
        try {
            // 添加 请求内容
            connection = (HttpURLConnection) url.openConnection();
            // 设置http连接属性
            connection.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            connection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
            connection.setRequestMethod("PUT"); // 可以根据需要 提交
            // GET、POST、DELETE、PUT等http提供的功能
            // connection.setUseCaches(false);//设置缓存，注意设置请求方法为post不能用缓存
            // connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Host", "*******"); // 设置请的服务器网址，域名，例如***.**.***.***
            connection.setRequestProperty("Content-Type", " application/json");// 设定
            // 请求格式
            // json，也可以设定xml格式的
            connection.setRequestProperty("Accept-Charset", "utf-8"); // 设置编码语言
            connection.setRequestProperty("X-Auth-Token", "token"); // 设置请求的token
            connection.setRequestProperty("Connection", "keep-alive"); // 设置连接的状态
            connection.setRequestProperty("Transfer-Encoding", "chunked");// 设置传输编码
            connection.setRequestProperty("Content-Length", obj.toString().getBytes().length + ""); // 设置文件请求的长度

            connection.setReadTimeout(10000);// 设置读取超时时间
            connection.setConnectTimeout(10000);// 设置连接超时时间
            connection.connect();
            OutputStream out = connection.getOutputStream();// 向对象输出流写出数据，这些数据将存到内存缓冲区中
            out.write(obj.toString().getBytes()); // out.write(new
            // String("测试数据").getBytes());
            // //刷新对象输出流，将任何字节都写入潜在的流中
            out.flush();
            // 关闭流对象,此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中
            out.close();
            // 读取响应
            if (connection.getResponseCode() == 200) {
                // 从服务器获得一个输入流
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());// 调用HttpURLConnection连接对象的getInputStream()函数,
                // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
                BufferedReader reader = new BufferedReader(inputStream);

                String lines;
                sbuffer = new StringBuffer("");

                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sbuffer.append(lines);
                }
                reader.close();
            } else {
                // Log.i(TAG,"请求失败"+connection.getResponseCode());
            }
            // 断开连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String httpPut(String url, Map<String, String> params) throws Exception {
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sBuilder = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(url);

            // httpPut.

            //Set<String> strSet = params.keySet();
            //for (String string : strSet) {
            //	httpPut.addHeader(string, params.get(string));
            //}

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                nameValuePairList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }

            if (nameValuePairList.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                httpPut.setEntity(entity);
            }

            HttpResponse httpResponse = httpClient.execute(httpPut);
            //连接成功
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                br = new BufferedReader(new InputStreamReader(is));
                String tempStr;
                sBuilder = new StringBuilder();
                while ((tempStr = br.readLine()) != null) {
                    sBuilder.append(tempStr);
                }
                br.close();
                is.close();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return sBuilder == null ? "" : sBuilder.toString();
    }

    public static String httpDelete(String url) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);

        HttpResponse httpResponse = httpClient.execute(httpDelete);

        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sBuilder = null;

        //连接成功
        if (200 == httpResponse.getStatusLine().getStatusCode()) {
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            br = new BufferedReader(new InputStreamReader(is));
            String tempStr;
            sBuilder = new StringBuilder();
            while ((tempStr = br.readLine()) != null) {
                sBuilder.append(tempStr);
            }
            br.close();
            is.close();
        }

        return sBuilder == null ? "" : sBuilder.toString();
    }





}
