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
 * <p>Java class for ApplicationCodesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationCodesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="CIPContractNo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="CIPApplicationNo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="CCRContractCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationCodesType")
public class ApplicationCodesType {

    @XmlAttribute(name = "CIPContractNo")
    protected String cipContractNo;
    @XmlAttribute(name = "CIPApplicationNo")
    protected String cipApplicationNo;
    @XmlAttribute(name = "CCRContractCode", required = true)
    protected String ccrContractCode;

    /**
     * Gets the value of the cipContractNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIPContractNo() {
        return cipContractNo;
    }

    /**
     * Sets the value of the cipContractNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIPContractNo(String value) {
        this.cipContractNo = value;
    }

    /**
     * Gets the value of the cipApplicationNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIPApplicationNo() {
        return cipApplicationNo;
    }

    /**
     * Sets the value of the cipApplicationNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIPApplicationNo(String value) {
        this.cipApplicationNo = value;
    }

    /**
     * Gets the value of the ccrContractCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCRContractCode() {
        return ccrContractCode;
    }

    /**
     * Sets the value of the ccrContractCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCRContractCode(String value) {
        this.ccrContractCode = value;
    }

}