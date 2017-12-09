package wepa.news.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// We use a separate data transfer object for posting/putting news mostly because we
// recieve a list of ids that need to be converted to authors/categories and
// would like to use annotation validation for those too
@Data
public class NewsItemWriteDTO {

    private Long id;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 200, message = "Title must be under 200 characters")
    private String title;

    @NotBlank(message = "Lead text must not be blank")
    @Size(max = 1000, message = "Lead text must be under 1000 characters")
    private String leadText;

    @NotBlank(message = "Content must not be blank")
    @Size(max = 10000, message = "Content must be under 10000 characters")
    private String content;

    @NotNull(message = "An image must be provided")
    private byte[] imageData;

    @ManyToMany
    @NotEmpty(message = "At least one author must be provided")
    private List<Long> authorIds;

    @ManyToMany
    @NotEmpty(message = "At least one category must be provided")
    private List<Long> categoryIds;

    @JsonProperty("image")
    public void setImage(MultipartFile image) throws IOException {
        // With FormData an image is always present, even when nothing is uploaded.
        // So we validate that it is actually set here, unless we are updating,
        // in which case id is sent. If not set when updating, it will simply
        // be ignored.
        if (!image.isEmpty() || id != null) {
            this.imageData = image.getBytes();
        }
    }
}
