package wepa.news.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
import wepa.news.domain.News;
import wepa.news.domain.NewsWriteDto;
import wepa.news.repository.AuthorRepository;
import wepa.news.repository.CategoryRepository;
import wepa.news.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<News> findNewest(Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "datePublished");
        return newsRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<News> findMostViewed(Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "views");
        return newsRepository.findAll(pageable).getContent();
    }

    public List<News> findNewestInCategory(String categoryName, Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "datePublished");
        return newsRepository.findByCategories_name(pageable, categoryName).getContent();
    }

    public List<News> findMostViewedInCategory(String categoryName, Integer count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "views");
        return newsRepository.findByCategories_name(pageable, categoryName).getContent();
    }

    @Transactional(readOnly = true)
    public News findById(Long id) {
        Optional<News> news =  newsRepository.findById(id);
        return news.isPresent() ? news.get() : null;
    }

    @Transactional
    public News read(Long id) {
        News news = newsRepository.getOne(id);
        news.setViews(news.getViews() + 1);
        newsRepository.save(news);
        return news;
    }

    @Transactional
    public News save(NewsWriteDto newsWriteDto) {
        News news = new News();
        if (newsWriteDto.getId() != null) {
            news = newsRepository.getOne(newsWriteDto.getId());
        }

        List<Author> authors = authorRepository.findAllById(newsWriteDto.getAuthorIds());
        List<Category> categories = categoryRepository.findAllById(newsWriteDto.getCategoryIds());

        news.setTitle(newsWriteDto.getTitle());
        news.setLeadText(newsWriteDto.getLeadText());
        news.setContent(newsWriteDto.getContent());
        news.setDatePublished(LocalDateTime.now());
        news.setAuthors(authors);
        news.setCategories(categories);
        news.setViews(0);

        if (newsWriteDto.getImageData().length > 0) {
            news.setImageData(newsWriteDto.getImageData());
        }

        return newsRepository.save(news);
    }

    @Transactional
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
