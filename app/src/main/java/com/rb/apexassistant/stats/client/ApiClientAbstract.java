package com.rb.apexassistant.stats.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.stats.model.ErrorCode;
import com.rb.apexassistant.stats.settings.ApiConfiguration;

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
import java.util.List;
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

    public String getResponse(String playerName) {
        String url = ApiConfiguration.API_ENDPOINT;

        ArrayList<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("version", "5"));
        parameters.add(new BasicNameValuePair("platform", "PC"));
        parameters.add(new BasicNameValuePair("player", playerName));

        Map<String, String> response = this.makeGetCall(url, parameters);

        GsonBuilder gsonBuilderError = new GsonBuilder();
        Gson gsonError = gsonBuilderError.create();
        ErrorCode errorCode = gsonError.fromJson(response.get("response"), ErrorCode.class);

        if (! response.get("code").equals("200")) {
            return null;
        }

        if (errorCode.getError() != null) {
            return null;
        }

        return response.get("response");
    }

    public abstract PlayerEntity getPlayerInfo(String json, String playerName);
    public abstract List<LegendEntity> getPlayerLegends(String json, String playerName);
}
