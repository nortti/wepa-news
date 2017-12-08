package wepa.news.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.news.domain.Author;
import wepa.news.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Author getOne(Long id) {
        return authorRepository.getOne(id);
    }

    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
