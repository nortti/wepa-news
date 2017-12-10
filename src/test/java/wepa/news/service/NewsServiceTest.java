package wepa.news.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
import wepa.news.domain.News;
import wepa.news.domain.NewsWriteDto;
import wepa.news.repository.AuthorRepository;
import wepa.news.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Autowired
    NewsService newsService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private final Random random = new Random();

    @Test
    public void canCreateNews() {
        newsService.save(createNewsWriteDto()); // No exception: it works
    }

    @Test
    public void canCreateManyNewsAndFindThemAll() {
        int oldCount = newsService.findAll().size();
        int newCount = oldCount + 5;
        for (int i = oldCount; i < newCount; i++) {
            newsService.save(createNewsWriteDto());
        }
        assertEquals(newCount, newsService.findAll().size());
    }

    @Test(expected = TransactionSystemException.class)
    public void cantCreateNewsWithNoImageData() {
        NewsWriteDto newsWriteDto = createNewsWriteDto();
        newsWriteDto.setImageData(new byte[]{});
        newsService.save(newsWriteDto);
    }

    @Test
    public void canUpdateNews() {
        // Create and save new news
        News news = newsService.save(createNewsWriteDto());

        // Update news with new data except for content
        NewsWriteDto newsWriteDtoUpdate = createNewsWriteDto();
        newsWriteDtoUpdate.setId(news.getId());
        newsWriteDtoUpdate.setContent(news.getContent());
        News updatedNews = newsService.save(newsWriteDtoUpdate);

        // Updated news should have same content and different title as original
        assertEquals(news.getContent(), updatedNews.getContent());
        assertNotEquals(news.getTitle(), updatedNews.getTitle());
    }

    @Test
    public void readingNewsIncreasesViews() {
        News news = newsService.save(createNewsWriteDto());
        assertEquals(0, news.getViews());
        int read = 3;
        for (int i = 0; i < read; i++) {
            news = newsService.read(news.getId());
        }
        assertEquals(read, news.getViews());
    }

    @Test
    public void canDeleteNews() {
        News news = newsService.save(createNewsWriteDto());
        Long id = news.getId();
        News dbNews = newsService.findById(id);
        assertEquals(id, dbNews.getId());
        newsService.deleteById(id);
        assertEquals(null, newsService.findById(id));
    }

    @Test
    public void findingNewestWorksCorrectly() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            newsService.save(createNewsWriteDto());
        }

        List<News> newest = newsService.findNewest(count);
        List<News> allNews = newsService.findAll();

        Collections.sort(allNews, (a, b) -> b.getDateTimePublished().compareTo(a.getDateTimePublished()));
        for (int i = 0; i < count; i++) {
            assertEquals(allNews.get(i).getDateTimePublished(), newest.get(i).getDateTimePublished());
        }
    }

    @Test
    public void findingMostViewedWorksCorrectly() {
        // Add some news with random amouts of views
        int count = 10;
        for (int i = 0; i < count * 2; i++) {
            News news = newsService.save(createNewsWriteDto());
            for (int j = 0; j < random.nextInt(100); j++) {
                newsService.read(news.getId());
            }
        }

        // Assert that findMostViewed matches the result of manually sorting by
        // views (using views as the key, since there may be many correct permutations)
        List<News> allNews = newsService.findAll();
        Collections.sort(allNews, (a, b) -> b.getViews() - a.getViews());
        List<News> mostViewedNews = newsService.findMostViewed(count);

        for (int i = 0; i < count; i++) {
            assertEquals(allNews.get(i).getViews(), mostViewedNews.get(i).getViews());
        }
    }

    @Test
    public void findingNewestInCategoryWorksCorrectly() {
        // Add this category to 10 of 20 news
        Category category = createCategory();
        for (int i = 0; i < 20; i++) {
            NewsWriteDto newsWriteDto;
            if (i % 2 == 0) {
                newsWriteDto = createNewsWriteDtoWithCategory(category);
            } else {
                newsWriteDto = createNewsWriteDto();
            }
            newsService.save(newsWriteDto);
        }

        // Assert that function returns exactly 10 news in that category
        List<News> newest = newsService.findNewestInCategory(
                category.getName(),
                Integer.MAX_VALUE);
        assertEquals(10, newest.size());

        // Assert that sorting does not affect the order
        List<News> newestCpy = new ArrayList<>(newest);
        Collections.sort(newestCpy, (a, b)
                -> b.getDateTimePublished().compareTo(a.getDateTimePublished()));

        for (int i = 0; i < 10; i++) {
            assertEquals(newestCpy.get(i).getDateTimePublished(), newest.get(i).getDateTimePublished());
        }
    }

    @Test
    @Transactional // Fixes some lazy initialization issue
    public void findingMostViewedInCategoryWorksCorrectly() {
        // Add this category to 10 of 20 news with random amounts of views
        Category category = createCategory();
        for (int i = 0; i < 20; i++) {
            NewsWriteDto newsWriteDto;
            if (i % 2 == 0) {
                newsWriteDto = createNewsWriteDtoWithCategory(category);
            } else {
                newsWriteDto = createNewsWriteDto();
            }
            News news = newsService.save(newsWriteDto);
            for (int j = 0; j < random.nextInt(100); j++) {
                newsService.read(news.getId());
            }
        }

        int count = 10;

        List<News> mostViewedInCategory = newsService.findMostViewedInCategory(category.getName(), count);

        List<News> copy = new ArrayList<>(mostViewedInCategory);
        Collections.sort(copy, (a, b) -> b.getViews() - a.getViews());

        // Assert that they are sorted properly and all in the correct catgory
        for (int i = 0; i < count; i++) {
            assertEquals(copy.get(i).getViews(), mostViewedInCategory.get(i).getViews());
            assertTrue(mostViewedInCategory.get(i).getCategories().contains(category));
        }
    }

    private NewsWriteDto createNewsWriteDto() {
        Author author = createAuthor();
        Category category = createCategory();
        NewsWriteDto newsWriteDto = new NewsWriteDto();
        newsWriteDto.setTitle(UUID.randomUUID().toString());
        newsWriteDto.setLeadText(UUID.randomUUID().toString());
        newsWriteDto.setContent(UUID.randomUUID().toString());
        newsWriteDto.setImageData(UUID.randomUUID().toString().getBytes());
        newsWriteDto.setAuthorIds(Arrays.asList(author.getId()));
        newsWriteDto.setCategoryIds(Arrays.asList(category.getId()));
        return newsWriteDto;
    }

    private NewsWriteDto createNewsWriteDtoWithCategory(Category category) {
        NewsWriteDto newsWriteDto = createNewsWriteDto();
        newsWriteDto.setCategoryIds(Arrays.asList(category.getId()));
        return newsWriteDto;
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName(UUID.randomUUID().toString());
        authorRepository.save(author);
        return author;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setName(UUID.randomUUID().toString());
        categoryRepository.save(category);
        return category;
    }
}
