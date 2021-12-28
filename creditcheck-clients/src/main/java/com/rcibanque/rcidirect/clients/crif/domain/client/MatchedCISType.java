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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MatchedCISType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MatchedCISType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Individual" type="{urn:crif-creditbureau:v1}IndividualMatchedType"/&gt;
 *         &lt;element name="Company" type="{urn:crif-creditbureau:v1}CompanyMatchedType"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="CCRCISCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="FlagMatched" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchedCISType", propOrder = {
    "individual",
    "company"
})
public class MatchedCISType {

    @XmlElement(name = "Individual")
    protected IndividualMatchedType individual;
    @XmlElement(name = "Company")
    protected CompanyMatchedType company;
    @XmlAttribute(name = "CCRCISCode", required = true)
    protected String ccrcisCode;
    @XmlAttribute(name = "FlagMatched", required = true)
    protected String flagMatched;

    /**
     * Gets the value of the individual property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualMatchedType }
     *     
     */
    public IndividualMatchedType getIndividual() {
        return individual;
    }

    /**
     * Sets the value of the individual property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualMatchedType }
     *     
     */
    public void setIndividual(IndividualMatchedType value) {
        this.individual = value;
    }

    /**
     * Gets the value of the company property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyMatchedType }
     *     
     */
    public CompanyMatchedType getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyMatchedType }
     *     
     */
    public void setCompany(CompanyMatchedType value) {
        this.company = value;
    }

    /**
     * Gets the value of the ccrcisCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCRCISCode() {
        return ccrcisCode;
    }

    /**
     * Sets the value of the ccrcisCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCRCISCode(String value) {
        this.ccrcisCode = value;
    }

    /**
     * Gets the value of the flagMatched property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagMatched() {
        return flagMatched;
    }

    /**
     * Sets the value of the flagMatched property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagMatched(String value) {
        this.flagMatched = value;
    }

}
