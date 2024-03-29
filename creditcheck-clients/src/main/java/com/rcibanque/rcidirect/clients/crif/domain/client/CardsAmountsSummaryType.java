//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardsAmountsSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardsAmountsSummaryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="BCAmounts" type="{urn:crif-creditbureau:v1}CardsAmountsType"/&gt;
 *         &lt;element name="GAmounts" type="{urn:crif-creditbureau:v1}CardsAmountsType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumberOfContracts" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardsAmountsSummaryType", propOrder = {
    "bcAmounts",
    "gAmounts"
})
public class CardsAmountsSummaryType {

    @XmlElement(name = "BCAmounts")
    protected CardsAmountsType bcAmounts;
    @XmlElement(name = "GAmounts")
    protected CardsAmountsType gAmounts;
    @XmlAttribute(name = "NumberOfContracts")
    protected BigInteger numberOfContracts;

    /**
     * Gets the value of the bcAmounts property.
     * 
     * @return
     *     possible object is
     *     {@link CardsAmountsType }
     *     
     */
    public CardsAmountsType getBCAmounts() {
        return bcAmounts;
    }

    /**
     * Sets the value of the bcAmounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardsAmountsType }
     *     
     */
    public void setBCAmounts(CardsAmountsType value) {
        this.bcAmounts = value;
    }

    /**
     * Gets the value of the gAmounts property.
     * 
     * @return
     *     possible object is
     *     {@link CardsAmountsType }
     *     
     */
    public CardsAmountsType getGAmounts() {
        return gAmounts;
    }

    /**
     * Sets the value of the gAmounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardsAmountsType }
     *     
     */
    public void setGAmounts(CardsAmountsType value) {
        this.gAmounts = value;
    }

    /**
     * Gets the value of the numberOfContracts property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfContracts() {
        return numberOfContracts;
    }

    /**
     * Sets the value of the numberOfContracts property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfContracts(BigInteger value) {
        this.numberOfContracts = value;
    }

}
