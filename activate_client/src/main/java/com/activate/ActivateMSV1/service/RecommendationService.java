package com.activate.ActivateMSV1.service;

import com.activate.ActivateMSV1.infra.DTO.ErrorResponse;
import com.activate.ActivateMSV1.infra.DTO.EventInfoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class RecommendationService {
    private static String apiUrl = "http://localhost:8081/api/activate/recommendation";

    public static ArrayList<EventInfoDTO> getRecommendedEvents(Long userId)  throws Exception {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = apiUrl + "/" + userId;
            HttpGet getRequest = new HttpGet(url);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            HttpResponse response = httpClient.execute(getRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ArrayList<EventInfoDTO> events = mapper.readValue(jsonResponse, new TypeReference<ArrayList<EventInfoDTO>>() {});
                httpClient.close();
                return events;
            } else {
                String responseBody = EntityUtils.toString(response.getEntity());
                ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
                httpClient.close();
                throw new Exception(errorResponse.getMessage());
            }
    }
}
