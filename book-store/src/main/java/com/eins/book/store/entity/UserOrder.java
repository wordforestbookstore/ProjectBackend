package com.eins.book.store.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * 用户订单 实体
 */
@Table(name = "bookstoredatabase.user_order")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_total")
    private BigDecimal orderTotal;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "billing_address_id")
    private Long billingAddressId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "shipping_address_id")
    private Long shippingAddressId;

    @Column(name = "user_id")
    private Long userId;

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
     * @return order_date
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return order_status
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return order_total
     */
    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    /**
     * @param orderTotal
     */
    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    /**
     * @return shipping_date
     */
    public Date getShippingDate() {
        return shippingDate;
    }

    /**
     * @param shippingDate
     */
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    /**
     * @return shipping_method
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * @param shippingMethod
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * @return billing_address_id
     */
    public Long getBillingAddressId() {
        return billingAddressId;
    }

    /**
     * @param billingAddressId
     */
    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    /**
     * @return payment_id
     */
    public Long getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId
     */
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return shipping_address_id
     */
    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    /**
     * @param shippingAddressId
     */
    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}