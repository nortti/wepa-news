package wepa.news.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NewsImage extends AbstractPersistable<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
}
