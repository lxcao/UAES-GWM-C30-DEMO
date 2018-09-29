package com.uaes.esw.gwmc30demo.infrastructure.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HttpClientFactory {
    static PoolingHttpClientConnectionManager cm;
    static SSLContext sslContext;

    public static void init(){

        //采用绕过验证的方式处理https请求
        try{
            sslContext = createIgnoreVerifySSL();
        }catch(Exception e){
            e.printStackTrace();
        }

        // 设置协议http和https对应的处理socket链接工厂的对象，针对GPSspg网址，需要把主机名验证关闭NoopHostnameVerified
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();



        if(cm == null){
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(50);//整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);//每路由最大连接数，默认值是2

            //创建http request的配置信息
            //RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(NewsConstant.REQUEST_TIMEOUT)
            //                              .setSocketTimeout(NewsConstant.REQUEST_SOCKET_TIME).build();
        }
    }
    public static CloseableHttpClient getHttpClient(){
            init();
            CloseableHttpClient closedHttpClient = HttpClients.custom()
                    .setConnectionManager(cm).build();
            return closedHttpClient;
        /*
        // 设置连接的超时时间5s
        closedHttpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(connectionTimeout);
        // 设置读取数据的超时时间8s
        closedHttpClient.getHttpConnectionManager().getParams()
                .setSoTimeout(soTimeout);
        //初始化httpclient客户端
        closedHttpClient.setConnectionManager(httpClientConnectionManager)
                        .setDefaultRequestConfig(requestConfig).setUserAgent(NewsConstant.USER_AGENT).setRedirectStrategy(redirectStrategy).build();
         */
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

}
