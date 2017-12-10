package wepa.news.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class News extends AbstractPersistable<Long> {

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
    @NotEmpty
    private byte[] imageData;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private List<Category> categories;

    @ManyToMany
    @NotEmpty
    @Basic(fetch = FetchType.LAZY)
    private List<Author> authors;

    private int views;

    public String getDatePublished() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        return datePublished.format(formatter);
    }

    @JsonIgnore // Used in tests
    public LocalDateTime getDateTimePublished() {
        return datePublished;
    }
}
