package com.eins.book.store.entity;

import javax.persistence.*;

@Table(name = "bookstoredatabase.billing_address")
public class BillingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "billing_address_city")
    private String billingAddressCity;

    @Column(name = "billing_address_country")
    private String billingAddressCountry;

    @Column(name = "billing_address_name")
    private String billingAddressName;

    @Column(name = "billing_address_state")
    private String billingAddressState;

    @Column(name = "billing_address_street1")
    private String billingAddressStreet1;

    @Column(name = "billing_address_street2")
    private String billingAddressStreet2;

    @Column(name = "billing_address_zipcode")
    private String billingAddressZipcode;

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
     * @return billing_address_city
     */
    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    /**
     * @param billingAddressCity
     */
    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    /**
     * @return billing_address_country
     */
    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    /**
     * @param billingAddressCountry
     */
    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    /**
     * @return billing_address_name
     */
    public String getBillingAddressName() {
        return billingAddressName;
    }

    /**
     * @param billingAddressName
     */
    public void setBillingAddressName(String billingAddressName) {
        this.billingAddressName = billingAddressName;
    }

    /**
     * @return billing_address_state
     */
    public String getBillingAddressState() {
        return billingAddressState;
    }

    /**
     * @param billingAddressState
     */
    public void setBillingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
    }

    /**
     * @return billing_address_street1
     */
    public String getBillingAddressStreet1() {
        return billingAddressStreet1;
    }

    /**
     * @param billingAddressStreet1
     */
    public void setBillingAddressStreet1(String billingAddressStreet1) {
        this.billingAddressStreet1 = billingAddressStreet1;
    }

    /**
     * @return billing_address_street2
     */
    public String getBillingAddressStreet2() {
        return billingAddressStreet2;
    }

    /**
     * @param billingAddressStreet2
     */
    public void setBillingAddressStreet2(String billingAddressStreet2) {
        this.billingAddressStreet2 = billingAddressStreet2;
    }

    /**
     * @return billing_address_zipcode
     */
    public String getBillingAddressZipcode() {
        return billingAddressZipcode;
    }

    /**
     * @param billingAddressZipcode
     */
    public void setBillingAddressZipcode(String billingAddressZipcode) {
        this.billingAddressZipcode = billingAddressZipcode;
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