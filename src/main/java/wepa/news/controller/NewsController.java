package wepa.news.controller;

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
@RequestMapping("/rest/news/")
public class NewsController {

    @Autowired
    private NewsItemService newsItemService;

    @GetMapping
    public List<NewsItem> getAll() {
        return newsItemService.findAll();
    }

    @GetMapping("{id}")
    public NewsItem getOne(@PathVariable Long id) {
        return newsItemService.getOne(id);
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
