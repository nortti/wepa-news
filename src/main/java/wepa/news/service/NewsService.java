package wepa.news.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.news.domain.NewsItem;
import wepa.news.repository.NewsItemRepository;

@Service
public class NewsService {

    @Autowired
    private NewsItemRepository newsItemRepository;

    public List<NewsItem> getAllNews() {
        List<NewsItem> authors = newsItemRepository.findAll();
        return authors;
    }

    public void addNews(NewsItem newsItem) {
        newsItemRepository.save(newsItem);
    }

    public Optional<NewsItem> getNewsById(Long id) {
        return newsItemRepository.findById(id);
    }

    public void deleteNews(Long id) {
        newsItemRepository.deleteById(id);
    }
}
