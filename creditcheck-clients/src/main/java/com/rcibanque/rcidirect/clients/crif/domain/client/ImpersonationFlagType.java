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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ImpersonationFlagType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImpersonationFlagType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ImpersonationFlag" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="StartValidityDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImpersonationFlagType")
public class ImpersonationFlagType {

    @XmlAttribute(name = "ImpersonationFlag")
    protected String impersonationFlag;
    @XmlAttribute(name = "StartValidityDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startValidityDate;

    /**
     * Gets the value of the impersonationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpersonationFlag() {
        return impersonationFlag;
    }

    /**
     * Sets the value of the impersonationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpersonationFlag(String value) {
        this.impersonationFlag = value;
    }

    /**
     * Gets the value of the startValidityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartValidityDate() {
        return startValidityDate;
    }

    /**
     * Sets the value of the startValidityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartValidityDate(XMLGregorianCalendar value) {
        this.startValidityDate = value;
    }

}
