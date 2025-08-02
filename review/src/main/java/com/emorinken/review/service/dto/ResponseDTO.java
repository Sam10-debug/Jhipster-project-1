package com.emorinken.review.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.emorinken.review.domain.Review} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String title;

    @NotNull
    @Size(min = 2, max = 40)
    private String author;

    @NotNull
    @Size(min = 3, max = 100)
    private String reviewText;

    @NotNull
    @Size(min = 2, max = 40)
    private String reviewerName;







    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewerName() {
        return reviewerName;
    }


    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseDTO)) {
            return false;
        }

        ResponseDTO responseDTO = (ResponseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + getId() +
            ", reviewText='" + getReviewText() + "'" +
            ", reviewerName='" + getReviewerName() + "'" +

            "}";
    }
}
