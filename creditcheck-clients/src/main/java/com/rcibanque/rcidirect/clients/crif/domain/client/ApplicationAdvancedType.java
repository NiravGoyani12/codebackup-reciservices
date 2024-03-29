//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.07 at 04:47:12 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationAdvancedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationAdvancedType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="DownPayment" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NetDeposit" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Term" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="MonthlyPayment" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *       &lt;attribute name="GFV" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationAdvancedType")
public class ApplicationAdvancedType {

    @XmlAttribute(name = "DownPayment")
    protected String downPayment;
    @XmlAttribute(name = "NetDeposit")
    protected String netDeposit;
    @XmlAttribute(name = "Term")
    protected String term;
    @XmlAttribute(name = "MonthlyPayment")
    @XmlSchemaType(name = "anySimpleType")
    protected String monthlyPayment;
    @XmlAttribute(name = "GFV")
    @XmlSchemaType(name = "anySimpleType")
    protected String gfv;

    /**
     * Gets the value of the downPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownPayment() {
        return downPayment;
    }

    /**
     * Sets the value of the downPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownPayment(String value) {
        this.downPayment = value;
    }

    /**
     * Gets the value of the netDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetDeposit() {
        return netDeposit;
    }

    /**
     * Sets the value of the netDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetDeposit(String value) {
        this.netDeposit = value;
    }

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerm(String value) {
        this.term = value;
    }

    /**
     * Gets the value of the monthlyPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthlyPayment() {
        return monthlyPayment;
    }

    /**
     * Sets the value of the monthlyPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthlyPayment(String value) {
        this.monthlyPayment = value;
    }

    /**
     * Gets the value of the gfv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGFV() {
        return gfv;
    }

    /**
     * Sets the value of the gfv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGFV(String value) {
        this.gfv = value;
    }

}
