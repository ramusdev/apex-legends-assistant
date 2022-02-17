package com.rb.apexassistant.stats.client;

import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.model.PlayerStats;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiClientAbstract {
    public static final String ACCEPT_HEADER = "application/vnd.api+json";
    public static final String USER_AGENT_HEADER = "Googlebot";
    public CloseableHttpClient closeableHttpClient;
    public String apiKey;
    public URIBuilder uriBuilder;

    protected ApiClientAbstract(String apiKey) {

        this.apiKey = apiKey;

        closeableHttpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();
    }

    protected Map<String, String> makeGetCall(String url, ArrayList<NameValuePair> parameters) {
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        parameters.add(new BasicNameValuePair("auth", apiKey));
        uriBuilder.addParameters(parameters);

        return makeGet();
    }

    private Map<String, String> makeGet() {

        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        httpGet.addHeader(HttpHeaders.USER_AGENT, USER_AGENT_HEADER);

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        // int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        int statusCode = closeableHttpResponse.getCode();

        String responseString = null;
        try {
            responseString = EntityUtils.toString(httpEntity);
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }

        Map<String, String> response = new HashMap<>();
        response.put("code", String.valueOf(statusCode));
        response.put("response", responseString);

        return response;
    }

    public abstract PlayerStatsEntity getPlayerInfo(String playerName);
}
