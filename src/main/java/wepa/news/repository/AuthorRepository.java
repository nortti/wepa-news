package wepa.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
