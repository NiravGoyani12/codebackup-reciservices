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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SocioDemoDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocioDemoDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Nationality" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="CivilStatus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ResidentialStatus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NoOfDependents" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="CurrentAddressSince" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="YearsCurrentAddress" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="MonthsCurrentAddress" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocioDemoDataType")
public class SocioDemoDataType {

    @XmlAttribute(name = "Nationality")
    protected String nationality;
    @XmlAttribute(name = "CivilStatus")
    protected String civilStatus;
    @XmlAttribute(name = "ResidentialStatus")
    protected String residentialStatus;
    @XmlAttribute(name = "NoOfDependents")
    protected String noOfDependents;
    @XmlAttribute(name = "CurrentAddressSince")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar currentAddressSince;
    @XmlAttribute(name = "YearsCurrentAddress")
    protected String yearsCurrentAddress;
    @XmlAttribute(name = "MonthsCurrentAddress")
    protected String monthsCurrentAddress;

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the civilStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivilStatus() {
        return civilStatus;
    }

    /**
     * Sets the value of the civilStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivilStatus(String value) {
        this.civilStatus = value;
    }

    /**
     * Gets the value of the residentialStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidentialStatus() {
        return residentialStatus;
    }

    /**
     * Sets the value of the residentialStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidentialStatus(String value) {
        this.residentialStatus = value;
    }

    /**
     * Gets the value of the noOfDependents property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoOfDependents() {
        return noOfDependents;
    }

    /**
     * Sets the value of the noOfDependents property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoOfDependents(String value) {
        this.noOfDependents = value;
    }

    /**
     * Gets the value of the currentAddressSince property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrentAddressSince() {
        return currentAddressSince;
    }

    /**
     * Sets the value of the currentAddressSince property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrentAddressSince(XMLGregorianCalendar value) {
        this.currentAddressSince = value;
    }

    /**
     * Gets the value of the yearsCurrentAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearsCurrentAddress() {
        return yearsCurrentAddress;
    }

    /**
     * Sets the value of the yearsCurrentAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearsCurrentAddress(String value) {
        this.yearsCurrentAddress = value;
    }

    /**
     * Gets the value of the monthsCurrentAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthsCurrentAddress() {
        return monthsCurrentAddress;
    }

    /**
     * Sets the value of the monthsCurrentAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthsCurrentAddress(String value) {
        this.monthsCurrentAddress = value;
    }

}
