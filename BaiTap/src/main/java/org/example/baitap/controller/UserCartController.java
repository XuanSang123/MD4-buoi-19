package org.example.baitap.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-cart")
public class UserCartController {
    @GetMapping
    public String show(){
        return "user-cart";
    }
}
