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
 * <p>Java class for EmploymentDataMatchedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentDataMatchedType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="EmploymentData" type="{urn:crif-creditbureau:v1}EmploymentDataType"/&gt;
 *         &lt;element name="EmploymentDataDesc" type="{urn:crif-creditbureau:v1}EmploymentDataDescType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentDataMatchedType", propOrder = {
    "employmentData",
    "employmentDataDesc"
})
public class EmploymentDataMatchedType {

    @XmlElement(name = "EmploymentData")
    protected EmploymentDataType employmentData;
    @XmlElement(name = "EmploymentDataDesc")
    protected EmploymentDataDescType employmentDataDesc;
    @XmlAttribute(name = "LastUpdateDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastUpdateDate;

    /**
     * Gets the value of the employmentData property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentDataType }
     *     
     */
    public EmploymentDataType getEmploymentData() {
        return employmentData;
    }

    /**
     * Sets the value of the employmentData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentDataType }
     *     
     */
    public void setEmploymentData(EmploymentDataType value) {
        this.employmentData = value;
    }

    /**
     * Gets the value of the employmentDataDesc property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentDataDescType }
     *     
     */
    public EmploymentDataDescType getEmploymentDataDesc() {
        return employmentDataDesc;
    }

    /**
     * Sets the value of the employmentDataDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentDataDescType }
     *     
     */
    public void setEmploymentDataDesc(EmploymentDataDescType value) {
        this.employmentDataDesc = value;
    }

    /**
     * Gets the value of the lastUpdateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the value of the lastUpdateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdateDate(XMLGregorianCalendar value) {
        this.lastUpdateDate = value;
    }

}