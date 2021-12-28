//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CompanyMatchedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompanyMatchedType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyName" type="{urn:crif-creditbureau:v1}CompanyNameType" minOccurs="0"/&gt;
 *         &lt;element name="AddressHistory" type="{urn:crif-creditbureau:v1}AddressHistoryType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="IdentificationCode" type="{urn:crif-creditbureau:v1}IdentificationCodeMatchedType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Contact" type="{urn:crif-creditbureau:v1}ContactMatchedType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SuspectImpersonation" type="{urn:crif-creditbureau:v1}SuspectImpersonationType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="EntityForm" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="EntityFormDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompanyMatchedType", propOrder = {
    "companyName",
    "addressHistory",
    "identificationCode",
    "contact",
    "suspectImpersonation"
})
public class CompanyMatchedType {

    @XmlElement(name = "CompanyName")
    protected CompanyNameType companyName;
    @XmlElement(name = "AddressHistory")
    protected List<AddressHistoryType> addressHistory;
    @XmlElement(name = "IdentificationCode")
    protected List<IdentificationCodeMatchedType> identificationCode;
    @XmlElement(name = "Contact")
    protected List<ContactMatchedType> contact;
    @XmlElement(name = "SuspectImpersonation")
    protected SuspectImpersonationType suspectImpersonation;
    @XmlAttribute(name = "EntityForm")
    protected String entityForm;
    @XmlAttribute(name = "EntityFormDesc")
    protected String entityFormDesc;
    @XmlAttribute(name = "LastUpdateDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastUpdateDate;

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyNameType }
     *     
     */
    public CompanyNameType getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyNameType }
     *     
     */
    public void setCompanyName(CompanyNameType value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the addressHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressHistoryType }
     * 
     * 
     */
    public List<AddressHistoryType> getAddressHistory() {
        if (addressHistory == null) {
            addressHistory = new ArrayList<AddressHistoryType>();
        }
        return this.addressHistory;
    }

    /**
     * Gets the value of the identificationCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificationCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificationCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentificationCodeMatchedType }
     * 
     * 
     */
    public List<IdentificationCodeMatchedType> getIdentificationCode() {
        if (identificationCode == null) {
            identificationCode = new ArrayList<IdentificationCodeMatchedType>();
        }
        return this.identificationCode;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactMatchedType }
     * 
     * 
     */
    public List<ContactMatchedType> getContact() {
        if (contact == null) {
            contact = new ArrayList<ContactMatchedType>();
        }
        return this.contact;
    }

    /**
     * Gets the value of the suspectImpersonation property.
     * 
     * @return
     *     possible object is
     *     {@link SuspectImpersonationType }
     *     
     */
    public SuspectImpersonationType getSuspectImpersonation() {
        return suspectImpersonation;
    }

    /**
     * Sets the value of the suspectImpersonation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuspectImpersonationType }
     *     
     */
    public void setSuspectImpersonation(SuspectImpersonationType value) {
        this.suspectImpersonation = value;
    }

    /**
     * Gets the value of the entityForm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityForm() {
        return entityForm;
    }

    /**
     * Sets the value of the entityForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityForm(String value) {
        this.entityForm = value;
    }

    /**
     * Gets the value of the entityFormDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityFormDesc() {
        return entityFormDesc;
    }

    /**
     * Sets the value of the entityFormDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityFormDesc(String value) {
        this.entityFormDesc = value;
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