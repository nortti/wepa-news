package wepa.news.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);
}
