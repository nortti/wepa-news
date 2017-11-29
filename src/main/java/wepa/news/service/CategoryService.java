package wepa.news.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.hasText;
import wepa.news.domain.Category;
import wepa.news.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public void addCategory(Category category) {
        if (hasText(category.getName()) && !categoryExists(category)) {
            categoryRepository.save(category);
        }
    }

    private boolean categoryExists(Category category) {
        return categoryRepository.findByName(category.getName()).isPresent();
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getCategoriesByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id).get())
                .collect(Collectors.toList());
    }
}
