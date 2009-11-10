//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-01/24/2006 06:08 PM(kohsuke)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.06.30 at 06:36:47 AM CEST 
//


package de.thorstenberger.examServer.dao.xml.jaxb;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/oetzi/workspace/ElatePortal/examServer/jaxb/users.xsd line 8)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="role-ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="account_enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="account_expired" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="account_locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="city" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="country" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="credentials_expired" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="email" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="first_name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="id" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="last_name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="password" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="password_hint" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="postal_code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="province" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="username" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="website" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="IdCount" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface UsersType {


    /**
     * Gets the value of the User property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the User property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link de.thorstenberger.examServer.dao.xml.jaxb.UsersType.UserType}
     * 
     */
    java.util.List getUser();

    boolean isSetUser();

    void unsetUser();

    /**
     * Gets the value of the idCount property.
     * 
     */
    long getIdCount();

    /**
     * Sets the value of the idCount property.
     * 
     */
    void setIdCount(long value);

    boolean isSetIdCount();

    void unsetIdCount();


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/oetzi/workspace/ElatePortal/examServer/jaxb/users.xsd line 11)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="role-ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="account_enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="account_expired" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="account_locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="city" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="country" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="credentials_expired" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="email" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="first_name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="id" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="last_name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="password" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="password_hint" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="postal_code" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="province" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="username" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="website" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface UserType {


        /**
         * Gets the value of the postalCode property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getPostalCode();

        /**
         * Sets the value of the postalCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setPostalCode(java.lang.String value);

        boolean isSetPostalCode();

        void unsetPostalCode();

        /**
         * Gets the value of the website property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getWebsite();

        /**
         * Sets the value of the website property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setWebsite(java.lang.String value);

        boolean isSetWebsite();

        void unsetWebsite();

        /**
         * Gets the value of the accountEnabled property.
         * 
         */
        boolean isAccountEnabled();

        /**
         * Sets the value of the accountEnabled property.
         * 
         */
        void setAccountEnabled(boolean value);

        boolean isSetAccountEnabled();

        void unsetAccountEnabled();

        /**
         * Gets the value of the accountLocked property.
         * 
         */
        boolean isAccountLocked();

        /**
         * Sets the value of the accountLocked property.
         * 
         */
        void setAccountLocked(boolean value);

        boolean isSetAccountLocked();

        void unsetAccountLocked();

        /**
         * Gets the value of the city property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getCity();

        /**
         * Sets the value of the city property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setCity(java.lang.String value);

        boolean isSetCity();

        void unsetCity();

        /**
         * Gets the value of the password property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getPassword();

        /**
         * Sets the value of the password property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setPassword(java.lang.String value);

        boolean isSetPassword();

        void unsetPassword();

        /**
         * Gets the value of the email property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getEmail();

        /**
         * Sets the value of the email property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setEmail(java.lang.String value);

        boolean isSetEmail();

        void unsetEmail();

        /**
         * Gets the value of the phoneNumber property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getPhoneNumber();

        /**
         * Sets the value of the phoneNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setPhoneNumber(java.lang.String value);

        boolean isSetPhoneNumber();

        void unsetPhoneNumber();

        /**
         * Gets the value of the version property.
         * 
         */
        int getVersion();

        /**
         * Sets the value of the version property.
         * 
         */
        void setVersion(int value);

        boolean isSetVersion();

        void unsetVersion();

        /**
         * Gets the value of the firstName property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getFirstName();

        /**
         * Sets the value of the firstName property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setFirstName(java.lang.String value);

        boolean isSetFirstName();

        void unsetFirstName();

        /**
         * Gets the value of the address property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getAddress();

        /**
         * Sets the value of the address property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setAddress(java.lang.String value);

        boolean isSetAddress();

        void unsetAddress();

        /**
         * Gets the value of the passwordHint property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getPasswordHint();

        /**
         * Sets the value of the passwordHint property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setPasswordHint(java.lang.String value);

        boolean isSetPasswordHint();

        void unsetPasswordHint();

        /**
         * Gets the value of the country property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getCountry();

        /**
         * Sets the value of the country property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setCountry(java.lang.String value);

        boolean isSetCountry();

        void unsetCountry();

        /**
         * Gets the value of the RoleRef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the RoleRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoleRef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String}
         * 
         */
        java.util.List getRoleRef();

        boolean isSetRoleRef();

        void unsetRoleRef();

        /**
         * Gets the value of the credentialsExpired property.
         * 
         */
        boolean isCredentialsExpired();

        /**
         * Sets the value of the credentialsExpired property.
         * 
         */
        void setCredentialsExpired(boolean value);

        boolean isSetCredentialsExpired();

        void unsetCredentialsExpired();

        /**
         * Gets the value of the accountExpired property.
         * 
         */
        boolean isAccountExpired();

        /**
         * Sets the value of the accountExpired property.
         * 
         */
        void setAccountExpired(boolean value);

        boolean isSetAccountExpired();

        void unsetAccountExpired();

        /**
         * Gets the value of the id property.
         * 
         */
        long getId();

        /**
         * Sets the value of the id property.
         * 
         */
        void setId(long value);

        boolean isSetId();

        void unsetId();

        /**
         * Gets the value of the username property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getUsername();

        /**
         * Sets the value of the username property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setUsername(java.lang.String value);

        boolean isSetUsername();

        void unsetUsername();

        /**
         * Gets the value of the province property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getProvince();

        /**
         * Sets the value of the province property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setProvince(java.lang.String value);

        boolean isSetProvince();

        void unsetProvince();

        /**
         * Gets the value of the lastName property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getLastName();

        /**
         * Sets the value of the lastName property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setLastName(java.lang.String value);

        boolean isSetLastName();

        void unsetLastName();

    }

}