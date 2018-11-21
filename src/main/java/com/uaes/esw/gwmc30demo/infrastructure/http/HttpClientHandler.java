package com.uaes.esw.gwmc30demo.infrastructure.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.EMPTY;

public class HttpClientHandler {

    /**
     * get 获取 rest 资源
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String do_get(String url)  {
        String body = "{}";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return body;
    }

    public static String httpGetRequest(String url){
        System.out.println("url="+url);
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> params){
        try{
            System.out.println("url="+url);
            URIBuilder ub = new URIBuilder();
            ub.setPath(url);

            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);

            HttpGet httpGet = new HttpGet(ub.build());
            System.out.println(httpGet.toString());
            return getResult(httpGet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params){
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param: params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        }

        return pairs;
    }

    /**
     * 处理Http请求
     * @param request
     * @return
     */
    public static String getResult(HttpRequestBase request){

        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
        try{
            CloseableHttpResponse response = httpClient.execute(request);
            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //获取消息实体
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                //long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                //httpClient.close();
                return result;
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{

        }

        return EMPTY;
    }

}
