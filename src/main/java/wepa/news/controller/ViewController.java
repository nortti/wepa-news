package wepa.news.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final List<String> links = Arrays.asList("News", "Categories", "Authors");

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("singular", "author");
        model.addAttribute("plural", "authors");
        model.addAttribute("tableHeaders", Arrays.asList("Name"));
        model.addAttribute("links", links);
        model.addAttribute("scripts", "author-scripts");
        return "layout";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("singular", "category");
        model.addAttribute("plural", "categories");
        model.addAttribute("tableHeaders", Arrays.asList("Name", "In navigation"));
        model.addAttribute("links", links);
        model.addAttribute("scripts", "category-scripts");
        return "layout";
    }

    @GetMapping({"/", "/news"})
    public String news(Model model) {
        model.addAttribute("singular", "news");
        model.addAttribute("plural", "news");
        model.addAttribute("tableHeaders", Arrays.asList("Name", "Authors", "Categories"));
        model.addAttribute("links", links);
        model.addAttribute("scripts", "news-scripts");
        return "layout";
    }
}
