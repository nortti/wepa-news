package wepa.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findByCategories_name(Pageable pageable, String categoryName);
}
