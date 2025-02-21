package com.secor.eatnow;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class MainController {

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    RestroRepository restroRepository;

    @Autowired
    DishRepository dishRepository;


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

    @PostMapping("/setUserDetail")
    public String setUserDetail(@RequestParam("username") String username,
                                @RequestParam("usertype") String usertype,
                                @RequestParam("fullname") String fullname,
                                Model model)
    {
        Userdetail userdetail = new Userdetail();
        userdetail.username = username;
        userdetail.usertype = usertype;
        userdetail.fullname = fullname;

        model.addAttribute("username", username);

        userDetailRepository.save(userdetail);

        return "dashboard";
    }

    @GetMapping("/restrocreate")
    public String showRestroCreatePage(Model model) {
        // Add any necessary attributes to the model
        return "restrocreate";
    }

    @GetMapping("/managerestro")
    public String showManageRestroPage(Model model) {
        // Add any necessary attributes to the model
        return "restrodashboard";
    }

    @PostMapping("/createRestro")
    public String createRestro(@RequestParam("username") String username,
                               @RequestParam("name") String name,
                               Model model)
    {

        Restro restro = new Restro();
        restro.setRestro_id(String.valueOf(new Random().nextInt(1000)));
        restro.setName(name);
        restro.setOwner(username);

        restroRepository.save(restro);

        return "dashboard";
    }

    @PostMapping("/createDish")
    public String createDish(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("restro_id") String restro_id)
    {

        Dish dish = new Dish();
        dish.setDish_id(String.valueOf(new Random().nextInt(1000)));
        dish.setName(name);
        dish.setDescription(description);
        dish.setRestro_id(restro_id);

        dishRepository.save(dish);

        return "restrodashboard";

    }



}
