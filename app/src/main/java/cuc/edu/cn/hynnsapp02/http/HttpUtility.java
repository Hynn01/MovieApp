package cuc.edu.cn.hynnsapp02.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import android.R.string;

public class HttpUtility {

    private static int retryCount = 5;

    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_AUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int NOT_ACCEPTABLE = 406;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;

    private static final int NETWORK_DISABLED = 601;
    public static int downloadSize = 0;

    /**
     * Post请求
     *
     * @param postParams
     * @param connectionUrl
     * @param connectTimeout
     * @return
     * @throws Exception
     */
    public static String doPost(List<NameValuePair> postParams, String connectionUrl, int connectTimeout) throws Exception {

        int retriedCount = 0;
        // Response response = null;

        downloadSize = 0;
        HttpURLConnection connection = null;

        int responseCode = -1;

        for (retriedCount = 0; retriedCount < retryCount; retriedCount++) {

            OutputStream os = null;

            //1 创建HttpURLConnection
            connection = (HttpURLConnection) new URL(connectionUrl).openConnection();
            if (connectTimeout != 0) {
                connection.setConnectTimeout(connectTimeout);
            }

            //2 设置HttpURLConnection参数
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postParam = "";
            if (postParams != null) {
                //POST请求参数的设置
                postParam = encodeParameters(postParams);
            }
            byte[] bytes = postParam.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));

            //3 URLConnection建立连接
            os = connection.getOutputStream();

            //4 HttpURLConnection发送请求
            os.write(bytes);
            os.flush();
            os.close();

            responseCode = connection.getResponseCode();

            if (responseCode == OK ) {
                break;
            }
        }

        if(responseCode != OK){
            return null;
        }

        String responseAsString = null;

        BufferedReader bufferedReader;

        //5 HttpURLConneciton获取响应
        InputStream stream = connection.getInputStream();
        if (null == stream) {
            return null;
        }

        bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while (null != (line = bufferedReader.readLine())) {
            buffer.append(line).append("\n");
        }

        responseAsString = buffer.toString();
        stream.close();
        connection.disconnect();

        return responseAsString;
    }

    /**
     * Get请求
     *
     * @param getParams
     * @param connectionUrl
     * @param connectTimeout
     * @return
     */
    public static String doGet(List<NameValuePair> getParams, String connectionUrl, int connectTimeout) throws Exception {

        HttpURLConnection connection = null;

        int retriedCount = 0;

        int responseCode = -1;

        for (retriedCount = 0; retriedCount < retryCount; retriedCount++) {

            //1 创建HttpURLConnection
            if (getParams != null) {
                ////Get请求参数的设置
                connection = (HttpURLConnection) new URL(connectionUrl + "?" + encodeParameters(getParams))
                        .openConnection();

            } else {
                connection = (HttpURLConnection) new URL(connectionUrl).openConnection();
            }
            if (connectTimeout != 0) {
                connection.setConnectTimeout(connectTimeout);
            }

            responseCode = connection.getResponseCode();

            if (responseCode == OK ) {
                break;
            }
        }

        if(responseCode != OK){
            return null;
        }

        String responseAsString = null;

        BufferedReader bufferedReader;

        //5 HttpURLConneciton获取响应
        InputStream stream = connection.getInputStream();
        if (null == stream) {
            return null;
        }

        bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while (null != (line = bufferedReader.readLine())) {
            buffer.append(line).append("\n");
        }

        responseAsString = buffer.toString();
        stream.close();
        connection.disconnect();

        return responseAsString;
    }

    private static String encodeParameters(List<NameValuePair> postParams) throws Exception {
        StringBuffer buffer = new StringBuffer();
        if (postParams != null) {

            for (int i = 0; i < postParams.size(); i++) {
                if (i != 0) {
                    buffer.append("&");
                }
                buffer.append(URLEncoder.encode(postParams.get(i).getName(), "UTF-8")).append("=")
                        .append(URLEncoder.encode(postParams.get(i).getValue().toString(), "UTF-8"));
            }
        }
        return buffer.toString();
    }
}
