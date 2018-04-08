package com.uaes.esw.gwmc30demo.infrastructure.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {
    static PoolingHttpClientConnectionManager cm;
    static String EMPTY_STR = "";
    static String UTF_8 = "UTF-8";

    public static void init(){
        if(cm == null){
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);//整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);//每路由最大连接数，默认值是2

            //创建http request的配置信息
            //RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(NewsConstant.REQUEST_TIMEOUT)
            //                              .setSocketTimeout(NewsConstant.REQUEST_SOCKET_TIME).build();
        }
    }
    public static CloseableHttpClient getHttpClient(){
        init();
        CloseableHttpClient closedHttpClient = HttpClients.custom().setConnectionManager(cm).build();

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
        return closedHttpClient;


    }
}
