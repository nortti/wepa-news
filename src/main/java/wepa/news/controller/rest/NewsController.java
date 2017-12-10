package wepa.news.controller.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wepa.news.domain.News;
import wepa.news.domain.NewsWriteDto;
import wepa.news.service.NewsService;

@RestController
@RequestMapping("/rest/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<News> findAll() {
        return newsService.findAll();
    }

    @GetMapping("/newest/{count}")
    public List<News> findNewest(@PathVariable Integer count) {
        return newsService.findNewest(count);
    }

    @GetMapping("/most-viewed/{count}")
    public List<News> findMostViewed(@PathVariable Integer count) {
        return newsService.findMostViewed(count);
    }

    @GetMapping("{categoryName}/newest/{count}")
    public List<News> findNewestInCategory(
            @PathVariable String categoryName,
            @PathVariable Integer count) {
        return newsService.findNewestInCategory(categoryName, count);
    }

    @GetMapping("{categoryName}/most-viewed/{count}")
    public List<News> findMostViewedInCategory(
            @PathVariable String categoryName,
            @PathVariable Integer count) {
        return newsService.findMostViewedInCategory(categoryName, count);
    }

    @GetMapping("{id}")
    public News getOne(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @GetMapping("{id}/read")
    public News read(@PathVariable Long id) {
        return newsService.read(id);
    }

    // No put mapping, FormData does not work with it
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid NewsWriteDto newsWriteDto) {
        newsService.save(newsWriteDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @GetMapping("{id}/image")
    public byte[] getImage(@PathVariable Long id) {
        return newsService.findById(id).getImageData();
    }
}
