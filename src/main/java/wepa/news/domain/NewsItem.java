package wepa.news.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Builder
public class NewsItem extends AbstractPersistable<Long> {

    @Column(length = 100)
    private String title;

    @Column(length = 1024)
    private String leadText;

    @Column(length = 16384)
    private String content;

    private LocalDate datePublished;

    private int views;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @ManyToMany
    private List<Category> categories;

    @ManyToMany
    private List<Author> authors;

}
