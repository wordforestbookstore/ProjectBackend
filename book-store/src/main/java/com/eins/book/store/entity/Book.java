package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 图书 实体
 */
@Table(name = "bookstoredatabase.book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active;

    private String author;

    private String category;

    private String format;

    @Column(name = "in_stock_number")
    private Integer inStockNumber;

    private Long isbn;

    private String language;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @Column(name = "our_price")
    private Double ourPrice;

    @Column(name = "publication_date")
    private String publicationDate;

    private String publisher;

    @Column(name = "shipping_weight")
    private Double shippingWeight;

    private String title;

    private String description;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return in_stock_number
     */
    public Integer getInStockNumber() {
        return inStockNumber;
    }

    /**
     * @param inStockNumber
     */
    public void setInStockNumber(Integer inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

    /**
     * @return isbn
     */
    public Long getIsbn() {
        return isbn;
    }

    /**
     * @param isbn
     */
    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    /**
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return list_price
     */
    public Double getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice
     */
    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * @return number_of_pages
     */
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * @param numberOfPages
     */
    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * @return our_price
     */
    public Double getOurPrice() {
        return ourPrice;
    }

    /**
     * @param ourPrice
     */
    public void setOurPrice(Double ourPrice) {
        this.ourPrice = ourPrice;
    }

    /**
     * @return publication_date
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * @param publicationDate
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return shipping_weight
     */
    public Double getShippingWeight() {
        return shippingWeight;
    }

    /**
     * @param shippingWeight
     */
    public void setShippingWeight(Double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}