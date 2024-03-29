//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.07 at 04:47:12 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorporateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorporateType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CorporateData" type="{http://www.w3.org/2001/XMLSchema}CorporateDataType" minOccurs="0"/&gt;
 *         &lt;element name="AddressData" type="{http://www.w3.org/2001/XMLSchema}AddressDataType" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="IdentificationData" type="{http://www.w3.org/2001/XMLSchema}IdentificationDataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Contact" type="{http://www.w3.org/2001/XMLSchema}ContactType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorporateType", propOrder = {
    "corporateData",
    "addressData",
    "identificationData",
    "contact"
})
public class CorporateType {

    @XmlElement(name = "CorporateData")
    protected CorporateDataType corporateData;
    @XmlElement(name = "AddressData")
    protected List<AddressDataType> addressData;
    @XmlElement(name = "IdentificationData")
    protected List<IdentificationDataType> identificationData;
    @XmlElement(name = "Contact")
    protected List<ContactType> contact;

    /**
     * Gets the value of the corporateData property.
     * 
     * @return
     *     possible object is
     *     {@link CorporateDataType }
     *     
     */
    public CorporateDataType getCorporateData() {
        return corporateData;
    }

    /**
     * Sets the value of the corporateData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorporateDataType }
     *     
     */
    public void setCorporateData(CorporateDataType value) {
        this.corporateData = value;
    }

    /**
     * Gets the value of the addressData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressDataType }
     * 
     * 
     */
    public List<AddressDataType> getAddressData() {
        if (addressData == null) {
            addressData = new ArrayList<AddressDataType>();
        }
        return this.addressData;
    }

    /**
     * Gets the value of the identificationData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificationData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificationData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentificationDataType }
     * 
     * 
     */
    public List<IdentificationDataType> getIdentificationData() {
        if (identificationData == null) {
            identificationData = new ArrayList<IdentificationDataType>();
        }
        return this.identificationData;
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
     * {@link ContactType }
     * 
     * 
     */
    public List<ContactType> getContact() {
        if (contact == null) {
            contact = new ArrayList<ContactType>();
        }
        return this.contact;
    }

}
