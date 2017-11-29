package wepa.news.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wepa.news.domain.NewsItem;
import wepa.news.service.AuthorService;
import wepa.news.service.CategoryService;
import wepa.news.service.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;

    @GetMapping
    public String newsControlPanel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("newsItems", newsService.getAllNews());
        return "news";
    }

    @PostMapping
    public String addNews(@RequestParam String title,
            @RequestParam String leadText,
            @RequestParam String content,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate publishingDate,
            @RequestParam MultipartFile image,
            @RequestParam List<Long> authorIds,
            @RequestParam List<Long> categoryIds) throws IOException {

        NewsItem newsItem = NewsItem.builder()
                .title(title)
                .leadText(leadText)
                .content(content)
                .datePublished(publishingDate)
                .imageData(image.getBytes())
                .authors(authorService.getAuthorsByIds(authorIds))
                .categories(categoryService.getCategoriesByIds(categoryIds))
                .build();

        newsService.addNews(newsItem);
        return "redirect:/news";
    }

    @DeleteMapping("{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "redirect:/news";
    }
}
