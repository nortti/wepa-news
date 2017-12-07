package wepa.news.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NewsItem extends AbstractPersistable<Long> implements Serializable {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 10000)
    private String leadText;

    @Column(nullable = false, length = 100000)
    private String content;

    @Column(nullable = false)
    private LocalDate datePublished;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
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
