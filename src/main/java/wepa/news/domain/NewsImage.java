package wepa.news.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NewsImage extends AbstractPersistable<Long> implements Serializable {

    @Column(nullable = false, length = 2000000)
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
}
