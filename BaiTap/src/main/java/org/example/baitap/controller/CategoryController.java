package org.example.baitap.controller;
import org.example.baitap.dao.category.ICategory;
import org.example.baitap.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategory categoryDao;

    @GetMapping
    public String list(Model model){
        List<Category> category1 = categoryDao.getAll();
        model.addAttribute("category", category1);
        return "category-list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "category-add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category){
        categoryDao.save(category);
        return "redirect:/category";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Category category = categoryDao.findById(id);
        model.addAttribute("category", category);
        return "category-edit";
    }

    @PostMapping("/{id}")
    public String editCategory(@PathVariable int id, @ModelAttribute Category category) {
        category.setId(id);
        categoryDao.update(category);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryDao.delete(id);
        return "redirect: /category";
    }
}
