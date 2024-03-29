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
 * <p>Java class for GuaranteeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuaranteeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="Real" type="{urn:crif-creditbureau:v1}RealGuaranteeType" minOccurs="0"/&gt;
 *         &lt;element name="Personal" type="{urn:crif-creditbureau:v1}PersonalGuaranteeType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="CCRCISCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="GuarantorName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ValidityStartDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="ValidityEndDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuaranteeType", propOrder = {
    "real",
    "personal"
})
public class GuaranteeType {

    @XmlElement(name = "Real")
    protected RealGuaranteeType real;
    @XmlElement(name = "Personal")
    protected PersonalGuaranteeType personal;
    @XmlAttribute(name = "CCRCISCode")
    protected String ccrcisCode;
    @XmlAttribute(name = "GuarantorName")
    protected String guarantorName;
    @XmlAttribute(name = "ValidityStartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validityStartDate;
    @XmlAttribute(name = "ValidityEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validityEndDate;

    /**
     * Gets the value of the real property.
     * 
     * @return
     *     possible object is
     *     {@link RealGuaranteeType }
     *     
     */
    public RealGuaranteeType getReal() {
        return real;
    }

    /**
     * Sets the value of the real property.
     * 
     * @param value
     *     allowed object is
     *     {@link RealGuaranteeType }
     *     
     */
    public void setReal(RealGuaranteeType value) {
        this.real = value;
    }

    /**
     * Gets the value of the personal property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalGuaranteeType }
     *     
     */
    public PersonalGuaranteeType getPersonal() {
        return personal;
    }

    /**
     * Sets the value of the personal property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalGuaranteeType }
     *     
     */
    public void setPersonal(PersonalGuaranteeType value) {
        this.personal = value;
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
     * Gets the value of the guarantorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuarantorName() {
        return guarantorName;
    }

    /**
     * Sets the value of the guarantorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuarantorName(String value) {
        this.guarantorName = value;
    }

    /**
     * Gets the value of the validityStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * Sets the value of the validityStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityStartDate(XMLGregorianCalendar value) {
        this.validityStartDate = value;
    }

    /**
     * Gets the value of the validityEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * Sets the value of the validityEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityEndDate(XMLGregorianCalendar value) {
        this.validityEndDate = value;
    }

}
