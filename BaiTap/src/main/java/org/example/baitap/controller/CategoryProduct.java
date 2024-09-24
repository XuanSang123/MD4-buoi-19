package org.example.baitap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category-product")
public class CategoryProduct {

    @GetMapping
    public String show(){
        return "category-product";
    }
}
