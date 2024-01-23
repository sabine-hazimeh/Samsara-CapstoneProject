package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    private final ClientService clientService;
    @Autowired
    private UserController(ClientService clientService)
    {
        this.clientService=clientService;
    }
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username) {
        boolean exists = clientService.userNameExists(username);
        return ResponseEntity.ok().body("{\"exists\": " + exists + "}");
    }
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        boolean exists = clientService.userEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhoneNumberAvailability(@RequestParam String phone) {
        boolean exists = clientService.userPhoneNumberExists(phone);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok().body(response);
    }

}
