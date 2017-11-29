package wepa.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.news.domain.Author;
import wepa.news.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }

    @PostMapping
    public String addAuthor(@RequestParam String name) {
        Author author = new Author(name);
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @DeleteMapping("{id}")
    public String deleteNews(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
