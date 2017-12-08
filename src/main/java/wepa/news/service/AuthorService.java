package wepa.news.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.news.domain.Author;
import wepa.news.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    public Author getOne(Long id) {
        return authorRepository.getOne(id);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
