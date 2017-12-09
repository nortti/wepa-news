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
import wepa.news.domain.NewsItem;
import wepa.news.domain.NewsItemWriteDTO;
import wepa.news.service.NewsItemService;

@RestController
@RequestMapping("/rest/news")
public class NewsController {

    @Autowired
    private NewsItemService newsItemService;

    @GetMapping
    public List<NewsItem> findAll() {
        return newsItemService.findAll();
    }

    @GetMapping("/newest/{count}")
    public List<NewsItem> findNewest(@PathVariable Integer count) {
        return newsItemService.findNewest(count);
    }

    @GetMapping("/most-viewed/{count}")
    public List<NewsItem> findMostViewed(@PathVariable Integer count) {
        return newsItemService.findMostViewed(count);
    }

    @GetMapping("{categoryName}/newest/{count}")
    public List<NewsItem> findNewestInCategory(
            @PathVariable String categoryName,
            @PathVariable Integer count) {
        return newsItemService.findNewestInCategory(categoryName, count);
    }

    @GetMapping("{categoryName}/most-viewed/{count}")
    public List<NewsItem> findMostViewedInCategory(
            @PathVariable String categoryName,
            @PathVariable Integer count) {
        return newsItemService.findMostViewedInCategory(categoryName, count);
    }

    @GetMapping("{id}")
    public NewsItem getOne(@PathVariable Long id) {
        return newsItemService.getOne(id);
    }

    @GetMapping("{id}/read")
    public NewsItem read(@PathVariable Long id) {
        return newsItemService.read(id);
    }

    // No put mapping, FormData does not work with it
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid NewsItemWriteDTO newsItemDTO) {
        newsItemService.save(newsItemDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        newsItemService.deleteById(id);
    }

    @GetMapping("{id}/image")
    public byte[] getImage(@PathVariable Long id) {
        return newsItemService.getOne(id).getImageData();
    }
}
