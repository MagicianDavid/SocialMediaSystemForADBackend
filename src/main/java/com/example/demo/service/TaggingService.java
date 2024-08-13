package com.example.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaggingService {

	 public String spamCheck(String text) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("text", text);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("prediction")) {
                return (String) responseBody.get("prediction");
            }
        }
        return "No tags";
    }
	 
	 
	 public String HugTagsForText(String text) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = "http://127.0.0.1:5000/mlbcheck";

	     
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");

	        Map<String, String> request = new HashMap<>();
	        request.put("text", text);

	        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	        		url, HttpMethod.POST, entity, String.class);

	        String responseBody = response.getBody();
         if (responseBody != null && !responseBody.isEmpty()) {
             return responseBody.toLowerCase();
         } else {
             return null;
         }
	}
	 
	
	
	 public String getTagsForText(String text) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = "http://127.0.0.1:5000/mlbpredict";

	     
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");

	        Map<String, String> request = new HashMap<>();
	        request.put("text", text);

	        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	        		url, HttpMethod.POST, entity, String.class);

	        String responseBody = response.getBody();
            if (responseBody != null && !responseBody.isEmpty()) {
                return responseBody.toLowerCase();
            } else {
                return null;
            }
	    }
	 
}