package wepa.news.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wepa.news.service.CategoryService;

@Controller
public class PublicController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.findAllInNavigation());
        model.addAttribute("currentCategory", ""); // = all categories
        return "index";
    }

    @GetMapping("{categoryName}")
    public String indexByCategory(@PathVariable String categoryName, Model model) {
        model.addAttribute("categories", categoryService.findAllInNavigation());
        model.addAttribute("currentCategory", categoryName);
        return "index";
    }
}
