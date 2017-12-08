package wepa.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.news.domain.NewsImage;

public interface NewsImageRepository extends JpaRepository<NewsImage, Long> {
}
