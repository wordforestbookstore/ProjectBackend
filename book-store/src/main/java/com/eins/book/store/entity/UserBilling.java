package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 用户账单 实体
 */
@Table(name = "bookstoredatabase.user_billing")
public class UserBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_billing_city")
    private String userBillingCity;

    @Column(name = "user_billing_country")
    private String userBillingCountry;

    @Column(name = "user_billing_name")
    private String userBillingName;

    @Column(name = "user_billing_state")
    private String userBillingState;

    @Column(name = "user_billing_street1")
    private String userBillingStreet1;

    @Column(name = "user_billing_street2")
    private String userBillingStreet2;

    @Column(name = "user_billing_zipcode")
    private String userBillingZipcode;

    @Column(name = "user_payment_id")
    private Long userPaymentId;

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
     * @return user_billing_city
     */
    public String getUserBillingCity() {
        return userBillingCity;
    }

    /**
     * @param userBillingCity
     */
    public void setUserBillingCity(String userBillingCity) {
        this.userBillingCity = userBillingCity;
    }

    /**
     * @return user_billing_country
     */
    public String getUserBillingCountry() {
        return userBillingCountry;
    }

    /**
     * @param userBillingCountry
     */
    public void setUserBillingCountry(String userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
    }

    /**
     * @return user_billing_name
     */
    public String getUserBillingName() {
        return userBillingName;
    }

    /**
     * @param userBillingName
     */
    public void setUserBillingName(String userBillingName) {
        this.userBillingName = userBillingName;
    }

    /**
     * @return user_billing_state
     */
    public String getUserBillingState() {
        return userBillingState;
    }

    /**
     * @param userBillingState
     */
    public void setUserBillingState(String userBillingState) {
        this.userBillingState = userBillingState;
    }

    /**
     * @return user_billing_street1
     */
    public String getUserBillingStreet1() {
        return userBillingStreet1;
    }

    /**
     * @param userBillingStreet1
     */
    public void setUserBillingStreet1(String userBillingStreet1) {
        this.userBillingStreet1 = userBillingStreet1;
    }

    /**
     * @return user_billing_street2
     */
    public String getUserBillingStreet2() {
        return userBillingStreet2;
    }

    /**
     * @param userBillingStreet2
     */
    public void setUserBillingStreet2(String userBillingStreet2) {
        this.userBillingStreet2 = userBillingStreet2;
    }

    /**
     * @return user_billing_zipcode
     */
    public String getUserBillingZipcode() {
        return userBillingZipcode;
    }

    /**
     * @param userBillingZipcode
     */
    public void setUserBillingZipcode(String userBillingZipcode) {
        this.userBillingZipcode = userBillingZipcode;
    }

    /**
     * @return user_payment_id
     */
    public Long getUserPaymentId() {
        return userPaymentId;
    }

    /**
     * @param userPaymentId
     */
    public void setUserPaymentId(Long userPaymentId) {
        this.userPaymentId = userPaymentId;
    }
}