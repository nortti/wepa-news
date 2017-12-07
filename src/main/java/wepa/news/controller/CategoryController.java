package wepa.news.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wepa.news.domain.Category;
import wepa.news.repository.CategoryRepository;

@RestController
@RequestMapping("/rest/categories/")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Category getOne(@PathVariable Long id) {
        return categoryRepository.getOne(id);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Category category) {
        categoryRepository.save(category);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
