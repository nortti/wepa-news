package wepa.news.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NewsItem extends AbstractPersistable<Long> {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 10000)
    private String leadText;

    @Column(nullable = false, length = 100000)
    private String content;

    @Column(nullable = false)
    private LocalDate datePublished;

    @Lob
    @Type(type = "image")
    private byte[] imageData;

    @ManyToMany
    @Column(nullable = false)
    private List<Category> categories;

    @ManyToMany
    @Column(nullable = false)
    private List<Author> authors;

    private int views;

    public String getDatePublished() {
        return datePublished.toString();
    }
}
