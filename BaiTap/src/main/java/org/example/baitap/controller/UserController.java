package org.example.baitap.controller;
import org.example.baitap.dao.user.IUserDao;
import org.example.baitap.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserDao userDao;

    @GetMapping("/login")
    public String show(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user){
        userDao.register(user);
        return "redirect:/product";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userDao.login(email, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/product";
        } else {
            return "login";
        }
    }

}
