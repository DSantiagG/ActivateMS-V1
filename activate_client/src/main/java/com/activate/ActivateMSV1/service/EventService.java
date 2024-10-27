package com.activate.ActivateMSV1.service;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.activate.ActivateMSV1.infra.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class EventService {

    private static String apiUrl = "http://localhost:8080/api/activate/event";

    public static boolean postEvent(EventInfoDTO event, Long organizerId) throws  Exception {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String url = apiUrl + "/organizer";  // URL del evento a crear

            HttpPost postRequest = new HttpPost(url);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            String jsonInString = mapper.writeValueAsString(EventRequest.fromEventInfoDTO(event, organizerId));

            postRequest.addHeader("content-type", "application/json");
            postRequest.setEntity(new StringEntity(jsonInString));

            HttpResponse response = httpClient.execute(postRequest);

            int statusCode = response.getStatusLine().getStatusCode();



            if (statusCode == 201) {
                httpClient.close();
                return true;
            } else {
                String responseBody = EntityUtils.toString(response.getEntity());
                ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
                httpClient.close();
                throw new Exception(errorResponse.getMessage());
            }
    }
    public static EventDTO getEvento(Long eventId) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId;
        HttpGet getRequest = new HttpGet(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();



        if (statusCode == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            EventDTO event = mapper.readValue(jsonResponse, EventDTO.class);
            httpClient.close();
            return event;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }
}
