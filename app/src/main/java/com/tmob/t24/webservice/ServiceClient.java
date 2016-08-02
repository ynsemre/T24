package com.tmob.t24.webservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ServiceClient {

    public static String sendHttpPost(String path, ArrayList<ParcelableNameValuePair> params) {
        try {
            HttpPost httpPostRequest = new HttpPost(path);

            if (params != null)
                httpPostRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            return connectService(httpPostRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendHttpPost(String path, String key, String value) {
        try {
            HttpPost httpPostRequest = new HttpPost(path);
            httpPostRequest.addHeader(key, value);
            return connectService(httpPostRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendHttpPost(String path, String key, String value, String json) {
        try {
            HttpPost httpPostRequest = new HttpPost(path);
            httpPostRequest.addHeader(key, value);
            httpPostRequest.addHeader("Content-Type", "Application/json");
            httpPostRequest.setEntity(new StringEntity(json, HTTP.UTF_8));
            return connectService(httpPostRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendHttpPost(String path, ArrayList<ParcelableNameValuePair> params, String json) {
        try {
            String combinedParams = "";
            if (!params.isEmpty()) {
                combinedParams += "?";
                for (NameValuePair p : params) {
                    String paramString = p.getName() + "="
                            + URLEncoder.encode(p.getValue(), "utf-8");
                    if (combinedParams.length() > 1) {
                        combinedParams += "&" + paramString;
                    } else {
                        combinedParams += paramString;
                    }
                }
            }

            HttpPost httpPostRequest = new HttpPost(path + combinedParams);
            httpPostRequest.addHeader("Content-Type", "Application/json");
            httpPostRequest.setEntity(new StringEntity(json, HTTP.UTF_8));
            return connectService(httpPostRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendHttpPost(String path, String key, String value, ArrayList<ParcelableNameValuePair> params, String json) {
        try {
            String combinedParams = "";
            if (!params.isEmpty()) {
                combinedParams += "?";
                for (NameValuePair p : params) {
                    String paramString = p.getName() + "="
                            + URLEncoder.encode(p.getValue(), "utf-8");
                    if (combinedParams.length() > 1) {
                        combinedParams += "&" + paramString;
                    } else {
                        combinedParams += paramString;
                    }
                }
            }

            //combinedParams = URLDecoder.decode(combinedParams);
            //combinedParams = combinedParams.replace(" ", "%20");

            HttpPost httpPostRequest = new HttpPost(path + combinedParams);
            httpPostRequest.addHeader(key, value);
            httpPostRequest.addHeader("Content-Type", "Application/json");
            httpPostRequest.setEntity(new StringEntity(json, HTTP.UTF_8));
            return connectService(httpPostRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String SendHttpGet(String path, ArrayList<ParcelableNameValuePair> params) {

        try {
            String combinedParams = "";
            if (!params.isEmpty()) {
                combinedParams += "?";
                for (NameValuePair p : params) {
                    String paramString = p.getName() + "="
                            + URLEncoder.encode(p.getValue(), "utf-8");
                    if (combinedParams.length() > 1) {
                        combinedParams += "&" + paramString;
                    } else {
                        combinedParams += paramString;
                    }
                }
            }

            HttpGet httpGetRequest = new HttpGet(path + combinedParams);
            Log.i("Path: ", path + combinedParams);
            return connectService(httpGetRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String connectService(HttpUriRequest request) {
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 60000);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpResponse response = (HttpResponse) httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String resultString = convertStreamToString(instream);
                instream.close();
                return resultString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
