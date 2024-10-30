package com.activate.ActivateMSV1.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.activate.ActivateMSV1.infra.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public static EventDTO getEvent(Long eventId) throws Exception {

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

    public static ArrayList<EventDTO> getEventsByOrganizer(Long organizerId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(apiUrl);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            ArrayList<EventDTO> events = mapper.readValue(jsonResponse, new TypeReference<ArrayList<EventDTO>>() {});

            //Filtrar por id del organizador
            ArrayList<EventDTO> eventsByOrganizer = new ArrayList<>();
            for (EventDTO event : events) {
                if (event.getOrganizer().getId().equals(organizerId)) {
                    eventsByOrganizer.add(event);
                }
            }
            httpClient.close();
            return eventsByOrganizer;
        } else {
            System.out.println("No events found");
            return new ArrayList<>();
        }
    }

    public static boolean cancelEvent(Long organizerId, Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/organizer/" + organizerId + "/cancel/" + eventId;
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean updateEventType(Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/type";
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean updateMaxCapacity(Long eventId, int maxCapacity) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/maxCapacity?maxCapacity=" + maxCapacity;
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean  updateDate(Long eventId, LocalDateTime date) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/date?date=" + date;
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean startEvent (Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/start";
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static boolean finishEvent (Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/finish";
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
            return true;
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static void participate(Long userId, Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/participant/" + userId;
        HttpPut putRequest = new HttpPut(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(putRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static ArrayList<EventInfoDTO> getParticipantEvents(Long id) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/participant/" + id;
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
        } else if (statusCode == 404) {
            throw new Exception("No events found");
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static void quitEvent(Long userId, Long eventId) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/" + eventId + "/participant/" + userId;
        HttpDelete deleteRequest = new HttpDelete(url);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        HttpResponse response = httpClient.execute(deleteRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 204) {
            httpClient.close();
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }

    public static void sendEvaluation(Long userId, Long eventId, String comment, int score) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = apiUrl + "/"+ eventId + "/evaluation";
        HttpPost postRequest = new HttpPost(url);
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(new EvaluationRequest(comment, score, userId));

        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(new StringEntity(jsonInString));

        HttpResponse response = httpClient.execute(postRequest);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 201) {
            httpClient.close();
        } else {
            String responseBody = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(responseBody, ErrorResponse.class);
            httpClient.close();
            throw new Exception(errorResponse.getMessage());
        }
    }
}
