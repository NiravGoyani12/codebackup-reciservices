//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressOutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressOutputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="FullAddress" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="City" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="County" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Eircode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Country" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="BorrowerNotContactable" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressOutputType")
public class AddressOutputType {

    @XmlAttribute(name = "Type")
    protected String type;
    @XmlAttribute(name = "FullAddress")
    protected String fullAddress;
    @XmlAttribute(name = "AddressLine1")
    protected String addressLine1;
    @XmlAttribute(name = "AddressLine2")
    protected String addressLine2;
    @XmlAttribute(name = "City")
    protected String city;
    @XmlAttribute(name = "PostalCode")
    protected String postalCode;
    @XmlAttribute(name = "County")
    protected String county;
    @XmlAttribute(name = "Eircode")
    protected String eircode;
    @XmlAttribute(name = "Country")
    protected String country;
    @XmlAttribute(name = "BorrowerNotContactable")
    protected String borrowerNotContactable;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the fullAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullAddress() {
        return fullAddress;
    }

    /**
     * Sets the value of the fullAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullAddress(String value) {
        this.fullAddress = value;
    }

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounty(String value) {
        this.county = value;
    }

    /**
     * Gets the value of the eircode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEircode() {
        return eircode;
    }

    /**
     * Sets the value of the eircode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEircode(String value) {
        this.eircode = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the borrowerNotContactable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerNotContactable() {
        return borrowerNotContactable;
    }

    /**
     * Sets the value of the borrowerNotContactable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerNotContactable(String value) {
        this.borrowerNotContactable = value;
    }

}