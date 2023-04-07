package cn.golaxy.util.http.manager;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class HttpClientManager {
    private PoolingHttpClientConnectionManager poolConnManager;
	private static final int maxTotalPool = 200;
	private static final int maxConPerRoute = 20;
	private static final int socketTimeout = 50000;
	private static final int connectionRequestTimeout = 50000;
	private static final int connectTimeout = 50000;
	
	
	
	public PoolingHttpClientConnectionManager getPoolConnManager() {
		return poolConnManager;
	}

	public void initHttpclientPool() {
		try {  
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,  
					new TrustSelfSignedStrategy())  
					.build();  
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
					sslcontext,hostnameVerifier);  
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
					.register("http", PlainConnectionSocketFactory.getSocketFactory())  
					.register("https", sslsf)  
					.build();  
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
			// Increase max total connection to 200  
			poolConnManager.setMaxTotal(maxTotalPool);  
			// Increase default max connection per route to 20  
			poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);  
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();  
			poolConnManager.setDefaultSocketConfig(socketConfig);  
		} catch (Exception e) {  

		} 
	}
	
	public  CloseableHttpClient getConnection(){  
	    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
	            .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
	    CloseableHttpClient httpClient = HttpClients.custom()  
	                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();  
	    if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){  

	        System.out.println("now client pool "+poolConnManager.getTotalStats().toString());  
	    }  
	    return httpClient;  
	} 
	
	public  CloseableHttpClient getConnection(String ip,int port){  
	    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
	            .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
	    CloseableHttpClient httpClient = HttpClients.custom()  
	                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();  
	    if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){  

	        System.out.println("now client pool "+poolConnManager.getTotalStats().toString());  
	    }  
	    return httpClient;  
	} 
	
	
	public  void resleaseConnection(){
		if(poolConnManager!=null){
			try {
				poolConnManager.shutdown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

