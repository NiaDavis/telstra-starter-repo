package com.telstra.simactivation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SimActivationController {

    private final RestTemplate restTemplate;

    public SimActivationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody Map<String, String> request) {
        String iccid = request.get("iccid");

        Map<String, String> activationRequest = new HashMap<>();
        activationRequest.put("iccid", iccid);

        ResponseEntity<Map> response = restTemplate.postForEntity(
            "http://localhost:8444/actuate",
            activationRequest,
            Map.class
        );

        boolean success = (boolean) response.getBody().get("success");

        return ResponseEntity.ok(success ? "Activation successful" : "Activation failed");
    }
}
