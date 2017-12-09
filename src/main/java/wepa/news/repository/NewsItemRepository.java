package wepa.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.NewsItem;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

    Page<NewsItem> findByCategories_name(Pageable pageable, String categoryName);
}
