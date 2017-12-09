package wepa.news.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<NewsItem> findNewest(Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "datePublished");
        return newsItemRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<NewsItem> findMostViewed(Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "views");
        return newsItemRepository.findAll(pageable).getContent();
    }

    public List<NewsItem> findNewestInCategory(String categoryName, Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "datePublished");
        return newsItemRepository.findByCategories_name(pageable, categoryName).getContent();
    }

    public List<NewsItem> findMostViewedInCategory(String categoryName, Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "views");
        return newsItemRepository.findByCategories_name(pageable, categoryName).getContent();
    }

    @Transactional(readOnly = true)
    public NewsItem getOne(Long id) {
        return newsItemRepository.getOne(id);
    }

    @Transactional
    public NewsItem read(Long id) {
        NewsItem newsItem = newsItemRepository.getOne(id);
        newsItem.setViews(newsItem.getViews() + 1);
        newsItemRepository.save(newsItem);
        return newsItem;
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
        newsItem.setDatePublished(LocalDateTime.now());
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
