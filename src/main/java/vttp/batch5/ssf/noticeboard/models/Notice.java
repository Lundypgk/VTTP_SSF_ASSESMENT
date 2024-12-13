
package vttp.batch5.ssf.noticeboard.models;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class Notice {

    @NotBlank(message = "mandatory")
    @Size(min = 3, max = 128, message = "between 3 and 128 chars")
    private String title;

    @NotBlank(message = "email is mandatory")
    @Email(message = "invalid email format")
    private String poster;

    @NotNull(message = "mandatory")
    @Future(message = "date must be in the future")
    private LocalDate postDate;

    @NotEmpty(message = "at least one category")
    private List<String> categories;

    @NotBlank(message = "mandatory")
    private String text;

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
