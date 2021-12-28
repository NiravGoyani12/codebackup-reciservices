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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PEP" type="{http://www.w3.org/2001/XMLSchema}PEPType" minOccurs="0"/&gt;
 *         &lt;element name="InvidualData" type="{http://www.w3.org/2001/XMLSchema}InvidualDataType" minOccurs="0"/&gt;
 *         &lt;element name="AddressData" type="{http://www.w3.org/2001/XMLSchema}AddressDataType" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="IdentificationData" type="{http://www.w3.org/2001/XMLSchema}IdentificationDataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Contact" type="{http://www.w3.org/2001/XMLSchema}ContactType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SocioDemoData" type="{http://www.w3.org/2001/XMLSchema}SocioDemoDataType" minOccurs="0"/&gt;
 *         &lt;element name="Employment" type="{http://www.w3.org/2001/XMLSchema}EmploymentType" minOccurs="0"/&gt;
 *         &lt;element name="IncomeExpenses" type="{http://www.w3.org/2001/XMLSchema}IncomeExpensesType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SoleTraderData" type="{http://www.w3.org/2001/XMLSchema}SoleTraderDataType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualType", propOrder = {
    "pep",
    "invidualData",
    "addressData",
    "identificationData",
    "contact",
    "socioDemoData",
    "employment",
    "incomeExpenses",
    "soleTraderData"
})
@XmlSeeAlso({
    com.rcibanque.rcidirect.clients.crif.domain.client.SubjectInputType.Individual.class
})
public class IndividualType {

    @XmlElement(name = "PEP")
    protected PEPType pep;
    @XmlElement(name = "InvidualData")
    protected InvidualDataType invidualData;
    @XmlElement(name = "AddressData")
    protected List<AddressDataType> addressData;
    @XmlElement(name = "IdentificationData")
    protected List<IdentificationDataType> identificationData;
    @XmlElement(name = "Contact")
    protected List<ContactType> contact;
    @XmlElement(name = "SocioDemoData")
    protected SocioDemoDataType socioDemoData;
    @XmlElement(name = "Employment")
    protected EmploymentType employment;
    @XmlElement(name = "IncomeExpenses")
    protected List<IncomeExpensesType> incomeExpenses;
    @XmlElement(name = "SoleTraderData")
    protected SoleTraderDataType soleTraderData;

    /**
     * Gets the value of the pep property.
     * 
     * @return
     *     possible object is
     *     {@link PEPType }
     *     
     */
    public PEPType getPEP() {
        return pep;
    }

    /**
     * Sets the value of the pep property.
     * 
     * @param value
     *     allowed object is
     *     {@link PEPType }
     *     
     */
    public void setPEP(PEPType value) {
        this.pep = value;
    }

    /**
     * Gets the value of the invidualData property.
     * 
     * @return
     *     possible object is
     *     {@link InvidualDataType }
     *     
     */
    public InvidualDataType getInvidualData() {
        return invidualData;
    }

    /**
     * Sets the value of the invidualData property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvidualDataType }
     *     
     */
    public void setInvidualData(InvidualDataType value) {
        this.invidualData = value;
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

    /**
     * Gets the value of the socioDemoData property.
     * 
     * @return
     *     possible object is
     *     {@link SocioDemoDataType }
     *     
     */
    public SocioDemoDataType getSocioDemoData() {
        return socioDemoData;
    }

    /**
     * Sets the value of the socioDemoData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocioDemoDataType }
     *     
     */
    public void setSocioDemoData(SocioDemoDataType value) {
        this.socioDemoData = value;
    }

    /**
     * Gets the value of the employment property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentType }
     *     
     */
    public EmploymentType getEmployment() {
        return employment;
    }

    /**
     * Sets the value of the employment property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentType }
     *     
     */
    public void setEmployment(EmploymentType value) {
        this.employment = value;
    }

    /**
     * Gets the value of the incomeExpenses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incomeExpenses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncomeExpenses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncomeExpensesType }
     * 
     * 
     */
    public List<IncomeExpensesType> getIncomeExpenses() {
        if (incomeExpenses == null) {
            incomeExpenses = new ArrayList<IncomeExpensesType>();
        }
        return this.incomeExpenses;
    }

    /**
     * Gets the value of the soleTraderData property.
     * 
     * @return
     *     possible object is
     *     {@link SoleTraderDataType }
     *     
     */
    public SoleTraderDataType getSoleTraderData() {
        return soleTraderData;
    }

    /**
     * Sets the value of the soleTraderData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoleTraderDataType }
     *     
     */
    public void setSoleTraderData(SoleTraderDataType value) {
        this.soleTraderData = value;
    }

}