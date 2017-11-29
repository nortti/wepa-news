package wepa.news.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.hasText;
import wepa.news.domain.Author;
import wepa.news.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public void addAuthor(Author author) {
        if (hasText(author.getName())) {
            authorRepository.save(author);
        }
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> getAuthorsByIds(List<Long> authorIds) {
        return authorIds.stream()
                .map(id -> authorRepository.findById(id).get())
                .collect(Collectors.toList());
    }
}
