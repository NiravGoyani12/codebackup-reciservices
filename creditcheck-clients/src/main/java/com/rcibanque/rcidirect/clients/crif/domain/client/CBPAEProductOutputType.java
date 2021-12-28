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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CB_PAE_ProductOutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CB_PAE_ProductOutputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageId" type="{urn:crif-creditbureau:v1}MessageIdOutputType" minOccurs="0"/&gt;
 *         &lt;element name="Cardinality" type="{urn:crif-creditbureau:v1}CardinalityType" minOccurs="0"/&gt;
 *         &lt;element name="Alert" type="{urn:crif-creditbureau:v1}AlertType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Error" type="{urn:crif-creditbureau:v1}ErrorsType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CB_PAE_ProductOutputType", propOrder = {
    "messageId",
    "cardinality",
    "alert",
    "error"
})
public class CBPAEProductOutputType {

    @XmlElement(name = "MessageId")
    protected MessageIdOutputType messageId;
    @XmlElement(name = "Cardinality")
    protected CardinalityType cardinality;
    @XmlElement(name = "Alert")
    protected List<AlertType> alert;
    @XmlElement(name = "Error")
    protected List<ErrorsType> error;

    /**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link MessageIdOutputType }
     *     
     */
    public MessageIdOutputType getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageIdOutputType }
     *     
     */
    public void setMessageId(MessageIdOutputType value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the cardinality property.
     * 
     * @return
     *     possible object is
     *     {@link CardinalityType }
     *     
     */
    public CardinalityType getCardinality() {
        return cardinality;
    }

    /**
     * Sets the value of the cardinality property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardinalityType }
     *     
     */
    public void setCardinality(CardinalityType value) {
        this.cardinality = value;
    }

    /**
     * Gets the value of the alert property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alert property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlert().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlertType }
     * 
     * 
     */
    public List<AlertType> getAlert() {
        if (alert == null) {
            alert = new ArrayList<AlertType>();
        }
        return this.alert;
    }

    /**
     * Gets the value of the error property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the error property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorsType }
     * 
     * 
     */
    public List<ErrorsType> getError() {
        if (error == null) {
            error = new ArrayList<ErrorsType>();
        }
        return this.error;
    }

}
