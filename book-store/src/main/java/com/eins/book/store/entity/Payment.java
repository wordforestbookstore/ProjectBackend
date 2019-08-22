package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 账单 实体
 */
@Table(name = "bookstoredatabase.payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    private Integer cvc;

    @Column(name = "expiry_month")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    private Integer expiryYear;

    @Column(name = "holder_name")
    private String holderName;

    private String type;

    @Column(name = "order_id")
    private Long orderId;

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
     * @return card_number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return cvc
     */
    public Integer getCvc() {
        return cvc;
    }

    /**
     * @param cvc
     */
    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    /**
     * @return expiry_month
     */
    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * @param expiryMonth
     */
    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * @return expiry_year
     */
    public Integer getExpiryYear() {
        return expiryYear;
    }

    /**
     * @param expiryYear
     */
    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * @return holder_name
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * @param holderName
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}