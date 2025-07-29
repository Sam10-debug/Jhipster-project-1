package com.emorinken.order.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 10, max = 13)
    @Column(name = "book_isbn", length = 13, nullable = false, unique = true)
    private String bookIsbn;

    @NotNull
    @Size(min=1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @NotNull
    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPrice;



    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookIsbn() {
        return this.bookIsbn;
    }

    public Order bookIsbn(String bookIsbn) {
        this.setBookIsbn(bookIsbn);
        return this;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Order quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Order customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public Order orderDate(LocalDate orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public Order totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", bookIsbn='" + getBookIsbn() + "'" +
            ", quantity=" + getQuantity() +
            ", customerName='" + getCustomerName() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
