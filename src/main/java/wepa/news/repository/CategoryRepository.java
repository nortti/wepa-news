package wepa.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
