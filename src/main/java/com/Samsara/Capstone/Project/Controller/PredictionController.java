package com.Samsara.Capstone.Project.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/predict")
@RestController
public class PredictionController {

    @PostMapping("/predictApartmentPrice")
    public String predictApartmentPrice(@RequestBody String features) {
        String flaskApiUrl = "http://localhost:5000/predict";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(flaskApiUrl, "{\"input_data\": " + features + "}", String.class);
        return result;
    }
}
