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
 * <p>Java class for FootPrintType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FootPrintType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="FunctionCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="FunctionCodeDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PurposeTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PurposeTypeCodeDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="EnquiryDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="CIPCodeEncrypted" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FootPrintType")
public class FootPrintType {

    @XmlAttribute(name = "FunctionCode")
    protected String functionCode;
    @XmlAttribute(name = "FunctionCodeDesc")
    protected String functionCodeDesc;
    @XmlAttribute(name = "PurposeTypeCode")
    protected String purposeTypeCode;
    @XmlAttribute(name = "PurposeTypeCodeDesc")
    protected String purposeTypeCodeDesc;
    @XmlAttribute(name = "EnquiryDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar enquiryDate;
    @XmlAttribute(name = "CIPCodeEncrypted")
    protected String cipCodeEncrypted;

    /**
     * Gets the value of the functionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Sets the value of the functionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionCode(String value) {
        this.functionCode = value;
    }

    /**
     * Gets the value of the functionCodeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionCodeDesc() {
        return functionCodeDesc;
    }

    /**
     * Sets the value of the functionCodeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionCodeDesc(String value) {
        this.functionCodeDesc = value;
    }

    /**
     * Gets the value of the purposeTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurposeTypeCode() {
        return purposeTypeCode;
    }

    /**
     * Sets the value of the purposeTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurposeTypeCode(String value) {
        this.purposeTypeCode = value;
    }

    /**
     * Gets the value of the purposeTypeCodeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurposeTypeCodeDesc() {
        return purposeTypeCodeDesc;
    }

    /**
     * Sets the value of the purposeTypeCodeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurposeTypeCodeDesc(String value) {
        this.purposeTypeCodeDesc = value;
    }

    /**
     * Gets the value of the enquiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnquiryDate() {
        return enquiryDate;
    }

    /**
     * Sets the value of the enquiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnquiryDate(XMLGregorianCalendar value) {
        this.enquiryDate = value;
    }

    /**
     * Gets the value of the cipCodeEncrypted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIPCodeEncrypted() {
        return cipCodeEncrypted;
    }

    /**
     * Sets the value of the cipCodeEncrypted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIPCodeEncrypted(String value) {
        this.cipCodeEncrypted = value;
    }

}
