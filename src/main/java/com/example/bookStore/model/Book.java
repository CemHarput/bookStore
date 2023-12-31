package com.example.bookStore.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ISBN;
    private String title;
    private String author;
    private BigDecimal price;
    private int stockQuantity;
    private Date createdAt;
    private Date updateAt;

    @ManyToMany(mappedBy = "books")
    private List<PurchaseOrder> purchaseOrders;


    public Book() {
    }
    public Book(UUID ISBN, String title, String author, BigDecimal price, int stockQuantity, Date createdAt, Date updateAt) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updateAt = updateAt;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getStockQuantity() == book.getStockQuantity() && Objects.equals(getISBN(), book.getISBN()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getCreatedAt(), book.getCreatedAt()) && Objects.equals(getUpdateAt(), book.getUpdateAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISBN(), getTitle(), getAuthor(), getPrice(), getStockQuantity(), getCreatedAt(), getUpdateAt());
    }


}
