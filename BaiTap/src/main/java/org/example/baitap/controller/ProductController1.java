package org.example.baitap.controller;

import org.example.baitap.dao.product1.IProductDao1;
import org.example.baitap.models.Product1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController1 {

    @Autowired
    private IProductDao1 productDao1;

    @GetMapping
    public String list(Model model){
        List<Product1> product1 = productDao1.getAll();
        model.addAttribute("product1", product1);
        return "product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model){
        List<Product1> product1 = productDao1.getAll();
        model.addAttribute("product1", product1);
        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product1 product1){
        productDao1.save(product1);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        productDao1.delete(id);
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable int id, Model model){
        Product1 product1 = productDao1.findById(id);
        model.addAttribute("product1", product1);
        return "product-edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Product1 product1){
        product1.setId(id);
        productDao1.update(product1);
        return "redirect:/product";
    }
}
