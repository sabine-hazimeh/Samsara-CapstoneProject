package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.PredictionInput;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/predict")
@Controller
public class PredictionController {

    @PostMapping("/predictApartmentPrice")
    public String predictApartmentPrice(@ModelAttribute PredictionInput predictionInput, Model model) {
        String flaskApiUrl = "http://localhost:5000/predict";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictionInput> requestEntity = new HttpEntity<>(predictionInput, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, requestEntity, String.class);

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseEntity.getBody());

            // Get the value of the "result" field
            String predictedPrice = jsonResponse.getString("result");

            // Add the predictedPrice to the model
            model.addAttribute("predictedPrice", predictedPrice);
            model.addAttribute("predict", new PredictionInput());

            return "PredictionForm"; // Replace with the actual name of your HTML template
        } catch (HttpClientErrorException e) {
            // Handle exception or log error
            model.addAttribute("predictedPrice", "Error: " + e.getMessage());
            return "PredictionForm";
        }
    }


}