package wepa.news.controller.rest;

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
import wepa.news.domain.Author;
import wepa.news.repository.AuthorRepository;

@RestController
@RequestMapping("/rest/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authoRepository;

    @GetMapping
    public List<Author> getAll() {
        return authoRepository.findAll();
    }

    @GetMapping("{id}")
    public Author getOne(@PathVariable Long id) {
        return authoRepository.getOne(id);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Author author) {
        authoRepository.save(author);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authoRepository.deleteById(id);
    }
}
