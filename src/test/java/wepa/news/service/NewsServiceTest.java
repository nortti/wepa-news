package wepa.news.service;

import java.util.Arrays;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.news.domain.Author;
import wepa.news.domain.Category;
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

    @Test
    public void canSaveNews() {
        Author author = createAuthor();
        Category category = createCategory();

        NewsWriteDto newsWriteDto = new NewsWriteDto();
        newsWriteDto.setId(null);
        String title = UUID.randomUUID().toString();
        newsWriteDto.setTitle(title);
        newsWriteDto.setLeadText("leadText");
        newsWriteDto.setContent("content");
        newsWriteDto.setImageData(new byte[]{0});
        newsWriteDto.setAuthorIds(Arrays.asList(author.getId()));
        newsWriteDto.setCategoryIds(Arrays.asList(category.getId()));
        newsService.save(newsWriteDto); // No exception: it works
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
