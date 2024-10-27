package com.activate.ActivateMSV1.service;

import com.activate.ActivateMSV1.infra.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class UserService {

    private static String apiUrl = "http://localhost:8082/api/activate/user";

    public static boolean registerUser(UserDTO user) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        String url = apiUrl;
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(new StringEntity(jsonString));

        HttpResponse response = httpClient.execute(postRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            throw new Exception(responseBody);
        }
    }

    public static UserDTO getUser(Long id) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + id;
        HttpGet getRequest = new HttpGet(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();


        if (statusCode == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            UserDTO user = mapper.readValue(jsonResponse, UserDTO.class);
            httpClient.close();
            return user;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean updateProfile(UserDTO user) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        String url = apiUrl+"/"+user.getId()+"/profile";
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", "application/json");
        putRequest.setEntity(new StringEntity(jsonString));

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            throw new Exception(responseBody);
        }
    }

    public static boolean addInterest(Long userId, InterestRequestDTO interest) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(interest);
        String url = apiUrl+"/"+userId+"/interests/add";
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", "application/json");
        putRequest.setEntity(new StringEntity(jsonString));

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            throw new Exception(responseBody);
        }
    }

    public static boolean removeInterest(Long userId, InterestRequestDTO interest) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(interest);
        String url = apiUrl+"/"+userId+"/interests/remove";
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", "application/json");
        putRequest.setEntity(new StringEntity(jsonString));

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            throw new Exception(responseBody);
        }
    }

    public static boolean updateLocation(Long userId, LocationDTO location) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(location);
        String url = apiUrl+"/"+userId+"/location";
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", "application/json");
        putRequest.setEntity(new StringEntity(jsonString));

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            throw new Exception(responseBody);
        }

    }
}


