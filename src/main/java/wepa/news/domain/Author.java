package wepa.news.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author extends AbstractPersistable<Long> {

    @Column(length = 50)
    private String name;

    @ManyToMany
    private List<NewsItem> newsItems;

    public Author(String name) {
        this.name = name;
    }
}
