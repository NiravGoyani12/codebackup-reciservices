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
 * <p>Java class for CardsMaximumType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardsMaximumType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="MaxOutstandingBalance" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="MaxOutstandingBalanceDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="MaxOverTheLimitAmount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="MaxOverTheLimitAmountDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="MaxOverduePaymentsNumber" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="MaxOverduePaymentsNumberDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="MaxChargedAmount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="MaxChargedAmountDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="LastCreditStatus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LastCreditStatusDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LastCreditStatusDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="LastRestructureEvent" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LastRestructureEventDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LastRestructureEventDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardsMaximumType")
public class CardsMaximumType {

    @XmlAttribute(name = "MaxOutstandingBalance")
    protected BigInteger maxOutstandingBalance;
    @XmlAttribute(name = "MaxOutstandingBalanceDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maxOutstandingBalanceDate;
    @XmlAttribute(name = "MaxOverTheLimitAmount")
    protected BigInteger maxOverTheLimitAmount;
    @XmlAttribute(name = "MaxOverTheLimitAmountDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maxOverTheLimitAmountDate;
    @XmlAttribute(name = "MaxOverduePaymentsNumber")
    protected BigInteger maxOverduePaymentsNumber;
    @XmlAttribute(name = "MaxOverduePaymentsNumberDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maxOverduePaymentsNumberDate;
    @XmlAttribute(name = "MaxChargedAmount")
    protected BigInteger maxChargedAmount;
    @XmlAttribute(name = "MaxChargedAmountDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maxChargedAmountDate;
    @XmlAttribute(name = "LastCreditStatus")
    protected String lastCreditStatus;
    @XmlAttribute(name = "LastCreditStatusDesc")
    protected String lastCreditStatusDesc;
    @XmlAttribute(name = "LastCreditStatusDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastCreditStatusDate;
    @XmlAttribute(name = "LastRestructureEvent")
    protected String lastRestructureEvent;
    @XmlAttribute(name = "LastRestructureEventDesc")
    protected String lastRestructureEventDesc;
    @XmlAttribute(name = "LastRestructureEventDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastRestructureEventDate;

    /**
     * Gets the value of the maxOutstandingBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOutstandingBalance() {
        return maxOutstandingBalance;
    }

    /**
     * Sets the value of the maxOutstandingBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOutstandingBalance(BigInteger value) {
        this.maxOutstandingBalance = value;
    }

    /**
     * Gets the value of the maxOutstandingBalanceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaxOutstandingBalanceDate() {
        return maxOutstandingBalanceDate;
    }

    /**
     * Sets the value of the maxOutstandingBalanceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaxOutstandingBalanceDate(XMLGregorianCalendar value) {
        this.maxOutstandingBalanceDate = value;
    }

    /**
     * Gets the value of the maxOverTheLimitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOverTheLimitAmount() {
        return maxOverTheLimitAmount;
    }

    /**
     * Sets the value of the maxOverTheLimitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOverTheLimitAmount(BigInteger value) {
        this.maxOverTheLimitAmount = value;
    }

    /**
     * Gets the value of the maxOverTheLimitAmountDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaxOverTheLimitAmountDate() {
        return maxOverTheLimitAmountDate;
    }

    /**
     * Sets the value of the maxOverTheLimitAmountDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaxOverTheLimitAmountDate(XMLGregorianCalendar value) {
        this.maxOverTheLimitAmountDate = value;
    }

    /**
     * Gets the value of the maxOverduePaymentsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOverduePaymentsNumber() {
        return maxOverduePaymentsNumber;
    }

    /**
     * Sets the value of the maxOverduePaymentsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOverduePaymentsNumber(BigInteger value) {
        this.maxOverduePaymentsNumber = value;
    }

    /**
     * Gets the value of the maxOverduePaymentsNumberDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaxOverduePaymentsNumberDate() {
        return maxOverduePaymentsNumberDate;
    }

    /**
     * Sets the value of the maxOverduePaymentsNumberDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaxOverduePaymentsNumberDate(XMLGregorianCalendar value) {
        this.maxOverduePaymentsNumberDate = value;
    }

    /**
     * Gets the value of the maxChargedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxChargedAmount() {
        return maxChargedAmount;
    }

    /**
     * Sets the value of the maxChargedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxChargedAmount(BigInteger value) {
        this.maxChargedAmount = value;
    }

    /**
     * Gets the value of the maxChargedAmountDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaxChargedAmountDate() {
        return maxChargedAmountDate;
    }

    /**
     * Sets the value of the maxChargedAmountDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaxChargedAmountDate(XMLGregorianCalendar value) {
        this.maxChargedAmountDate = value;
    }

    /**
     * Gets the value of the lastCreditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastCreditStatus() {
        return lastCreditStatus;
    }

    /**
     * Sets the value of the lastCreditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastCreditStatus(String value) {
        this.lastCreditStatus = value;
    }

    /**
     * Gets the value of the lastCreditStatusDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastCreditStatusDesc() {
        return lastCreditStatusDesc;
    }

    /**
     * Sets the value of the lastCreditStatusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastCreditStatusDesc(String value) {
        this.lastCreditStatusDesc = value;
    }

    /**
     * Gets the value of the lastCreditStatusDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastCreditStatusDate() {
        return lastCreditStatusDate;
    }

    /**
     * Sets the value of the lastCreditStatusDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastCreditStatusDate(XMLGregorianCalendar value) {
        this.lastCreditStatusDate = value;
    }

    /**
     * Gets the value of the lastRestructureEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastRestructureEvent() {
        return lastRestructureEvent;
    }

    /**
     * Sets the value of the lastRestructureEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastRestructureEvent(String value) {
        this.lastRestructureEvent = value;
    }

    /**
     * Gets the value of the lastRestructureEventDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastRestructureEventDesc() {
        return lastRestructureEventDesc;
    }

    /**
     * Sets the value of the lastRestructureEventDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastRestructureEventDesc(String value) {
        this.lastRestructureEventDesc = value;
    }

    /**
     * Gets the value of the lastRestructureEventDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastRestructureEventDate() {
        return lastRestructureEventDate;
    }

    /**
     * Sets the value of the lastRestructureEventDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastRestructureEventDate(XMLGregorianCalendar value) {
        this.lastRestructureEventDate = value;
    }

}