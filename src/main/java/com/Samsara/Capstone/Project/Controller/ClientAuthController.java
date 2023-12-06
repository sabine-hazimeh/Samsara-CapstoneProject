package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Security.UserInfoDetails;
import com.Samsara.Capstone.Project.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/client")
@Controller
public class ClientAuthController {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ClientAuthController(ClientService clientService, PasswordEncoder passwordEncoder)
    {
        this.clientService = clientService;
        this.passwordEncoder =  passwordEncoder;
    }
    @GetMapping(value = "/display-details")
    public String getClientDetail(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        if (client != null) {
            model.addAttribute("client",clientService.getClientById(client.getId()));
        } else {
            model.addAttribute("errorMessage", "Client not found");
        }
        return "UserProfile";
    }
    @GetMapping(value="/edit-details")
    public String EditClientDetail(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        Client client = clientService.getClientById(userInfoDetails.getUserId());
        model.addAttribute("client", clientService.getClientById(client.getId()));
        return "EditUserProfile";
    }
//    @PostMapping(value = "/update-details")
//    public String updatePost(@RequestParam("files") MultipartFile files, Authentication authentication) throws IOException {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
//        Client client = clientService.getClientById(userInfoDetails.getUserId());
//
//        clientService.updateClient(client, files);
//        return "redirect:/client/display-details";
//    }
@PostMapping(value = "/update-details")
public String updatePost(@ModelAttribute("client") Client client, @RequestParam("files") MultipartFile files, Authentication authentication) throws IOException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
    Client existingClient = clientService.getClientById(userInfoDetails.getUserId());

    existingClient.setUserName(client.getUserName());
    existingClient.setPhoneNumber(client.getPhoneNumber());
    existingClient.setEmail(client.getEmail());
    existingClient.setPassword(passwordEncoder.encode(client.getPassword()));
//    existingClient.setPassword(client.getPassword());

    clientService.updateClient(existingClient, files);

    return "redirect:/client/display-details";
}
}
