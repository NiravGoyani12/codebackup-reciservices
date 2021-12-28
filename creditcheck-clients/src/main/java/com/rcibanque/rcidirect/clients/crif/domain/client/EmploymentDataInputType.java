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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EmploymentDataInputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentDataInputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="EmployerData" type="{urn:crif-creditbureau:v1}EmployerDataType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="GrossIncome" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="OccupationStatus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="DateHired" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="Occupation" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentDataInputType", propOrder = {
    "employerData"
})
public class EmploymentDataInputType {

    @XmlElement(name = "EmployerData")
    protected EmployerDataType employerData;
    @XmlAttribute(name = "GrossIncome")
    protected String grossIncome;
    @XmlAttribute(name = "Currency")
    protected String currency;
    @XmlAttribute(name = "OccupationStatus")
    protected String occupationStatus;
    @XmlAttribute(name = "DateHired")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateHired;
    @XmlAttribute(name = "Occupation")
    protected String occupation;

    /**
     * Gets the value of the employerData property.
     * 
     * @return
     *     possible object is
     *     {@link EmployerDataType }
     *     
     */
    public EmployerDataType getEmployerData() {
        return employerData;
    }

    /**
     * Sets the value of the employerData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployerDataType }
     *     
     */
    public void setEmployerData(EmployerDataType value) {
        this.employerData = value;
    }

    /**
     * Gets the value of the grossIncome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossIncome() {
        return grossIncome;
    }

    /**
     * Sets the value of the grossIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossIncome(String value) {
        this.grossIncome = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the occupationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupationStatus() {
        return occupationStatus;
    }

    /**
     * Sets the value of the occupationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupationStatus(String value) {
        this.occupationStatus = value;
    }

    /**
     * Gets the value of the dateHired property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateHired() {
        return dateHired;
    }

    /**
     * Sets the value of the dateHired property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateHired(XMLGregorianCalendar value) {
        this.dateHired = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the value of the occupation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupation(String value) {
        this.occupation = value;
    }

}
