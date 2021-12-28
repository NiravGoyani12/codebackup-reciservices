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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GrantedCardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GrantedCardType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="PaymentFrequency" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PaymentFrequencyDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PaymentMethod" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PaymentMethodDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NextPaymentDue" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="NextPaymentDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="LastPaymentMade" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="OutstandingBalance" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="NumberOfPaymentsPastDue" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="ChargedAmount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="LastChargeDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="OverTheLimitAmount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="OverTheLimitDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="MinPaymentPercentage" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="ReorganisedCreditCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ReorganisedCreditCodeDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ReorganisedCCRContractCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="OriginalCCRContractCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ReorganisedCreditCodeDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="LastPaymentDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GrantedCardType")
public class GrantedCardType {

    @XmlAttribute(name = "CreditLimit")
    protected BigInteger creditLimit;
    @XmlAttribute(name = "PaymentFrequency")
    protected String paymentFrequency;
    @XmlAttribute(name = "PaymentFrequencyDesc")
    protected String paymentFrequencyDesc;
    @XmlAttribute(name = "PaymentMethod")
    protected String paymentMethod;
    @XmlAttribute(name = "PaymentMethodDesc")
    protected String paymentMethodDesc;
    @XmlAttribute(name = "NextPaymentDue")
    protected BigInteger nextPaymentDue;
    @XmlAttribute(name = "NextPaymentDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar nextPaymentDate;
    @XmlAttribute(name = "LastPaymentMade")
    protected BigInteger lastPaymentMade;
    @XmlAttribute(name = "OutstandingBalance")
    protected BigInteger outstandingBalance;
    @XmlAttribute(name = "NumberOfPaymentsPastDue")
    protected BigInteger numberOfPaymentsPastDue;
    @XmlAttribute(name = "ChargedAmount")
    protected BigInteger chargedAmount;
    @XmlAttribute(name = "LastChargeDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastChargeDate;
    @XmlAttribute(name = "OverTheLimitAmount")
    protected BigInteger overTheLimitAmount;
    @XmlAttribute(name = "OverTheLimitDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar overTheLimitDate;
    @XmlAttribute(name = "MinPaymentPercentage")
    protected BigInteger minPaymentPercentage;
    @XmlAttribute(name = "ReorganisedCreditCode")
    protected String reorganisedCreditCode;
    @XmlAttribute(name = "ReorganisedCreditCodeDesc")
    protected String reorganisedCreditCodeDesc;
    @XmlAttribute(name = "ReorganisedCCRContractCode")
    protected String reorganisedCCRContractCode;
    @XmlAttribute(name = "OriginalCCRContractCode")
    protected String originalCCRContractCode;
    @XmlAttribute(name = "ReorganisedCreditCodeDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reorganisedCreditCodeDate;
    @XmlAttribute(name = "LastPaymentDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastPaymentDate;

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCreditLimit(BigInteger value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the paymentFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    /**
     * Sets the value of the paymentFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentFrequency(String value) {
        this.paymentFrequency = value;
    }

    /**
     * Gets the value of the paymentFrequencyDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentFrequencyDesc() {
        return paymentFrequencyDesc;
    }

    /**
     * Sets the value of the paymentFrequencyDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentFrequencyDesc(String value) {
        this.paymentFrequencyDesc = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the value of the paymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

    /**
     * Gets the value of the paymentMethodDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMethodDesc() {
        return paymentMethodDesc;
    }

    /**
     * Sets the value of the paymentMethodDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMethodDesc(String value) {
        this.paymentMethodDesc = value;
    }

    /**
     * Gets the value of the nextPaymentDue property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNextPaymentDue() {
        return nextPaymentDue;
    }

    /**
     * Sets the value of the nextPaymentDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNextPaymentDue(BigInteger value) {
        this.nextPaymentDue = value;
    }

    /**
     * Gets the value of the nextPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNextPaymentDate() {
        return nextPaymentDate;
    }

    /**
     * Sets the value of the nextPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNextPaymentDate(XMLGregorianCalendar value) {
        this.nextPaymentDate = value;
    }

    /**
     * Gets the value of the lastPaymentMade property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLastPaymentMade() {
        return lastPaymentMade;
    }

    /**
     * Sets the value of the lastPaymentMade property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLastPaymentMade(BigInteger value) {
        this.lastPaymentMade = value;
    }

    /**
     * Gets the value of the outstandingBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOutstandingBalance() {
        return outstandingBalance;
    }

    /**
     * Sets the value of the outstandingBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOutstandingBalance(BigInteger value) {
        this.outstandingBalance = value;
    }

    /**
     * Gets the value of the numberOfPaymentsPastDue property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfPaymentsPastDue() {
        return numberOfPaymentsPastDue;
    }

    /**
     * Sets the value of the numberOfPaymentsPastDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfPaymentsPastDue(BigInteger value) {
        this.numberOfPaymentsPastDue = value;
    }

    /**
     * Gets the value of the chargedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getChargedAmount() {
        return chargedAmount;
    }

    /**
     * Sets the value of the chargedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setChargedAmount(BigInteger value) {
        this.chargedAmount = value;
    }

    /**
     * Gets the value of the lastChargeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastChargeDate() {
        return lastChargeDate;
    }

    /**
     * Sets the value of the lastChargeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastChargeDate(XMLGregorianCalendar value) {
        this.lastChargeDate = value;
    }

    /**
     * Gets the value of the overTheLimitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOverTheLimitAmount() {
        return overTheLimitAmount;
    }

    /**
     * Sets the value of the overTheLimitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOverTheLimitAmount(BigInteger value) {
        this.overTheLimitAmount = value;
    }

    /**
     * Gets the value of the overTheLimitDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOverTheLimitDate() {
        return overTheLimitDate;
    }

    /**
     * Sets the value of the overTheLimitDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOverTheLimitDate(XMLGregorianCalendar value) {
        this.overTheLimitDate = value;
    }

    /**
     * Gets the value of the minPaymentPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinPaymentPercentage() {
        return minPaymentPercentage;
    }

    /**
     * Sets the value of the minPaymentPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinPaymentPercentage(BigInteger value) {
        this.minPaymentPercentage = value;
    }

    /**
     * Gets the value of the reorganisedCreditCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReorganisedCreditCode() {
        return reorganisedCreditCode;
    }

    /**
     * Sets the value of the reorganisedCreditCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReorganisedCreditCode(String value) {
        this.reorganisedCreditCode = value;
    }

    /**
     * Gets the value of the reorganisedCreditCodeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReorganisedCreditCodeDesc() {
        return reorganisedCreditCodeDesc;
    }

    /**
     * Sets the value of the reorganisedCreditCodeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReorganisedCreditCodeDesc(String value) {
        this.reorganisedCreditCodeDesc = value;
    }

    /**
     * Gets the value of the reorganisedCCRContractCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReorganisedCCRContractCode() {
        return reorganisedCCRContractCode;
    }

    /**
     * Sets the value of the reorganisedCCRContractCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReorganisedCCRContractCode(String value) {
        this.reorganisedCCRContractCode = value;
    }

    /**
     * Gets the value of the originalCCRContractCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalCCRContractCode() {
        return originalCCRContractCode;
    }

    /**
     * Sets the value of the originalCCRContractCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalCCRContractCode(String value) {
        this.originalCCRContractCode = value;
    }

    /**
     * Gets the value of the reorganisedCreditCodeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReorganisedCreditCodeDate() {
        return reorganisedCreditCodeDate;
    }

    /**
     * Sets the value of the reorganisedCreditCodeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReorganisedCreditCodeDate(XMLGregorianCalendar value) {
        this.reorganisedCreditCodeDate = value;
    }

    /**
     * Gets the value of the lastPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPaymentDate() {
        return lastPaymentDate;
    }

    /**
     * Sets the value of the lastPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPaymentDate(XMLGregorianCalendar value) {
        this.lastPaymentDate = value;
    }

}
