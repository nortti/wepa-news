package wepa.news.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
import wepa.news.domain.NewsItem;
import wepa.news.domain.NewsItemWriteDTO;
import wepa.news.repository.NewsItemRepository;

@Service
public class NewsItemService {

    @Autowired
    NewsItemRepository newsItemRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<NewsItem> findAll() {
        return newsItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NewsItem getOne(Long id) {
        return newsItemRepository.getOne(id);
    }

    @Transactional
    public void save(NewsItemWriteDTO newsItemDTO) {
        NewsItem newsItem = new NewsItem();
        if (newsItemDTO.getId() != null) {
            newsItem = newsItemRepository.getOne(newsItemDTO.getId());
        }

        List<Author> authors = authorService.findAllById(newsItemDTO.getAuthorIds());
        List<Category> categories = categoryService.findAllById(newsItemDTO.getCategoryIds());

        newsItem.setTitle(newsItemDTO.getTitle());
        newsItem.setLeadText(newsItemDTO.getLeadText());
        newsItem.setContent(newsItemDTO.getContent());
        newsItem.setDatePublished(newsItemDTO.getDatePublished());
        newsItem.setAuthors(authors);
        newsItem.setCategories(categories);
        newsItem.setViews(0);

        if (newsItemDTO.getImageData().length > 0) {
            newsItem.setImageData(newsItemDTO.getImageData());
        }

        newsItemRepository.save(newsItem);
    }

    @Transactional
    public void deleteById(Long id) {
        newsItemRepository.deleteById(id);
    }
}
