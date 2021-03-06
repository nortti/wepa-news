package wepa.news.domain;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Author extends AbstractPersistable<Long> {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be under 50 characters")
    private String name;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    private List<News> news;
}
