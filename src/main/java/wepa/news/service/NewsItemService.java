package wepa.news.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
import wepa.news.domain.NewsItem;
import wepa.news.domain.NewsItemWriteDTO;
import wepa.news.repository.AuthorRepository;
import wepa.news.repository.CategoryRepository;
import wepa.news.repository.NewsItemRepository;

@Service
public class NewsItemService {

    @Autowired
    NewsItemRepository newsItemRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<NewsItem> findAll() {
        return newsItemRepository.findAll();
    }

    public void deleteById(Long id) {
        newsItemRepository.deleteById(id);
    }

    public void createOrUpdate(NewsItemWriteDTO newsItemDTO) throws IOException {
        NewsItem newsItem = new NewsItem();
        if (newsItemDTO.getId() != null) {
            newsItem = newsItemRepository.getOne(newsItemDTO.getId());
        }

        List<Author> authors = (List<Author>) objectsFromIds(newsItemDTO.getAuthorIds(), authorRepository);
        List<Category> categories = (List<Category>) objectsFromIds(newsItemDTO.getCategoryIds(), categoryRepository);

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

    public NewsItem findById(Long id) {
        return newsItemRepository.getOne(id);
    }

    private List<?> objectsFromIds(List<Long> ids, JpaRepository repository) {
        return ids.stream().map(id -> repository.findById(id).get()).collect(Collectors.toList());
    }
}