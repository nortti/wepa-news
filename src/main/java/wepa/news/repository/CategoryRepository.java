package wepa.news.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByDisplayInNavigation(boolean displayInNavigation);
}
