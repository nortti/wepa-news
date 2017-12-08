package wepa.news.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
import wepa.news.domain.NewsImage;
import wepa.news.domain.NewsItem;
import wepa.news.domain.NewsItemWriteDTO;
import wepa.news.repository.NewsImageRepository;
import wepa.news.repository.NewsItemRepository;

@Service
public class NewsItemService {

    @Autowired
    NewsItemRepository newsItemRepository;

    @Autowired
    NewsImageRepository newsImageRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

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

        List<Author> authors = authorService.findAllById(newsItemDTO.getAuthorIds());
        List<Category> categories = categoryService.findAllById(newsItemDTO.getCategoryIds());

        newsItem.setTitle(newsItemDTO.getTitle());
        newsItem.setLeadText(newsItemDTO.getLeadText());
        newsItem.setContent(newsItemDTO.getContent());
        newsItem.setDatePublished(newsItemDTO.getDatePublished());
        newsItem.setAuthors(authors);
        newsItem.setCategories(categories);
        newsItem.setViews(0);

        NewsImage image = newsItemDTO.getNewsImage();

        if (image.getImageData().length > 0) {
            newsImageRepository.save(image);
            newsItem.setImage(image);
        }

        newsItemRepository.save(newsItem);
    }

    @Transactional
    public NewsItem findById(Long id) {
        return newsItemRepository.getOne(id);
    }
}
