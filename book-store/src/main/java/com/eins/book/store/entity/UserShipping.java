package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 用户发货 实体
 */
@Table(name = "bookstoredatabase.user_shipping")
public class UserShipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_shipping_city")
    private String userShippingCity;

    @Column(name = "user_shipping_country")
    private String userShippingCountry;

    @Column(name = "user_shipping_default")
    private Boolean userShippingDefault;

    @Column(name = "user_shipping_name")
    private String userShippingName;

    @Column(name = "user_shipping_state")
    private String userShippingState;

    @Column(name = "user_shipping_street1")
    private String userShippingStreet1;

    @Column(name = "user_shipping_street2")
    private String userShippingStreet2;

    @Column(name = "user_shipping_zipcode")
    private String userShippingZipcode;

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
     * @return user_shipping_city
     */
    public String getUserShippingCity() {
        return userShippingCity;
    }

    /**
     * @param userShippingCity
     */
    public void setUserShippingCity(String userShippingCity) {
        this.userShippingCity = userShippingCity;
    }

    /**
     * @return user_shipping_country
     */
    public String getUserShippingCountry() {
        return userShippingCountry;
    }

    /**
     * @param userShippingCountry
     */
    public void setUserShippingCountry(String userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
    }

    /**
     * @return user_shipping_default
     */
    public Boolean getUserShippingDefault() {
        return userShippingDefault;
    }

    /**
     * @param userShippingDefault
     */
    public void setUserShippingDefault(Boolean userShippingDefault) {
        this.userShippingDefault = userShippingDefault;
    }

    /**
     * @return user_shipping_name
     */
    public String getUserShippingName() {
        return userShippingName;
    }

    /**
     * @param userShippingName
     */
    public void setUserShippingName(String userShippingName) {
        this.userShippingName = userShippingName;
    }

    /**
     * @return user_shipping_state
     */
    public String getUserShippingState() {
        return userShippingState;
    }

    /**
     * @param userShippingState
     */
    public void setUserShippingState(String userShippingState) {
        this.userShippingState = userShippingState;
    }

    /**
     * @return user_shipping_street1
     */
    public String getUserShippingStreet1() {
        return userShippingStreet1;
    }

    /**
     * @param userShippingStreet1
     */
    public void setUserShippingStreet1(String userShippingStreet1) {
        this.userShippingStreet1 = userShippingStreet1;
    }

    /**
     * @return user_shipping_street2
     */
    public String getUserShippingStreet2() {
        return userShippingStreet2;
    }

    /**
     * @param userShippingStreet2
     */
    public void setUserShippingStreet2(String userShippingStreet2) {
        this.userShippingStreet2 = userShippingStreet2;
    }

    /**
     * @return user_shipping_zipcode
     */
    public String getUserShippingZipcode() {
        return userShippingZipcode;
    }

    /**
     * @param userShippingZipcode
     */
    public void setUserShippingZipcode(String userShippingZipcode) {
        this.userShippingZipcode = userShippingZipcode;
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