//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ME_EnquiredDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ME_EnquiredDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="CIS" type="{urn:crif-creditbureau:v1}CISOutputType"/&gt;
 *         &lt;element name="CISCodes" type="{urn:crif-creditbureau:v1}ME_CISCodesType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ME_EnquiredDataType", propOrder = {
    "cis",
    "cisCodes"
})
public class MEEnquiredDataType {

    @XmlElement(name = "CIS")
    protected CISOutputType cis;
    @XmlElement(name = "CISCodes")
    protected MECISCodesType cisCodes;

    /**
     * Gets the value of the cis property.
     * 
     * @return
     *     possible object is
     *     {@link CISOutputType }
     *     
     */
    public CISOutputType getCIS() {
        return cis;
    }

    /**
     * Sets the value of the cis property.
     * 
     * @param value
     *     allowed object is
     *     {@link CISOutputType }
     *     
     */
    public void setCIS(CISOutputType value) {
        this.cis = value;
    }

    /**
     * Gets the value of the cisCodes property.
     * 
     * @return
     *     possible object is
     *     {@link MECISCodesType }
     *     
     */
    public MECISCodesType getCISCodes() {
        return cisCodes;
    }

    /**
     * Sets the value of the cisCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link MECISCodesType }
     *     
     */
    public void setCISCodes(MECISCodesType value) {
        this.cisCodes = value;
    }

}
