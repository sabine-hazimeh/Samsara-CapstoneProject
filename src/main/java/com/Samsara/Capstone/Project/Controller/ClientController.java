package com.Samsara.Capstone.Project.Controller;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.Post;
import com.Samsara.Capstone.Project.Model.PostPicture;
import com.Samsara.Capstone.Project.Security.UserInfoDetails;
import com.Samsara.Capstone.Project.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequestMapping("/Register")
@Controller
public class ClientController {
    private final ClientService clientService;
    @Autowired
    private ClientController(ClientService clientService)
    {
        this.clientService=clientService;
    }
    @GetMapping(value = "/display-Register")
    public String getRegisterPage(Model model)  {
        model.addAttribute("Client",new Client());
        return "Register";
    }
    @PostMapping(value = "/create")
    public String createUser(@ModelAttribute Client client, Model model, @RequestParam("files") MultipartFile files) throws IOException {
        if (clientService.userNameExists(client.getUserName())) {
            model.addAttribute("errorMessage", "Username is already in use");
            return "Register";
        }
        if (clientService.userEmailExists(client.getEmail())) {
            model.addAttribute("errorMessage", "Email is already in use");
            return "Register";
        }
        if (clientService.userPhoneNumberExists(client.getPhoneNumber())) {
            model.addAttribute("errorMessage", "Phone number is already in use");
            return "Register";
        }
        clientService.createClient(client, files);
        return "HomePage";
    }


}
