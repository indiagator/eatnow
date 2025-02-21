package com.secor.eatnow;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    UserCredentialRepository userCredentialRepository;


    @PostMapping("/signup")
    public String signUp(@RequestParam("username") String username, @RequestParam("password") String password)
    {

        UserCredential userCredential = new UserCredential();
        userCredential.username = username;
        userCredential.password = password;

        userCredentialRepository.save(userCredential);

        return "redirect:/login.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model)
    {
        if(!userCredentialRepository.existsById(username))
        {
            return "redirect:/login.html";
        }

        UserCredential userCredential = userCredentialRepository.findById(username).get();

        if(userCredential.password.equals(password))
        {
            model.addAttribute("username", username);
            return "dashboard";
        }

        return "redirect:/login.html";
    }

}
