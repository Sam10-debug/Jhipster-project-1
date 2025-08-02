package com.emorinken.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "review_text", length = 100, nullable = false)
    private String reviewText;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "reviewer_name", length = 40, nullable = false)
    private String reviewerName;

    @NotNull
    @Column(name = "book_isbn", nullable = false, unique = true)
    private String bookIsbn;

    @NotNull
    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Review id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public Review reviewText(String reviewText) {
        this.setReviewText(reviewText);
        return this;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewerName() {
        return this.reviewerName;
    }

    public Review reviewerName(String reviewerName) {
        this.setReviewerName(reviewerName);
        return this;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getBookIsbn() {
        return this.bookIsbn;
    }

    public Review bookIsbn(String bookIsbn) {
        this.setBookIsbn(bookIsbn);
        return this;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public LocalDate getReviewDate() {
        return this.reviewDate;
    }

    public Review reviewDate(LocalDate reviewDate) {
        this.setReviewDate(reviewDate);
        return this;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return getId() != null && getId().equals(((Review) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", reviewText='" + getReviewText() + "'" +
            ", reviewerName='" + getReviewerName() + "'" +
            ", bookIsbn='" + getBookIsbn() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            "}";
    }
}
