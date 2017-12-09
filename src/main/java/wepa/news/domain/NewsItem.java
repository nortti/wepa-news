package wepa.news.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Column(length = 200)
    private String title;

    @Column(length = 10000)
    private String leadText;

    @Column(length = 100000)
    private String content;

    private LocalDateTime datePublished;

    @Lob
    @Type(type = "image")
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    private List<Category> categories;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    private List<Author> authors;

    private int views;

    public String getDatePublished() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        return datePublished.format(formatter);
    }
}
