package wepa.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.NewsItem;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

}
