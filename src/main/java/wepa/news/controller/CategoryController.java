package wepa.news.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.news.domain.Category;
import wepa.news.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @PostMapping
    public String addCategory(@RequestParam String name,
            @RequestParam(required = false) boolean displayInNavigation) {
        Category category = new Category(name, displayInNavigation, new ArrayList());
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @DeleteMapping("{id}")
    public String deleteNews(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
