package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 发货地址 实体
 */
@Table(name = "bookstoredatabase.shipping_address")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipping_address_city")
    private String shippingAddressCity;

    @Column(name = "shipping_address_country")
    private String shippingAddressCountry;

    @Column(name = "shipping_address_name")
    private String shippingAddressName;

    @Column(name = "shipping_address_state")
    private String shippingAddressState;

    @Column(name = "shipping_address_street1")
    private String shippingAddressStreet1;

    @Column(name = "shipping_address_street2")
    private String shippingAddressStreet2;

    @Column(name = "shipping_address_zipcode")
    private String shippingAddressZipcode;

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
     * @return shipping_address_city
     */
    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    /**
     * @param shippingAddressCity
     */
    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    /**
     * @return shipping_address_country
     */
    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    /**
     * @param shippingAddressCountry
     */
    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    /**
     * @return shipping_address_name
     */
    public String getShippingAddressName() {
        return shippingAddressName;
    }

    /**
     * @param shippingAddressName
     */
    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    /**
     * @return shipping_address_state
     */
    public String getShippingAddressState() {
        return shippingAddressState;
    }

    /**
     * @param shippingAddressState
     */
    public void setShippingAddressState(String shippingAddressState) {
        this.shippingAddressState = shippingAddressState;
    }

    /**
     * @return shipping_address_street1
     */
    public String getShippingAddressStreet1() {
        return shippingAddressStreet1;
    }

    /**
     * @param shippingAddressStreet1
     */
    public void setShippingAddressStreet1(String shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
    }

    /**
     * @return shipping_address_street2
     */
    public String getShippingAddressStreet2() {
        return shippingAddressStreet2;
    }

    /**
     * @param shippingAddressStreet2
     */
    public void setShippingAddressStreet2(String shippingAddressStreet2) {
        this.shippingAddressStreet2 = shippingAddressStreet2;
    }

    /**
     * @return shipping_address_zipcode
     */
    public String getShippingAddressZipcode() {
        return shippingAddressZipcode;
    }

    /**
     * @param shippingAddressZipcode
     */
    public void setShippingAddressZipcode(String shippingAddressZipcode) {
        this.shippingAddressZipcode = shippingAddressZipcode;
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